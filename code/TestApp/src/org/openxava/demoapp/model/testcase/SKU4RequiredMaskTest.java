package org.openxava.demoapp.model.testcase;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openxava.actions.OnChangePropertyBaseAction;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.OnChange;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required;
import org.openxava.demoapp.model.md.UOM;
import org.openxava.demoapp.model.md.Vendor;
import org.openxava.ex.annotation.masks.RequiredMask;
import org.openxava.ex.editor.RequiredMasksHelper;
import org.openxava.ex.model.base.BaseMasterDataModel;
import org.openxava.view.View;

@Entity
@Table(name="TEST_SKU_RequiredMask")
public class SKU4RequiredMaskTest extends BaseMasterDataModel{
	public static enum TestCase {ALL_UOM_IS_REQUIRED, ALL_VENDOR_IS_REQUIRED, CODE_NAME_IS_OPTIONAL}
	
	@Required
	@OnChange(TestCaseChangeAction.class)
	private TestCase testCase;
	
	@RequiredMask
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@ReferenceView("V-UOM-code-name")	//Code and name
	private UOM uom;
	
	@RequiredMask(false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@DescriptionsList(descriptionProperties="code, name")
	@Required
	private Vendor vendor;
	
	@RequiredMask
	private String codeMasked;
	
	@RequiredMask(false)
	@Required
	private String nameMaskedFasle;
	
	//FIXME: requiredMask can't control the subview
	@RequiredMask(false)
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@ReferenceView("V-UOM-code-name")	//Code and name
	@Required
	private UOM uomMaskedFalse;

	@RequiredMask
	@ManyToOne(fetch=FetchType.LAZY, optional=true)
	@DescriptionsList(descriptionProperties="code, name")
	private Vendor vendorMasked;

	public TestCase getTestCase() {
		return testCase;
	}
	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}
	public UOM getUom() {
		return uom;
	}
	public void setUom(UOM uom) {
		this.uom = uom;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public String getCodeMasked() {
		return codeMasked;
	}
	public void setCodeMasked(String codeMasked) {
		this.codeMasked = codeMasked;
	}
	public String getNameMaskedFasle() {
		return nameMaskedFasle;
	}
	public void setNameMaskedFasle(String nameMaskedFasle) {
		this.nameMaskedFasle = nameMaskedFasle;
	}
	public UOM getUomMaskedFalse() {
		return uomMaskedFalse;
	}
	public void setUomMaskedFalse(UOM uomMaskedFalse) {
		this.uomMaskedFalse = uomMaskedFalse;
	}
	public Vendor getVendorMasked() {
		return vendorMasked;
	}
	public void setVendorMasked(Vendor vendorMasked) {
		this.vendorMasked = vendorMasked;
	}

	public static class TestCaseChangeAction extends OnChangePropertyBaseAction{
		public void execute() throws Exception {
			View v = this.getView();
			TestCase tc = (TestCase) v.getValue("testCase");
			if (TestCase.ALL_UOM_IS_REQUIRED.equals(tc)){
				RequiredMasksHelper.setRequiredMask(v, "uom", true);
				RequiredMasksHelper.setRequiredMask(v, "uomMaskedFalse", true);
			}
			if (TestCase.ALL_VENDOR_IS_REQUIRED.equals(tc)){
				RequiredMasksHelper.setRequiredMask(v, "vendor", true);
				RequiredMasksHelper.setRequiredMask(v, "vendorMasked", true);
			}
			if (TestCase.CODE_NAME_IS_OPTIONAL.equals(tc)){
				RequiredMasksHelper.setRequiredMask(v, "code", false);
				RequiredMasksHelper.setRequiredMask(v, "name", false);
			}
		}
	}
}
