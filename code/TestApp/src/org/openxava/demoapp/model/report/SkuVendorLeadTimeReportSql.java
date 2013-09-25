package org.openxava.demoapp.model.report;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.openxava.annotations.NoCreate;
import org.openxava.annotations.NoModify;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Tab;
import org.openxava.annotations.View;
import org.openxava.demoapp.model.md.SKU;
import org.openxava.demoapp.model.md.Vendor;
import org.openxava.ex.annotation.query.Condition;
import org.openxava.ex.annotation.query.FieldProp;
import org.openxava.ex.annotation.query.FieldTmpl;
import org.openxava.ex.annotation.query.FieldTmpls;
import org.openxava.ex.annotation.query.Sql;
import org.openxava.ex.model.base.BaseReportSqlQuery;
import org.openxava.ex.model.pqgrid.PQGridClientModel;
import org.openxava.ex.model.pqgrid.PQGridClientModel.ColModelDetail;

@View(members="#maxLeadTimeDays; sku, vendor; queryResult")
@Tab(properties="*, skuName, vendorCode, *, leadTimeDays, modifyTime") //BP: Use left-*-right to adjust the default column order
@Sql("SELECT sku.id as skuId, sku.code as skuCode, sku.name as skuName, sku.version as modifyTime," + 
	 "       v.code as vendorCode, v.name as vendorName, v.leadTimeDays" +
	 "  FROM md_sku sku, md_vendor v" +
 	 " WHERE sku.vendor_id = v.id" +
	 "   AND sku.enabled = 1" +
	 "   AND ${#Where}" +
	 " ORDER BY 1,3")
@FieldTmpls({
	@FieldTmpl(fieldName="skuId", value={
			@FieldProp(name=PQGridClientModel.HIDDEN, value="true")
		}),
	@FieldTmpl(fieldName="skuName", value={
			@FieldProp(name=PQGridClientModel.WIDTH, value="220"),
			//FIXME: Can't support related url in IE9
			@FieldProp(name=PQGridClientModel.ACTION, value="client:window.TestApp.openJspAction(ui, 'skuId', '/TestApp/TestApp/SkuInfo.jsp?skuId=')")
		}),
	@FieldTmpl(fieldName="VendorName", value={
			@FieldProp(name=PQGridClientModel.WIDTH, value="180"),
			@FieldProp(name=PQGridClientModel.ACTION, value="client:alert('Current Vendor ['+ui.dataModel.data[ui.rowIndx][ui.dataIndx]+']')")
	}),
	@FieldTmpl(fieldName="modifyTime", value={
			@FieldProp(name=PQGridClientModel.PROTOTYPE, value=ColModelDetail.PROTOTYPE_date)
	})
})
public class SkuVendorLeadTimeReportSql extends BaseReportSqlQuery{
	@Column
	@Condition("v.leadTimeDays <= ${maxLeadTimeDays}")
	private Integer maxLeadTimeDays;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true) @NoCreate @NoModify
	@ReferenceView("V-Vendor-Simple") //@NoFrame
	@Condition("v.id = ${vendor.id}")
	private Vendor vendor;

	@ManyToOne(fetch=FetchType.LAZY, optional=true) @NoCreate @NoModify
	@ReferenceView("V-SKU-Simple") //@NoFrame
	@Condition("sku.id = ${sku.id}")
	private SKU sku;

	public SKU getSku() {
		return sku;
	}
	public void setSku(SKU sku) {
		this.sku = sku;
	}
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Integer getMaxLeadTimeDays() {
		return maxLeadTimeDays;
	}
	public void setMaxLeadTimeDays(Integer maxLeadTimeDays) {
		this.maxLeadTimeDays = maxLeadTimeDays;
	}
}
