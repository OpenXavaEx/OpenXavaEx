/**
 * 
 */
package org.openxava.web.layout.impl;

import static org.openxava.web.layout.LayoutJspKeys.ATTRVAL_STYLE_WIDTH_100P;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_CLASS;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_COLSPAN;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_ID;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_LIST;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_SRC;
import static org.openxava.web.layout.LayoutJspKeys.ATTR_STYLE;
import static org.openxava.web.layout.LayoutJspKeys.TAG_DIV;
import static org.openxava.web.layout.LayoutJspKeys.TAG_IMG;
import static org.openxava.web.layout.LayoutJspKeys.TAG_SPAN;
import static org.openxava.web.layout.LayoutJspKeys.TAG_TABLE;
import static org.openxava.web.layout.LayoutJspKeys.TAG_TD;
import static org.openxava.web.layout.LayoutJspKeys.TAG_TR;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.annotations.LabelFormatType;
import org.openxava.controller.ModuleContext;
import org.openxava.model.meta.MetaReference;
import org.openxava.util.Is;
import org.openxava.view.View;
import org.openxava.view.meta.MetaView;
import org.openxava.view.meta.MetaViewAction;
import org.openxava.web.Ids;
import org.openxava.web.layout.AbstractJspPainter;
import org.openxava.web.layout.ILayoutCollectionBeginElement;
import org.openxava.web.layout.ILayoutCollectionEndElement;
import org.openxava.web.layout.ILayoutColumnBeginElement;
import org.openxava.web.layout.ILayoutColumnEndElement;
import org.openxava.web.layout.ILayoutFrameBeginElement;
import org.openxava.web.layout.ILayoutFrameEndElement;
import org.openxava.web.layout.ILayoutGroupBeginElement;
import org.openxava.web.layout.ILayoutGroupEndElement;
import org.openxava.web.layout.ILayoutPropertyBeginElement;
import org.openxava.web.layout.ILayoutPropertyEndElement;
import org.openxava.web.layout.ILayoutRowBeginElement;
import org.openxava.web.layout.ILayoutRowEndElement;
import org.openxava.web.layout.ILayoutSectionsBeginElement;
import org.openxava.web.layout.ILayoutSectionsEndElement;
import org.openxava.web.layout.ILayoutSectionsRenderBeginElement;
import org.openxava.web.layout.ILayoutSectionsRenderEndElement;
import org.openxava.web.layout.ILayoutViewBeginElement;
import org.openxava.web.layout.ILayoutViewEndElement;
import org.openxava.web.layout.LayoutJspKeys;
import org.openxava.web.layout.LayoutJspUtils;
import org.openxava.web.style.Style;
import org.openxava.web.taglib.ActionTag;
import org.openxava.web.taglib.EditorTag;
import org.openxava.web.taglib.LinkTag;

/**
 * Implements a basic Painter.
 * @author Federico Alcantara
 *
 */
public class DefaultLayoutPainter extends AbstractJspPainter {
	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory.getLog(DefaultLayoutPainter.class);
	private boolean firstCellPainted = false;
	private int tdPerColumn = 2; // One TD for the label and another for Data and other cells.
	private int frameLevel = 0;
	private boolean blockStarted = false;
	
	/**
	 * @see org.openxava.web.layout.ILayoutPainter#beginView(org.openxava.web.layout.LayoutElement)
	 */
	public void beginView(ILayoutViewBeginElement element) {
		frameLevel = 0;
		resetLog();
		setContainer(element);
		attributes.clear();
		if (element.isRepresentsSection()) {
			attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		} else {
			if (element.getView().isFramesMaximized()
					&& element.getMaxFramesCount() > 0) {
				attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
			}
		}
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endView(org.openxava.web.layout.LayoutElement)
	 */
	public void endView(ILayoutViewEndElement element) {
		frameLevel = 0;
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		outputLog();
	}

	/**
	 * This has the same behavior as the startFrame method.
	 * @see org.openxava.web.layout.ILayoutPainter#beginGroup(org.openxava.web.layout.LayoutElement)
	 */
	public void beginGroup(ILayoutGroupBeginElement element) {
		beginFrame(element);
	}

	/**
	 * This has the same behavior as the endFrame method.
	 * @see org.openxava.web.layout.ILayoutPainter#endGroup(org.openxava.web.layout.LayoutElement)
	 */
	public void endGroup(ILayoutGroupEndElement element) {
		endFrame(element);
	}

	/**
	 * Creates the frame. This implementation uses the same style as the original OX design.
	 * @see org.openxava.web.layout.ILayoutPainter#beginFrame(org.openxava.web.layout.LayoutElement)
	 */
	public void beginFrame(ILayoutFrameBeginElement element) {
		// Frame should occupy as many columns as needed. 
		// In this design each column is 2 TD wide.
		// However if this frame is the only one in the row
		// Takes the full size of the view.
		frameLevel++;
		Integer columnSpan = 0;
		String labelKey = Ids.decorate(
				getRequest().getParameter("application"),
				getRequest().getParameter("module"),
				"label_" + getView().getPropertyPrefix() + element.getName()); 
		boolean maximizeTable = false;
		if (frameLevel <= 1) {
			maximizeTable = getContainer().getView().isFramesMaximized();
		}
		int count = getRow().getRowCurrentColumnsCount() + 1;
		if (getRow().getMaxFramesCount() == getRow().getMaxRowColumnsCount() &&
				getRow().getMaxFramesCount() == 1 &&
				element.getGroupLevel() <= 3) { // A single frame topmost
			columnSpan = getMaxColumnsOnView();
			count = getMaxColumnsOnView();
		}
		columnSpan = calculateTdSpan(columnSpan);
		
		getRow().setRowCurrentColumnsCount(count);
		
		attributes.clear();
		if (columnSpan > 0) {
			attributes.put(ATTR_COLSPAN, columnSpan.toString());
		}
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		
		write(getStyle().getFrameHeaderStartDecoration(maximizeTable ? 100 : 0));
			write(getStyle().getFrameTitleStartDecoration());
				attributes.clear();
				attributes.put(ATTR_ID, labelKey);
				write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
					write(element.getLabel());
				write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
			write(getStyle().getFrameTitleEndDecoration());
			write(getStyle().getFrameActionsStartDecoration());
				String frameId = Ids.decorate(getRequest(), "frame_group_" + getView().getPropertyPrefix() + element.getName());
				String frameActionsURL = "frameActions.jsp?frameId=" + frameId + 
					"&closed=" + getView().isFrameClosed(frameId);
				includeJspPage(frameActionsURL);
			write(getStyle().getFrameActionsEndDecoration());
		write(getStyle().getFrameHeaderEndDecoration());
		write(getStyle().getFrameContentStartDecoration(frameId + "content", getView().isFrameClosed(frameId)));
		// Start the property container
		attributes.clear();
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
		setContainer(element);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endFrame(org.openxava.web.layout.LayoutElement)
	 */
	public void endFrame(ILayoutFrameEndElement element) {
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		write(getStyle().getFrameContentEndDecoration());
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		createTdColumnSpan();
		unsetContainer();
		frameLevel--;
	}

	/**
	 * Actually starts a row where all the columns are painted.
	 * @see org.openxava.web.layout.ILayoutPainter#beginRow(org.openxava.web.layout.LayoutElement)
	 */
	public void beginRow(ILayoutRowBeginElement element) {
		setRow(element);
		if (element.getMaxRowColumnsCount() > 0) {
			attributes.clear();
			if (getRow().getMaxFramesCount() > 1) {
				attributes.put("valign", "top");
			}
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR, attributes));
			if (element.getMaxFramesCount() > 0) {
				attributes.clear();
				Integer columnSpan = calculateTdSpan(getMaxColumnsOnView());
				attributes.put(ATTR_COLSPAN, columnSpan.toString());
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
				
				attributes.clear();
				attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
				attributes.clear();
				attributes.put("valign", "top");
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TR, attributes));
			} else if (element.isBlockStart()) {
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
				blockStarted = true;
			}
		}
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endRow(org.openxava.web.layout.LayoutElement)
	 */
	public void endRow(ILayoutRowEndElement element) {
		if (getRow().getMaxRowColumnsCount() > 0 ) {
			if (!getRow().isBlockEnd()) {
				createTdColumnSpan();
			}
			if (getRow().getMaxFramesCount() > 0) {
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			} else if (getRow().isBlockEnd()) {
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
				blockStarted = false;
			}
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
			if (!getRow().isLast()) { // Do not create the row spacer for the last row
				createRowSpacer();
			}
		}
		unsetRow();
	}
	
	/**
	 * Creates a space between rows.
	 */
	protected void createRowSpacer() {
		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutRowSpacer());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TR, attributes));
		
		// Label content
		attributes.clear();
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutLabelCell() + " " + Style.getInstance().getLayoutRowSpacerLabelCell());
		
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		// Data content
		attributes.clear();
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutDataCell() + " " + Style.getInstance().getLayoutRowSpacerDataCell());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));

	}
	
	/**
	 * Creates the necessary td for column span
	 */
	protected void createTdColumnSpan() {
		createTdColumnSpan(getRow().getRowCurrentColumnsCount(), LayoutJspKeys.CHAR_SPACE);
	}

	/**
	 * Creates the necessary td for column span
	 * @param currentColumnsCount Number of columns so far displayed
	 */
	protected void createTdColumnSpan(int currentColumnsCount, String spacer) {
		// Separation line
		int maxColumnsCount = getMaxColumnsOnView();
		if (maxColumnsCount > currentColumnsCount) {
			Integer lastColumnSpan = maxColumnsCount - currentColumnsCount;
			lastColumnSpan = calculateTdSpan(lastColumnSpan); // Each column has 2 TD elements.

			if (lastColumnSpan > 0) {
				attributes.clear();
				attributes.put(ATTR_COLSPAN, lastColumnSpan.toString());
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			}
		}
	}

	
	/**
	 * Each column does not open a table element (TD), this is done by the 
	 * startCell method. However, each column can contain more than one cell,
	 * but only three TD elements are allowed in the column, so the first cell
	 * creates one TD for the left spacer, another TD for the label and 
	 * a final TD for the data and any other remaining cell of the column.
	 * By parsing contiguous cells without labels are considered as part of the column.<br />
	 * In this implementation columns are composed of three TD.
	 * @see org.openxava.web.layout.ILayoutPainter#beginColumn(org.openxava.web.layout.LayoutElement)
	 */
	public void beginColumn(ILayoutColumnBeginElement element) {
		int count = getRow().getRowCurrentColumnsCount() + 1;
		getRow().setRowCurrentColumnsCount(count);
		firstCellPainted = false; // to indicate to the cell renderer that the TD pair is about to start.
	}

	/**
	 * In this painter implementation the column does end the last TD. So the cell
	 * implementation must start the first TD but NOT close the last one
	 * @see org.openxava.web.layout.ILayoutPainter#endColumn(org.openxava.web.layout.LayoutElement)
	 */
	public void endColumn(ILayoutColumnEndElement element) {
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#beginProperty(org.openxava.web.layout.LayoutElement)
	 */
	public void beginProperty(ILayoutPropertyBeginElement element) {
		Integer width =  getMaxColumnsOnView() > 0 ? 50 / getMaxColumnsOnView() : 0;
		width = 0;
		if (!firstCellPainted) {
			attributes.clear();
			attributes.put(ATTR_CLASS, getStyle().getLabel() + " " + getStyle().getLayoutLabelCell());
			if (width > 0) {
				attributes.put(ATTR_STYLE, "width:" + width.toString() + "%");
			}
			attributes.put("valign", "center");
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		}
		
		// Left spacer
		beginPropertySpacer(element, getStyle().getLayoutLabelLeftSpacer());
		
		if (!element.isMetaViewAction()) {
			// Label
			beginPropertyLabel(element);
		}
		
		// Left spacer
		beginPropertySpacer(element, getStyle().getLayoutLabelRightSpacer());
		
		if (!firstCellPainted) {
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		}

		// Data. There is no end TD tag this one is closed by the end column method.
		if (!firstCellPainted) {
			attributes.clear();
			attributes.put(ATTR_CLASS, getStyle().getLayoutDataCell());
			StringBuffer style = new StringBuffer("");
			if (width > 0) {
				style.append("width:" + width.toString() + "%");
			}
			if (style.length() > 0) {
				attributes.put(ATTR_STYLE, style.toString());
			}
			attributes.put("valign", "center");
			startTd();
		}
		if (element.isDisplayAsDescriptionsList() 
				|| element.getMetaReference() != null) {
			beginReferenceData(element);
		} else {
			if (element.isMetaViewAction()) {
				beginMetaViewActionData(element);
			} else {
				beginPropertyData(element);
			}
		}
		
		// Mark first cell painted
		firstCellPainted = true;
	}
	
	/**
	 * Starts a TD properly (with column span if needed).
	 */
	private void startTd() {
		if (getRow().getMaxRowColumnsCount() == getRow().getRowCurrentColumnsCount() &&
				!blockStarted) { // last column
			Integer columnSpan = getMaxColumnsOnView() - getRow().getRowCurrentColumnsCount();
			if (columnSpan > 0) {
				columnSpan = calculateTdSpan(columnSpan);
				columnSpan = columnSpan + 1;
				//attributes.put(ATTR_COLSPAN, columnSpan.toString());
				getRow().setRowCurrentColumnsCount(getMaxColumnsOnView()); // No td to add.
			}
		}
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
	}

	/**
	 * Paints the cell left spacer.
	 * @param element Representing cell element.
	 */
	protected void beginPropertySpacer(ILayoutPropertyBeginElement element, String classType) {
		attributes.clear();
		attributes.put(ATTR_CLASS, classType);
		attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/spacer.gif");
		write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
	}
	
	/**
	 * Paints the cell label.
	 * @param element Representing cell element.
	 */
	protected void beginPropertyLabel(ILayoutPropertyBeginElement element) {
		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutLabel());
		if (!element.isDisplayAsDescriptionsList()) {
			attributes.put(ATTR_ID, Ids.decorate(getRequest(), "label_" + element.getPropertyPrefix() 
					+ element.getName()));
		} else {
			attributes.put(ATTR_ID, Ids.decorate(getRequest(), "label_" + element.getPropertyPrefix() 
					+ element.getReferenceForDescriptionsList()));
		}
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
		String label = element.getLabelFormat() != LabelFormatType.NO_LABEL.ordinal() &&
				element.getLabel() != null ? element.getLabel() + LayoutJspKeys.CHAR_SPACE : LayoutJspKeys.CHAR_SPACE;
		label = label.replaceAll(" ", LayoutJspKeys.CHAR_SPACE);
		write(label);
		String img = "";
		if (!element.isDisplayAsDescriptionsList()) {
			if (element.isKey()) {
				img = "key.gif";
			} else if (element.isRequired()) {
				if (element.isEditable()) { // No need to mark it as required, since the user can not change it anyway
					img = "required.gif";
				}
			}
		} else if (element.isRequired()) {
			img = "required.gif";
		}
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
		if (!Is.emptyString(img)) {
			attributes.clear();
			attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/" + img);
			write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
		}
		attributes.clear();
		attributes.put(ATTR_ID, Ids.decorate(getRequest(), "error_image_" + element.getQualifiedName()));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
			if (getErrors().memberHas(element.getMetaMember())) {
				attributes.clear();
				attributes.put(ATTR_SRC, getRequest().getContextPath() + "/xava/images/error.gif");
				write(LayoutJspUtils.INSTANCE.startTag(TAG_IMG, attributes));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_IMG));
			}
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
	}
	
	/**
	 * Paints the input controls.
	 * @param element Element to be painted.
	 */
	protected void beginPropertyData(ILayoutPropertyBeginElement element) {

		attributes.clear();
		attributes.put(ATTR_CLASS, getStyle().getLayoutData());
		attributes.put(ATTR_ID, Ids.decorate(getRequest(), "editor_"
				+ element.getPropertyPrefix() + element.getName()));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
		
		getRequest().setAttribute(element.getPropertyKey(), element.getMetaMember());
		EditorTag editorTag = new EditorTag();
		editorTag.setProperty(element.getName());
		editorTag.setEditable(element.isEditable());
		editorTag.setViewObject(element.getView().getViewObject());
		editorTag.setPropertyPrefix(element.getPropertyPrefix());
		if (element.isEditable()) {
			if (element.getMetaProperty().isKey()) {
				editorTag.setEditable(element.getView().isKeyEditable());
			}
			if (element.isLastSearchKey() && element.isSearch()) {
				editorTag.setEditable(element.isEditable());
			}
		}
		editorTag.setPageContext(getPageContext());
		editorTag.setThrowPropertyChanged(element.isThrowPropertyChanged());
		
		try {
			editorTag.doStartTag();
		} catch (JspException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));

		beginPropertyDataAddPropertyActions(element);
	}
	
	/**
	 * Adds the list of property Actions.
	 * @param element Element containing the actions.
	 */
	private void beginPropertyDataAddPropertyActions(ILayoutPropertyBeginElement element) {
		boolean isSearch = false;
		boolean isCreateNew = false;
		boolean isModify = false;
		try {
			isSearch = element.getView().isSearch();
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		try {
			isCreateNew = element.getView().isCreateNew();
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		try {
			isModify = element.getView().isModify();
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
		beginPropertyDataAddActions(element, isSearch, isCreateNew, isModify);
	}
	
	@SuppressWarnings("rawtypes")
	/**
	 * Paints the action for editable properties according to the state of search, createNew and modify.
	 * @param element Current element.
	 * @param isSearch If true a search action is rendered.
	 * @param isCreateNew If true a create new object action is rendered.
	 * @param isModify If true a modify object is rendered.
	 */
	private void beginPropertyDataAddActions(ILayoutPropertyBeginElement element, 
			boolean isSearch, boolean isCreateNew, boolean isModify) {
		String propertyPrefix = element.getPropertyPrefix() == null ? "" : element.getPropertyPrefix();
		attributes.clear();
		attributes.put("id", Ids.decorate(getRequest(), "property_actions_" + propertyPrefix + element.getName()));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));

		ActionTag actionTag;
		try {
			if (element.hasActions()) {
				if (element.isLastSearchKey() && element.isEditable()) {
					if (isSearch) {
						actionTag = new ActionTag();
						actionTag.setAction(element.getSearchAction());
						actionTag.setArgv("keyProperty="+Ids.undecorate(element.getPropertyKey()));
						actionTag.setPageContext(getPageContext());
						actionTag.doStartTag();
					}
					if (isCreateNew) {
						actionTag = new ActionTag();
						actionTag.setAction("Reference.createNew");
						actionTag.setArgv("model=" + 
							element.getMetaMember().getMetaModel().getName() + ",keyProperty="+Ids.undecorate(element.getPropertyKey()));
						actionTag.setPageContext(getPageContext());
						actionTag.doStartTag();
					}
					if (isModify) {
						actionTag = new ActionTag();
						actionTag.setAction("Reference.modify");
						actionTag.setArgv("model=" + 
								element.getMetaMember().getMetaModel().getName() + ",keyProperty="+Ids.undecorate(element.getPropertyKey()));
						actionTag.setPageContext(getPageContext());
						actionTag.doStartTag();
					}
				}
			}
		} catch (JspException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		try {
			if (element.isEditable() && element.getActionsNameForReference() != null
					&& element.getActionsNameForReference().size() > 0) {
				Iterator it = element.getActionsNameForReference().iterator();
				while(it.hasNext()) {
					String action = (String) it.next();
					actionTag = new ActionTag();
					actionTag.setAction(action);
					actionTag.setPageContext(getPageContext());
					actionTag.doStartTag();
					actionTag.doEndTag();
				}
			}
			if (element.getActionsNameForProperty() != null 
					&& element.getActionsNameForProperty().size() > 0) {
				Iterator it = element.getActionsNameForProperty().iterator();
				while(it.hasNext()) {
					String action = (String) it.next();
					actionTag = new ActionTag();
					actionTag.setAction(action);
					actionTag.setArgv("xava.keyProperty=" + Ids.undecorate(element.getPropertyKey()));
					actionTag.setPageContext(getPageContext());
					actionTag.doStartTag();
					actionTag.doEndTag();
				}
			}
		} catch (JspException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
		
	}
	/**
	 * Display element as description list.
	 * @param element Element to be displayed.
	 */
	private void beginReferenceData(ILayoutPropertyBeginElement element) {
		ModuleContext context = (ModuleContext) getPageContext().getSession().getAttribute("context");
		View view = (View)context.get(getRequest(), element.getViewObject());
		try {
			MetaReference metaReference = view.getMetaReference(element.getReferenceForDescriptionsList());
			String referenceKey = Ids.decorate(getRequest(), element.getPropertyKey());
			getRequest().setAttribute(referenceKey, metaReference);
			String editorURL = "reference.jsp"
					+ "?referenceKey=" + referenceKey 
					+ "&onlyEditor=true"
					+ "&frame=false"
					+ "&composite=false"
					+ "&descriptionsList=" + element.isDisplayAsDescriptionsList()
					+ "&viewObject=" + view.getViewObject(); 
			includeJspPage(editorURL);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Paints the input controls.
	 * @param element Element to be painted.
	 */
	protected void beginMetaViewActionData(ILayoutPropertyBeginElement element) {
		getRequest().setAttribute(element.getPropertyKey(), element.getMetaMember());
		MetaViewAction metaViewAction = (MetaViewAction) element.getMetaProperty();
		attributes.clear();
		attributes.put("id", Ids.decorate(getRequest(), "")
				+ ("editor_" 
				+ element.getPropertyPrefix()
				+ (!Is.emptyString(element.getPropertyPrefix()) ? "__" : "")
				+ "__ACTION__" + metaViewAction.getAction()).replaceAll("\\.", "_"));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));

		
		String editorURL = "editors/actionEditor.jsp"
				+ "?propertyKey=" + element.getPropertyKey() 
				+ "&editable=" + element.isEditable()
				+ "&viewObject=" + element.getViewObject(); 
		includeJspPage(editorURL);
		
		write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
	}
	
	/**
	 * In this implementation nothing is done at cell end.
	 * @see org.openxava.web.layout.ILayoutPainter#endProperty(org.openxava.web.layout.LayoutElement)
	 */
	public void endProperty(ILayoutPropertyEndElement element) {

	}

	/**
	 * Besides the frame handling, it lets the collection.jsp to take care of the collection rendering.
	 * @see org.openxava.web.layout.ILayoutPainter#beginCollection(org.openxava.web.layout.LayoutElement)
	 */
	public void beginCollection(ILayoutCollectionBeginElement element) {
		String propertyPrefix = element.getView().getPropertyPrefix();
		String collectionPrefix = propertyPrefix == null ? element.getMetaCollection().getName() + "." :
				propertyPrefix + element.getMetaCollection().getName() + ".";
		String collectionId = Ids.decorate(getRequest(), "collection_" + collectionPrefix);
		attributes.clear();
		startTd();
		if (element.hasFrame()) {
			write(getStyle().getFrameHeaderStartDecoration(100));
			write(getStyle().getFrameTitleStartDecoration());
			write(element.getLabel());
			String frameId = Ids.decorate(getRequest(), "frame_" + element.getView().getPropertyPrefix() + element.getMetaCollection().getName());
			String collectionHeaderId = frameId + "header";
			attributes.clear();
			attributes.put("id", collectionHeaderId);
			write(LayoutJspUtils.INSTANCE.startTag(TAG_SPAN, attributes));
			includeJspPage("collectionFrameHeader.jsp" 
				+ "?collectionName=" + element.getMetaCollection().getName()
				+ "&viewObject=" + element.getView().getViewObject()
				+ "&propertyPrefix=" + propertyPrefix);
			write(LayoutJspUtils.INSTANCE.endTag(TAG_SPAN));
			write(getStyle().getFrameTitleEndDecoration());
			write(getStyle().getFrameActionsStartDecoration());
			String frameActionsURL = "frameActions.jsp?frameId=" + frameId +
					"&closed=" + element.getView().isFrameClosed(frameId);
			includeJspPage(frameActionsURL);
			write(getStyle().getFrameActionsEndDecoration());
			write(getStyle().getFrameHeaderEndDecoration());
			write(getStyle().getFrameContentStartDecoration(frameId + "content", element.getView().isFrameClosed(frameId)));
		}
		attributes.clear();
		attributes.put("id", collectionId);
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_DIV, attributes));
			includeJspPage("collection.jsp"
					+ "?collectionName=" + element.getMetaCollection().getName() 
					+ "&viewObject=" + element.getView().getViewObject()
					+ "&propertyPrefix=" + propertyPrefix);
		write(LayoutJspUtils.INSTANCE.endTag(TAG_DIV));
		if (element.hasFrame()) {
			write(getStyle().getFrameContentEndDecoration());
		}
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
	}

	/**
	 * Actually all code is performed in the startCollection method.
	 * However for future implementations this might be useful for adding
	 * features to the collection that might be painted at end of the collection
	 * renderization.
	 * @see org.openxava.web.layout.ILayoutPainter#endCollection(org.openxava.web.layout.LayoutElement)
	 */
	public void endCollection(ILayoutCollectionEndElement element) {

	}
	
	/**
	 * Each section behave as a marker. This is called upon section change or page reload. 
	 * @see org.openxava.web.layout.ILayoutPainter#beginSections(org.openxava.web.layout.LayoutElement)
	 */
	public void beginSections(ILayoutSectionsBeginElement element) {
		View view = element.getView().hasSections() ? element.getView() : getView();
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
		attributes.clear();
		attributes.put(ATTR_COLSPAN, Integer.toString(calculateTdSpan(getMaxColumnsOnView())));
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
		attributes.clear();
		attributes.put(ATTR_ID, Ids.decorate(getRequest(), "sections_" + view.getViewObject()));
		attributes.put(ATTR_CLASS, Style.getInstance().getLayoutContent());
		write(LayoutJspUtils.INSTANCE.startTag(TAG_DIV, attributes));
	}
	
	@SuppressWarnings("rawtypes")
	public void beginSectionsRender(ILayoutSectionsRenderBeginElement element) {
		View view = element.getView().hasSections() ? element.getView() : getView();
		View sectionView = view.getSectionView(view.getActiveSection());

		Collection sections = view.getSections();
		int activeSection = view.getActiveSection();
		if (view.getViewObject() == null) {
			view.setViewObject("xava_view");
		}
		attributes.clear();
		attributes.put(ATTR_STYLE, ATTRVAL_STYLE_WIDTH_100P);
		write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
					attributes.clear();
					attributes.put(ATTR_CLASS, getStyle().getSection());
					write(LayoutJspUtils.INSTANCE.startTag(TAG_DIV, attributes));
						attributes.clear();
						attributes.put(ATTR_LIST, getStyle().getSectionTableAttributes());
						write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE, attributes));
							write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
								
								write(getStyle().getSectionBarStartDecoration());
								// Loop to paint section(s)
								Iterator itSections = sections.iterator();
								int i = 0;
								while(itSections.hasNext()) {
									MetaView section = (MetaView) itSections.next();
									if (activeSection == i) {
										write(getStyle().getActiveSectionTabStartDecoration(i == 0, !itSections.hasNext()));
										write(section.getLabel(getRequest()));
										write(getStyle().getActiveSectionTabEndDecoration());
									} else {
										try {
											write(getStyle().getSectionTabStartDecoration(i == 0, !itSections.hasNext()));
											String viewObjectArgv = "xava_view".equals(view.getViewObject())
													? "" : ",viewObject=" + view.getViewObject();
											LinkTag linkTag = new LinkTag();
											linkTag.setAction("Sections.change");
											linkTag.setArgv("activeSection=" + i + viewObjectArgv);
											linkTag.setCssClass(getStyle().getSectionLink());
											linkTag.setCssStyle(getStyle().getSectionLinkStyle());
											linkTag.setPageContext(getPageContext());
											linkTag.doStartTag();
											write(section.getLabel(getRequest()));
											linkTag.doAfterBody();
											linkTag.doEndTag();
											write(getStyle().getSectionTabEndDecoration());
										} catch (JspException e) {
											LOG.error(e.getMessage(), e);
											throw new RuntimeException(e);
										}
									}
									i++;
								}
								write(getStyle().getSectionBarEndDecoration());
							write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
						write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
					write(LayoutJspUtils.INSTANCE.endTag(TAG_DIV));
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
			write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
				attributes.clear();
				attributes.put(ATTR_CLASS, getStyle().getActiveSection());
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TD, attributes));
				attributes.clear();
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TABLE));
				write(LayoutJspUtils.INSTANCE.startTag(TAG_TR));
					write(LayoutJspUtils.INSTANCE.startTag(TAG_TD));
					includeJspPage("detail.jsp"
									+ "?viewObject=" + sectionView.getViewObject()
									+ "&propertyPrefix=" + sectionView.getPropertyPrefix());
	}

	public void endSectionsRender(ILayoutSectionsRenderEndElement element) {
		
				write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
			write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TABLE));
		
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#endSections(org.openxava.web.layout.LayoutElement)
	 */
	public void endSections(ILayoutSectionsEndElement element) {
		write(LayoutJspUtils.INSTANCE.endTag(TAG_DIV));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TD));
		write(LayoutJspUtils.INSTANCE.endTag(TAG_TR));
	}
	
	private int calculateTdSpan(int currentCount) {
		return currentCount * tdPerColumn;
	}
	
	/**
	 * @see org.openxava.web.layout.ILayoutPainter#defaultBeginSectionsRenderElement(org.openxava.web.layout.ILayoutSectionsBeginElement)
	 */
	public ILayoutSectionsRenderBeginElement defaultBeginSectionsRenderElement(View view) {
		return new DefaultLayoutSectionsRenderBeginElement(view, 0);
	}

	/**
	 * @see org.openxava.web.layout.ILayoutPainter#defaultEndSectionsRenderElement(org.openxava.web.layout.ILayoutSectionsBeginElement)
	 */
	public ILayoutSectionsRenderEndElement defaultEndSectionsRenderElement(View view) {
		return new DefaultLayoutSectionsRenderEndElement(view, 0);
	}

}
