package org.openxava.demoapp.model.testcase;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Editor;
import org.openxava.annotations.EntityValidator;
import org.openxava.annotations.ListProperties;
import org.openxava.annotations.NoCreate;
import org.openxava.annotations.NoModify;
import org.openxava.annotations.NoSearch;
import org.openxava.annotations.PropertyValue;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Tab;
import org.openxava.annotations.Tabs;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.openxava.calculators.CurrentUserCalculator;
import org.openxava.calculators.ICalculator;
import org.openxava.ex.ctx.BaseTabAndViewContext;
import org.openxava.ex.ctx.OpenXavaContextFilter;
import org.openxava.ex.editor.filter.BaseRequestFilter;
import org.openxava.ex.model.base.BaseMasterDataModel;
import org.openxava.filters.FilterException;
import org.openxava.filters.IFilter;
import org.openxava.util.Messages;
import org.openxava.util.Messages.Type;
import org.openxava.validators.IValidator;

/**
 * administrative division;
 * test case for 
 *  -1. Customize @DescriptionsList with {@link IFilter}
 *  -2. Customize @EntityValidator, use {@link OpenXavaContextFilter} to get current context info
 */
@Entity
@Table(name="TEST_ManyToOne_AdminDiv")
@Tabs({
	// The default Tab
	@Tab(baseCondition = "e.enabled=true", properties="code, name, owner.name, descr, createApp",
	     defaultOrder="${code}"),
	//BP: Customize @Tab in application.xml
 	@Tab(name="ShowCountryOnly",
	     baseCondition = "e.enabled=true AND e.owner IS NULL", properties="code, name, capital.name, createApp, descr",
	     defaultOrder="${code}")
})
@Views({
	//BP: Customize @View in application.xml
	@View(name="ShowCountryOnly", members="code, name, capital; divisions; descr; creator, createApp, enabled")
})
@EntityValidator(value=AdminDivTestCase.Validator.class, properties= {
	 @PropertyValue(name="owner"),
	 @PropertyValue(name="id"),
	 @PropertyValue(name="code"),
	 @PropertyValue(name="name")
})
public class AdminDivTestCase extends BaseMasterDataModel {
	
	/**
	 * The administrative division that current one was owned, for example: province is the owner of general city
	 */
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@Editor("TestApp_DescriptionsList_Owner")	//BP: Customize @DescriptionsList by specify Editor
	@NoCreate @NoModify @NoSearch
	private AdminDivTestCase owner;
	
	/**
	 * The capital, MUST be owned by current administrative division
	 */
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@Editor("TestApp_DescriptionsList_Capital")	// BP: Customize @DescriptionsList by specify Editor
	@NoCreate @NoModify @NoSearch
	private AdminDivTestCase capital;
	
	/**
	 * The administrative divisions which current administrative division owned
	 */
	@OneToMany(mappedBy="owner", cascade=CascadeType.ALL)
	@OrderBy("code")
	@ListProperties("code, name")
	@ReadOnly
	private Collection<AdminDivTestCase> divisions = new ArrayList<AdminDivTestCase>();
	
	@DefaultValueCalculator(value=CurrentUserCalculator.class)
	@ReadOnly
	private String creator;

	@DefaultValueCalculator(value=AdminDivTestCase.CreateAppCalculator.class)
	@ReadOnly
	private String createApp;

	public AdminDivTestCase getOwner() {
		return owner;
	}

	public void setOwner(AdminDivTestCase owner) {
		this.owner = owner;
	}

	public AdminDivTestCase getCapital() {
		return capital;
	}

	public void setCapital(AdminDivTestCase capital) {
		this.capital = capital;
	}

	public Collection<AdminDivTestCase> getDivisions() {
		return divisions;
	}

	public void setDivisions(Collection<AdminDivTestCase> divisions) {
		this.divisions = divisions;
	}
	
	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
	
	public String getCreateApp() {
		return createApp;
	}

	public void setCreateApp(String createApp) {
		this.createApp = createApp;
	}

	/** Referred in {@link editors.xml}, to provide the parameters to filter the "capital" @DescriptionsList */
	public static class CapitalFilter extends BaseRequestFilter{
		private static final long serialVersionUID = 20130827L;
		@Override
		protected void fillParameters(List<Object> parameters)throws FilterException {
			org.openxava.view.View v = this.getEditorContext().getView();
			String thisId = (String) v.getValue("id");
			parameters.add(thisId);		//It's the last parameter in filter
		}
	}
	/** Referred in {@link editors.xml}, to provide the parameters to filter the "owner" @DescriptionsList */
	public static class OwnerFilter extends BaseRequestFilter{
		private static final long serialVersionUID = 20130827L;
		protected void fillParameters(List<Object> parameters)throws FilterException {
			org.openxava.view.View v = this.getEditorContext().getView();
			String thisId = (String) v.getValue("id");
			if (null==thisId) thisId = "not-existed";	//Avoid "=null" in SQL
			parameters.add(thisId);		//It's the first parameter in filter
			parameters.add(thisId);		//It's the second parameter in filter
		}
	}
	
	public static class Validator extends AdminDivTestCase implements IValidator{
		private static final long serialVersionUID = 20130826L;
		public void validate(Messages errors) throws Exception {
			//BP: Use OpenXavaContextFilter.getContext() to get context information
			BaseTabAndViewContext ctx = OpenXavaContextFilter.getContext();
			String module = ctx.getModule();
			
			if ("AdminDivTestCase".equals(module)){
				AdminDivTestCase owner = this.getOwner();
				if (null==owner){
					errors.add(Type.ERROR, "["+this.getCode()+"] - property 'owner' is required");
				}
			}
		}
	}
	
	public static class CreateAppCalculator implements ICalculator{
		private static final long serialVersionUID = 20130828L;

		public Object calculate() throws Exception {
			//BP: Use OpenXavaContextFilter.getContext() to get context information
			BaseTabAndViewContext ctx = OpenXavaContextFilter.getContext();
			
			return ctx.getApplication() + "-" + ctx.getModule();
		}
	}
}
