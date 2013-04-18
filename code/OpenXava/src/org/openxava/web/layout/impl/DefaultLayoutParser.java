/**
 * 
 */
package org.openxava.web.layout.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.controller.ModuleContext;
import org.openxava.model.meta.MetaCollection;
import org.openxava.model.meta.MetaMember;
import org.openxava.model.meta.MetaProperty;
import org.openxava.model.meta.MetaReference;
import org.openxava.util.Is;
import org.openxava.view.View;
import org.openxava.view.meta.MetaGroup;
import org.openxava.view.meta.MetaViewAction;
import org.openxava.view.meta.PropertiesSeparator;
import org.openxava.web.Ids;
import org.openxava.web.WebEditors;
import org.openxava.web.layout.ILayoutCollectionBeginElement;
import org.openxava.web.layout.ILayoutCollectionEndElement;
import org.openxava.web.layout.ILayoutColumnBeginElement;
import org.openxava.web.layout.ILayoutColumnEndElement;
import org.openxava.web.layout.ILayoutContainerElement;
import org.openxava.web.layout.ILayoutElement;
import org.openxava.web.layout.ILayoutFrameBeginElement;
import org.openxava.web.layout.ILayoutFrameEndElement;
import org.openxava.web.layout.ILayoutGroupBeginElement;
import org.openxava.web.layout.ILayoutGroupEndElement;
import org.openxava.web.layout.ILayoutParser;
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
import org.openxava.web.layout.LayoutKeys;
import org.openxava.web.meta.MetaEditor;

/**
 * Layout manager, class to prepare view presentation. 
 * 
 * @author Juan Mendoza and Federico Alcantara
 *
 */
public class DefaultLayoutParser implements ILayoutParser {
	private static final long serialVersionUID = 1L;

	private static Log LOG = LogFactory.getLog(DefaultLayoutParser.class);

	private String groupLabel;
	private List<ILayoutElement> elements;
	private HttpServletRequest request;
	private ModuleContext context;
	private boolean editable;
	private int groupLevel;
	private ILayoutRowBeginElement currentRow;
	private ILayoutViewBeginElement currentView;
	private Stack<ILayoutContainerElement> containersStack;
	private Stack<ILayoutRowBeginElement> rowsStack;
	private int rowIndex;
	private List<Integer> columnsPerRow;
	private Map<Integer, Boolean> levelRowStarted;
	private Map<Integer, Boolean> levelMustStartRow;
	
	public DefaultLayoutParser() {
	}
	
	/**
	 * Parses the layout in order to determine its size. No rendering
	 * occurs in this phase.
	 * @param view Originating view.
	 * @param pageContext pageContext.
	 * @return returnValue Integer value containing the count
	 */
	public List<ILayoutElement> parseView(View view, PageContext pageContext) {
		return parseView(view, pageContext, false);
	}
	
	/**
	 * Parses the layout in order to determine its size. No rendering
	 * occurs in this phase.
	 * @param view Originating view.
	 * @param pageContext pageContext.
	 * @param representsSection If true this view is within a section.
	 * @return returnValue Integer value containing the count
	 */
	public List<ILayoutElement> parseView(View view, PageContext pageContext, boolean representsSection) {
		request = (HttpServletRequest)pageContext.getRequest();
		context = (ModuleContext) request.getSession().getAttribute("context");
		context.put(request, view.getViewObject(), view);
		String propertyPrefix = view.getPropertyPrefix();
		view.setPropertyPrefix("");
		parseLayout(view, representsSection, propertyPrefix);
		view.setPropertyPrefix(propertyPrefix);
		if (LOG.isDebugEnabled()) {
			StringBuffer buffer = new StringBuffer("\n\n");
			for (ILayoutElement readElement : elements) {
				buffer.append(StringUtils.repeat("    ", readElement.getGroupLevel()) 
						+ readElement.toString());
				buffer.append('\n');
			}
			LOG.debug(buffer.toString());
		}
		return elements;
	}

	/**
	 * Calculate cells by rows.
	 * 
	 * @param view. View to process it metamembers.
	 */
	protected void parseLayout(View view, boolean representsSection, String inputPropertyPrefix) {
		rowIndex = -1;
		groupLevel = 0;
		elements = new ArrayList<ILayoutElement>();
		containersStack = new Stack<ILayoutContainerElement>();
		rowsStack = new Stack<ILayoutRowBeginElement>();
		columnsPerRow = new ArrayList<Integer>();
		levelRowStarted = new HashMap<Integer, Boolean>();
		levelMustStartRow = new HashMap<Integer, Boolean>();
		currentRow = null;
		editable = false;
		currentView = createBeginViewMarker(view);
		currentView.setRepresentsSection(representsSection);
		addLayoutElement(currentView);
		parseMetamembers(view.getMetaMembers(), view, false, true, inputPropertyPrefix);
		addLayoutElement(createEndViewMarker(view));
	}


	/**
	 * Parses each meta member in order to get a hint of the view to display.
	 * 
	 * @param metaMembers. Metamembers to processed.
	 * @param view. View to be processed.
	 * @param descriptionsList. True if the meta property is a descriptionsList
	 * @param isGrouped. True if this parsing should be treated as a group.
	 * 
	 */
	@SuppressWarnings("rawtypes")
	protected void parseMetamembers(Collection metaMembers, View view, boolean isDescriptionsList, boolean isGrouped,
			String inputPropertyPrefix) {
		boolean displayAsDescriptionsList = isDescriptionsList;
		boolean frameOnSameColumn = false;
		int frameStartingRowIndex = -1;
		int frameMaxEndingRowIndex = -1;
		setCurrentMustStartRow(isGrouped || rowsStack.isEmpty());
		
		Iterator it = metaMembers.iterator();
		while (it.hasNext()) {
			MetaMember m = (MetaMember) it.next();
			ILayoutElement element = null;
			if (!PropertiesSeparator.INSTANCE.equals(m)) {

		  		if (frameOnSameColumn) {
		  			rowIndex = frameStartingRowIndex;
		  		}

				if (m instanceof MetaProperty) {					
					MetaProperty p = (MetaProperty) m;
					if (view.isHidden(p.getName())) {
						continue;
					}
					ILayoutElement last = elements.size() > 0 ? elements.get(elements.size() - 1) : null;
					MetaViewAction action = (p instanceof MetaViewAction) ? (MetaViewAction) p : null;
					boolean createPropertyOnSameColumn = action != null && !currentMustStartRow() && currentRowStarted()
							&& (last instanceof ILayoutColumnEndElement);

					setEditable(view.isEditable(p));
					if (createPropertyOnSameColumn) {
						elements.remove(elements.size() - 1);
						groupLevel++;
					} else {
						addLayoutElement(createBeginColumnMarker(view));
					}
					int rowIndexBeforeFrame = rowIndex;
					boolean hasFrame = WebEditors.hasFrame(p, view.getViewName());
					if (hasFrame) {
				  		addLayoutElement(createBeginFrameMarker(p, view, ""));
				  		addLayoutElement(createBeginRowMarker(view));
					}
					element = createBeginPropertyMarker(p, displayAsDescriptionsList, hasFrame, view,
							inputPropertyPrefix); // hasFrame:true = suppress label
					addLayoutElement(element);
					addLayoutElement(createEndPropertyMarker(view));
					if (hasFrame) {
				  		addLayoutElement(createEndFrameMarker(p, view));
				  		rowIndex = rowIndexBeforeFrame;
					}
					addLayoutElement(createEndColumnMarker(view));
					
					this.groupLabel = "";
					if (displayAsDescriptionsList) {
						break; // Only one
					}

				} else if (m instanceof MetaReference) {
					MetaReference ref = (MetaReference) m;
					if (view.isHidden(ref.getName())) {
						continue;
					}
					try {
						View subView = view.getSubview(ref.getName());
						subView.setPropertyPrefix(view.getPropertyPrefix() + ref.getName() + ".");
						subView.setViewObject(getViewObject(view) + "_" + ref.getName());
						boolean isReferenceAsDescriptionsList = view.displayAsDescriptionsList(ref);
						MetaEditor metaEditor = WebEditors.getMetaEditorFor(ref, view.getViewName());
						boolean isFramed = isReferenceAsDescriptionsList ? false : subView.isFrame();
						if (isFramed && !"referenceEditor.jsp".equalsIgnoreCase(metaEditor.getUrl())) {
							isFramed = !view.displayReferenceWithNoFrameEditor(ref);
						}
						context.put(request, subView.getViewObject(), subView);
						if (isFramed) {
							addLayoutElement(createBeginColumnMarker(view));
					  		addLayoutElement(createBeginFrameMarker(ref, view, ""));
					  		frameOnSameColumn = true;
						}
						
						frameStartingRowIndex = rowIndex;
						
						groupLabel = ref.getLabel();
							if (isReferenceAsDescriptionsList && subView.getMetaMembers().isEmpty()) {
								addLayoutElement(createBeginColumnMarker(view));
								addLayoutElement(createBeginPropertyMarker(ref, isReferenceAsDescriptionsList, false, subView,
										subView.getPropertyPrefix()));
								addLayoutElement(createEndColumnMarker(view));
							} else {
								if ("referenceEditor.jsp".equalsIgnoreCase(metaEditor.getUrl())) {
									parseMetamembers(subView.getMetaMembers(), subView, isReferenceAsDescriptionsList, isFramed 
											|| (currentRowStarted() && !isReferenceAsDescriptionsList) 
											|| (!currentRowStarted() && currentMustStartRow()),
											subView.getPropertyPrefix());
								} else { // uses it owns reference editor
									addLayoutElement(createBeginColumnMarker(view));
									addLayoutElement(createBeginReferenceMarker(ref, isReferenceAsDescriptionsList, false, view,
											view.getPropertyPrefix()));
									addLayoutElement(createEndColumnMarker(view));
								}
							}
						if (isFramed) {
					  		addLayoutElement(createEndFrameMarker(ref, view));
							addLayoutElement(createEndColumnMarker(view));
							if (rowIndex > frameMaxEndingRowIndex) {
								frameMaxEndingRowIndex = rowIndex;
							}
						}
						
					} catch (Exception ex) {
						LOG.info("Sub-view not found: " + ref.getName());
					}
			  	} else if (m instanceof MetaGroup) {
					MetaGroup group = (MetaGroup) m;
					View subView = view.getGroupView(group.getName());
					groupLabel = group.getLabel(request);
					if (subView != null) {
						subView.setPropertyPrefix(view.getPropertyPrefix());
						subView.setViewObject(getViewObject(view) + "_" + group.getName());
					}

					frameOnSameColumn = true;
					frameStartingRowIndex = rowIndex;
					
					context.put(request, subView.getViewObject(), subView);
					addLayoutElement(createBeginColumnMarker(view));
			  		addLayoutElement(createBeginGroupMarker(group, subView, groupLabel));
					parseMetamembers(group.getMetaView().getMetaMembers(), subView, false, true,
							subView.getPropertyPrefix());
					if (rowIndex > frameMaxEndingRowIndex) {
						frameMaxEndingRowIndex = rowIndex;
					}
			  		addLayoutElement(createEndGroupMarker(group, subView));
					addLayoutElement(createEndColumnMarker(view));
				} else if (m instanceof MetaCollection) {
					addLayoutElement(createBeginColumnMarker(view));
					addLayoutElement(createBeginCollectionMarker(m, view));
					addLayoutElement(createEndCollectionMarker(view));
					addLayoutElement(createEndColumnMarker(view));
				}				
			} else {
				if (currentRowStarted()) {
					addLayoutElement(createEndRowMarker(view));
				}
				if (frameOnSameColumn) {
					rowIndex = frameMaxEndingRowIndex;
				}
				frameOnSameColumn = false;
			}
		}
		if (frameOnSameColumn) {
			rowIndex = frameMaxEndingRowIndex;
		}
		
		if (view.hasSections()) {
			if (currentRowStarted()) {
				addLayoutElement(createEndRowMarker(view));
			}
			addLayoutElement(createBeginRowMarker(view));
			addLayoutElement(createBeginSectionMarker(view));
			addLayoutElement(createBeginSectionRender(view));
			for (int index = 0; index < view.getSections().size(); index++) {
				View childSection = view.getSectionView(index);
				childSection.setPropertyPrefix(inputPropertyPrefix);
				childSection.setViewObject(view.getViewObject() + "_section" + index);
				context.put(request, childSection.getViewObject(), childSection);
			}
			addLayoutElement(createEndSectionRender(view));
			addLayoutElement(createEndSectionMarker(view));
		}

	}

	/**
	 * Method to get the viewObject of a view.
	 * 
	 * @param view. View to process.
	 * @return returnValue. Value with viewobject.
	 */
	protected String getViewObject(View view) {
		String returnValue = view.getViewObject();
		if (returnValue == null) {
			returnValue = LayoutKeys.LAYOUT_DEFAULT_VIEW_NAME;
		}
		return returnValue;
	}

	/**
	 * Creates a begin view marker.
	 * @param view Originating view.
	 * @return Created layout element.
	 */
	protected ILayoutViewBeginElement createBeginViewMarker(View view) {
		ILayoutViewBeginElement returnValue = new DefaultLayoutViewBeginElement(view, groupLevel);
		beginContainerProcess(returnValue);
		return returnValue;
	}
	
	/**
	 * Creates an end view marker.
	 * @param view Originating view.
	 * @return Created layout element.
	 */
	protected ILayoutViewEndElement createEndViewMarker(View view) {
		endContainerProcess(view);
		return new DefaultLayoutViewEndElement(view, groupLevel);
	}
	
	/**
	 * Creates a begin group marker.
	 * @param metaGroup Container metagroup
	 * @param view Originating view.
	 * @param label Label to use on the meta group.
	 * @return An instance of ILayoutGroupBeginElement.
	 */
	protected ILayoutGroupBeginElement createBeginGroupMarker(MetaGroup metaGroup, View view, String label) {
		ILayoutGroupBeginElement returnValue = new DefaultLayoutGroupBeginElement(view, groupLevel, metaGroup);
		beginContainerProcess(returnValue);
		return returnValue;
	}
	
	/**
	 * Creates an end group marker.
	 * @param metaGroup Container metagroup
	 * @param view Originating view.
	 * @return An instance of ILayoutGroupEndElement.
	 */
	protected ILayoutGroupEndElement createEndGroupMarker(MetaGroup metaGroup, View view) {
		endContainerProcess(view);
		ILayoutGroupEndElement returnValue = new DefaultLayoutGroupEndElement(view, groupLevel, metaGroup);
		return returnValue;
	}
	
	/**
	 * Creates a begin frame marker.
	 * @param metaMember Metamember containing the elements.
	 * @param view Originating view.
	 * @param label Label to use for the frame.
	 * @return An instance of ILayoutFrameBeginElement.
	 */
	protected ILayoutFrameBeginElement createBeginFrameMarker(MetaMember metaMember, View view, String label) {
		ILayoutFrameBeginElement returnValue = new DefaultLayoutFrameBeginElement(view, groupLevel, metaMember);
		beginContainerProcess(returnValue);
		return returnValue;
	}
	
	/**
	 * Creates and end frame marker.
	 * @param metaMember Metamember containing the elements.
	 * @param view Originating view.
	 * @return An instance of ILayoutFrameEndElement.
	 */
	protected ILayoutFrameEndElement createEndFrameMarker(MetaMember metaMember, View view) {
		endContainerProcess(view);
		ILayoutFrameEndElement returnValue = new DefaultLayoutFrameEndElement(view, groupLevel, metaMember);
		return returnValue;
	}


	/**
	 * Creates the start of row.
	 * @param view Current view;
	 */
	protected ILayoutRowBeginElement createBeginRowMarker(View view) {
		if ((columnsPerRow.size() - 1) <= rowIndex) {
			columnsPerRow.add(0);
		}
		rowIndex++;
		rowsStack.push(currentRow);
		currentRow = new DefaultLayoutRowBeginElement(view, groupLevel);
		levelRowStarted.put(groupLevel, true);
		levelMustStartRow.put(groupLevel, false);
		currentRow.setRowIndex(rowIndex);
		groupLevel++;
		return currentRow;
	}
	
	/**
	 * Creates the end of row.
	 * @param view
	 * @param cellsCount
	 */
	private ILayoutRowEndElement createEndRowMarker(View view) {
		ILayoutRowEndElement returnValue = null;
		levelRowStarted.put(groupLevel, false);
		levelMustStartRow.put(groupLevel, true);
		groupLevel--;
		ILayoutElement last = null;
		if (elements.size() > 0) {
			last = elements.get(elements.size() - 1);
		}
		if (last != null && (last instanceof ILayoutRowEndElement)) { // empty row
			elements.remove(elements.size() - 1);
		} else {
			returnValue = new DefaultLayoutRowEndElement(view, groupLevel);
			if (groupLevel <= 2
					&& currentRow.getMaxFramesCount() > currentView.getMaxFramesCount()) {
				 currentView.setMaxFramesCount(currentRow.getMaxFramesCount());
			}
			containersStack.peek().getRows().add(currentRow);
		}
		currentRow = rowsStack.pop();
		Boolean currentRowStarted = levelRowStarted.get(groupLevel); 
		Boolean currentMustStartRow = levelMustStartRow.get(groupLevel);
		if (currentRow == null || currentRowStarted == null) {
			currentRowStarted = false;
			currentMustStartRow = true;
		}
		levelRowStarted.put(groupLevel, currentRowStarted);
		levelMustStartRow.put(groupLevel, currentMustStartRow);
		return returnValue;
	}

	/**
	 * Create element for section begin.
	 * 
	 * @param view. View object.
	 * @return A Layout Element of type SECTIONS_BEGIN
	 */
	protected ILayoutSectionsBeginElement createBeginSectionMarker(View view) {
		ILayoutSectionsBeginElement returnValue = new DefaultLayoutSectionsBeginElement(view, groupLevel);
		groupLevel++;
		return returnValue;
	}
	
	/**
	 * Create element for section end.
	 * 
	 * @param view. View object.
	 * @return A Layout Element of type SECTIONS_BEGIN
	 */
	protected ILayoutSectionsEndElement createEndSectionMarker(View view) {
		groupLevel--;
		return new DefaultLayoutSectionsEndElement(view, groupLevel);
	}

	
	/**
	 * Create element for section begin.
	 * 
	 * @param view. View object.
	 * @return A Layout Element of type SECTIONS_BEGIN
	 */
	protected ILayoutSectionsRenderBeginElement createBeginSectionRender(View view) {
		ILayoutSectionsRenderBeginElement returnValue = new DefaultLayoutSectionsRenderBeginElement(view, groupLevel);
		beginContainerProcess(returnValue);
		return returnValue;
	}
	
	/**
	 * Create element for section end.
	 * 
	 * @param view. View object.
	 * @return A Layout Element of type SECTIONS_BEGIN
	 */
	protected ILayoutSectionsRenderEndElement createEndSectionRender(View view) {
		endContainerProcess(view);
		return new DefaultLayoutSectionsRenderEndElement(view, groupLevel);
	}

	/**
	 * Common routine for handling the start of containers (view, group, frames).
	 * @param frameElement
	 */
	protected void beginContainerProcess(ILayoutContainerElement frameElement) {
		groupLevel++;
		if (currentRow != null) {
			int framesCount = currentRow.getMaxFramesCount() + 1;
			currentRow.setMaxFramesCount(framesCount);
		}
		containersStack.push(frameElement);
	}
	
	/**
	 * Inspect the rows of the container and marks the start and end of blocks.
	 */
	protected void endContainerProcess(View view) {
		Boolean started = levelRowStarted.get(groupLevel - 1);
		if (started == null) {
			started = false;
		}
		if (started) {
			addLayoutElement(createEndRowMarker(view));
		}
		if (containersStack.peek().getRows().size() > 0) {
			boolean blockStarted = false;
			ILayoutRowBeginElement lastRow = null;
			// Mark first and last row.
			containersStack.peek().getRows().get(0).setFirst(true);
			containersStack.peek().getRows().get(containersStack.peek().getRows().size() - 1)
					.setLast(true);
			
			for (ILayoutRowBeginElement rowBegin: containersStack.peek().getRows()) {
				rowBegin.setBlockEnd(false);
				if (rowBegin.getMaxFramesCount() > 0) {
					rowBegin.setBlockStart(false);
					if (lastRow != null) {
						lastRow.setBlockEnd(true);
						lastRow = null;
					}
					blockStarted = false;
				} else {
					if (!blockStarted) {
						rowBegin.setBlockStart(true);
						blockStarted = true;
					}
					lastRow = rowBegin;
				}
			}
			if (lastRow != null && lastRow.getMaxFramesCount() == 0) {
				lastRow.setBlockEnd(true);
			}
		}
		containersStack.pop();
		groupLevel--;
	}
	
	
	/**
	 * Create a marker for the beginning of a column
	 * @param view Current view object
	 * @return A 
	 */
	protected ILayoutColumnBeginElement createBeginColumnMarker(View view) {
		if (currentMustStartRow()) {
			if (currentRowStarted()) {
				addLayoutElement(createEndRowMarker(view));
			}
			addLayoutElement(createBeginRowMarker(view));
		}
		ILayoutColumnBeginElement returnValue = new DefaultLayoutColumnBeginElement(view, groupLevel);
		// Add to column
		int maxRowColumnsCount = currentRow.getMaxRowColumnsCount() + 1;
		currentRow.setMaxRowColumnsCount(maxRowColumnsCount);
		
		// Add to indexedRow
		int rowIndex = currentRow.getRowIndex();
		int columnsPerIndexedRow = columnsPerRow.get(rowIndex) + 1;
		columnsPerRow.set(rowIndex, columnsPerIndexedRow);
		
		// Add to container
		int maxContainerColumnsCount = containersStack.peek().getMaxContainerColumnsCount();
		if (columnsPerIndexedRow > maxContainerColumnsCount) {
			containersStack.peek().setMaxContainerColumnsCount(columnsPerIndexedRow);
		}
		
		// Add to view
		int maxViewColumnsCount = currentView.getMaxContainerColumnsCount();
		if (columnsPerIndexedRow > maxViewColumnsCount) {
			currentView.setMaxContainerColumnsCount(columnsPerIndexedRow);
		}
		
		groupLevel++;
		return returnValue;
	}
	
	/**
	 * Creates the marker for column end.
	 * @param view Current view.
	 * @return A layout element of type COLUMN_END
	 */
	protected ILayoutColumnEndElement createEndColumnMarker(View view) {
		groupLevel--;
		return new DefaultLayoutColumnEndElement(view, groupLevel);
	}

	/**
	 * Method to create layout elements.
	 * 
	 * @param m. Metamember to process
	 * @param section. If view is a section
	 * @param frame. If view has frame.
	 * @param descriptionsList. If view must be display as description list.
	 * @param view. View with special meaning.
	 * @return returnValue. Layout element of type PROPERTY_BEGIN.
	 */
	protected ILayoutPropertyBeginElement createBeginPropertyMarker(MetaMember m, boolean descriptionsList, 
			boolean suppressLabel, View view, String inputPropertyPrefix) {
		ILayoutPropertyBeginElement returnValue = new DefaultLayoutPropertyBeginElement(view, groupLevel);
		
		String referenceForDescriptionsList = "";
		String propertyPrefix = inputPropertyPrefix;
		String propertyLabel = null;
		boolean isKey = false;
		boolean isSearchKey = false;
		boolean isLastSearchKey = false;
		boolean isSearch = false;
		boolean throwsChanged = false;
		boolean hasActions = false;
		int labelFormat = 0;
		
		if (Is.empty(propertyPrefix)) {
			propertyPrefix = view.getPropertyPrefix();
		}

		MetaProperty p = null;
		if (m instanceof MetaProperty) {
			p = (MetaProperty) m;
			isKey = p.isKey();
			isSearchKey = p.isSearchKey();
			isLastSearchKey = view.isLastSearchKey(p);
			throwsChanged = view.throwsPropertyChanged(p);
			hasActions = view.propertyHasActions(p);
			labelFormat = view.getLabelFormatForProperty(p);
			returnValue.setMetaProperty(p);
			if (descriptionsList && !Is.emptyString(propertyPrefix)) {
				referenceForDescriptionsList = propertyPrefix.substring(0, propertyPrefix.length() - 1);
				if (referenceForDescriptionsList.contains(".")) {
					referenceForDescriptionsList = referenceForDescriptionsList.substring(referenceForDescriptionsList.lastIndexOf('.') + 1);
				}
			}
		}
		
		if (descriptionsList && !Is.emptyString(propertyPrefix)) {
			propertyPrefix = propertyPrefix.substring(0, propertyPrefix.length() - 1);
			if (propertyPrefix.contains(".")) {
				propertyPrefix = propertyPrefix.substring(0, propertyPrefix.lastIndexOf(".") + 1);
			} else {
				propertyPrefix = "";
			}
		}

		MetaReference ref = null;
		if (m instanceof MetaReference) {
			ref = (MetaReference) m;
			isKey = ref.isKey();
			isSearchKey = ref.isSearchKey();
			isLastSearchKey = view.isLastSearchKey(ref.getName());
			throwsChanged = view.throwsReferenceChanged(ref);
			labelFormat = view.getLabelFormatForReference(ref);
			returnValue.setMetaReference(ref);
			referenceForDescriptionsList = (Is.empty(propertyPrefix) ? "" : propertyPrefix + ".")  + ref.getName();
			returnValue.setReferenceForDescriptionsList(ref.getName());
		}
		
		if (!suppressLabel) {
			propertyLabel = descriptionsList ? groupLabel : view.getLabelFor(m);
			if (propertyLabel == null) {
				propertyLabel = m.getLabel();
			}
		}
		String propertyKey= Ids.decorate(
				request.getParameter("application"),
				request.getParameter("module"),
				propertyPrefix + 
				(descriptionsList ? referenceForDescriptionsList : m.getName()));
				
		try {
			if ((isKey || (isSearchKey && isLastSearchKey))
					&& view.isRepresentsEntityReference()) {
				isSearch = view.isSearch();
				returnValue.setSearch(isSearch);
				returnValue.setCreateNew(view.isCreateNew());
				returnValue.setModify(view.isModify());
			}
			if (view.getPropertyPrefix() == null) {
				view.setPropertyPrefix("");
			}
			returnValue.setName(m.getName());
			returnValue.setEditable(isEditable());//(view.isEditable(p)); // Must confirm this
			returnValue.setSearchAction(view.getSearchAction());
			returnValue.setLabel(propertyLabel);
			returnValue.setLabelFormat(labelFormat);
			returnValue.setThrowPropertyChanged(throwsChanged);
			returnValue.setPropertyKey(propertyKey);
			returnValue.setPropertyPrefix(propertyPrefix);
			returnValue.setLastSearchKey(isLastSearchKey);
			returnValue.setSearch(isSearch && (isSearchKey || isLastSearchKey));
			returnValue.setDisplayAsDescriptionsList(descriptionsList);
			if (descriptionsList) {
				View parentView = view.getParent();
				if (parentView != null) {
					returnValue.setViewObject(parentView.getViewObject());
				}
			}
			if (returnValue.isEditable()) {
				returnValue.setActionsNameForReference(getActionsNameForReference(view, isLastSearchKey));
			}
			returnValue.setActionsNameForProperty(getActionsNameForProperty(view, p, returnValue.isEditable()));
			returnValue.setReferenceForDescriptionsList(referenceForDescriptionsList);
			returnValue.setActions(hasActions ||
					(returnValue.getActionsNameForReference() != null &&
					!returnValue.getActionsNameForReference().isEmpty()) ||
					(returnValue.getActionsNameForProperty() != null &&
					!returnValue.getActionsNameForProperty().isEmpty()));
		} catch (Exception ex) {
			LOG.warn("Maybe this is a separator:" + p.getName());
		}
		return returnValue;
	}

	protected ILayoutPropertyBeginElement createBeginReferenceMarker(MetaMember m, boolean descriptionsList, 
			boolean suppressLabel, View view, String inputPropertyPrefix) {
		ILayoutPropertyBeginElement returnValue = 
				createBeginPropertyMarker(m, descriptionsList, suppressLabel, view, inputPropertyPrefix);
		return returnValue;
	}
	
	/**
	 * Creates an end property marker.
	 * @param view Current view.
	 * @return Layout element of type PROPERTY_END.
	 */
	protected ILayoutPropertyEndElement createEndPropertyMarker(View view) {
		return new DefaultLayoutPropertyEndElement(view, groupLevel);
	}
	
	/**
	 * Method to create collection layout elements.
	 * 
	 * @param m. Metamember to process
	 * @param section. If view is a section
	 * @param frame. If view has frame.
	 * @param descriptionsList. If view must be display as description list.
	 * @param view. View with special meaning.
	 * @return returnValue. Layout element of type COLLECTION_BEGIN
	 */
	protected ILayoutCollectionBeginElement createBeginCollectionMarker(MetaMember m, View view) {
		ILayoutCollectionBeginElement returnValue = new DefaultLayoutCollectionBeginElement(view, groupLevel);
		if (view.getMemberName() == null) {
			view.setMemberName("");
		}
		if (view.getPropertyPrefix() == null) {
			view.setPropertyPrefix("");
		}
		MetaCollection collection = (MetaCollection) m;
		returnValue.setMetaCollection(collection);
		returnValue.setLabel(collection.getLabel(request));
		returnValue.setView(view);
		returnValue.setFrame(!view.isSection() || view.getMetaMembers().size() > 1);
		if (currentRow != null) {
			int framesCount = currentRow.getMaxFramesCount() + 1;
			currentRow.setMaxFramesCount(framesCount);
		}
		return returnValue;
	}
	
	/**
	 * Creates a collection end marker.
	 * @param view Current view.
	 * @return A layout element of type COLLECTION_END.
	 */
	protected ILayoutCollectionEndElement createEndCollectionMarker(View view) {
		return new DefaultLayoutCollectionEndElement(view, groupLevel);
	}
	
	
	@SuppressWarnings("unchecked")
	private Collection<String> getActionsNameForProperty(View view, MetaProperty p,
			boolean editable) {
		Collection<String> returnValues = new ArrayList<String>();
		if (p != null) {
			for (java.util.Iterator<String> itActions = view.getActionsNamesForProperty(p, editable).iterator(); itActions.hasNext();) {
				returnValues.add((String) itActions.next());
			}
		}
		return returnValues;
	}

	@SuppressWarnings("unchecked")
	private Collection<String> getActionsNameForReference(View view, boolean lastSearchKey) {
		Collection<String> returnValues = new ArrayList<String>();
		for (java.util.Iterator<String> itActions = view.getActionsNamesForReference(lastSearchKey).iterator(); itActions.hasNext();) {
			returnValues.add((String) itActions.next());
		}
		return returnValues;
	}

	/**
	 * Adds a layout element to the list of displayable objects.
	 * @param e Layout element to add
	 */
	private void addLayoutElement(ILayoutElement e) {
		if (elements == null) {
			elements = new ArrayList<ILayoutElement>();
		}
		if (e != null) {
			elements.add(e);
			LOG.trace(StringUtils.repeat("    ", e.getGroupLevel()) 
				+ e.toString());
		}
	}

	/**
	 * @return the elements
	 */
	public List<ILayoutElement> getElements() {
		return elements;
	}

	/**
	 * @param elements the elements to set
	 */
	public void setElements(List<ILayoutElement> elements) {
		this.elements = elements;
	}

	/**
	 * @return the editable
	 */
	protected boolean isEditable() {
		return editable;
	}

	/**
	 * @param editable the editable to set
	 */
	protected void setEditable(boolean editable) {
		this.editable = editable;
	}

	/**
	 * @return the request
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	protected void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private boolean currentRowStarted() {
		Boolean returnValue = levelRowStarted.get(groupLevel - 1);
		if (returnValue == null) {
			returnValue = false;
		}
		return returnValue;
	}
	
	private boolean currentMustStartRow() {
		Boolean returnValue = levelMustStartRow.get(groupLevel - 1);
		if (returnValue == null) {
			returnValue = true;
		}
		return returnValue;
	}
	
	private void setCurrentMustStartRow(boolean newValue) {
		levelMustStartRow.put(groupLevel, newValue);
	}

}
