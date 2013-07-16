package org.openxava.demoapp.model.report;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import org.openxava.ex.annotation.query.Sql;
import org.openxava.ex.model.base.BaseReportSqlQuery;
import org.openxava.ex.utils.Misc;

@View(members="#maxLeadTimeDays; sku, vendor; queryResult")
@Tab(properties="skuCode, skuName, venderCode,venderName, leadTimeDays, *")
@Sql("SELECT sku.code as skuCode, sku.name as skuName, sku.version as modifyTime," + 
	 "       v.code as vendorCode, v.name as vendorName, v.leadTimeDays" +
	 "  FROM md_sku sku, md_vendor v" +
 	 " WHERE sku.vendor_id = v.id" +
	 "   AND sku.enabled = 1" +
	 "   AND ${#Where}" +
	 " ORDER BY 1,3")
public class SkuVendorLeadTimeReportSql extends BaseReportSqlQuery{
	@Column
	@Condition("v.leadTimeDays <= ${maxLeadTimeDays}")
	private int maxLeadTimeDays;
	
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
	public int getMaxLeadTimeDays() {
		return maxLeadTimeDays;
	}
	public void setMaxLeadTimeDays(int maxLeadTimeDays) {
		this.maxLeadTimeDays = maxLeadTimeDays;
	}
	
	@Override
	protected QueryResult doQuery(_ReportQueryAction action) {
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> data = Misc.$list(
				Misc.$attrs("skuCode", "001", "skuName", "电脑", "venderCode", "HP", "venderName", "惠普", "leadTimeDays", 30,
						"modifyTime", new Date(System.currentTimeMillis()-13*24*60*60*1000), "credit", 11231.53),
				Misc.$attrs("skuCode", "002", "skuName", "打印机", "venderCode", "HP", "venderName", "惠普", "leadTimeDays", 30,
						"modifyTime", new Date(System.currentTimeMillis()-7*24*60*60*1000), "credit", 12119.681),
				Misc.$attrs("skuCode", "011", "skuName", "存储", "venderCode", "IBM", "venderName", "IBM", "leadTimeDays", 60,
						"modifyTime", new Date(System.currentTimeMillis()-11*24*60*60*1000), "credit", 7434.3)
		);
		@SuppressWarnings("serial")
		Map<String, Class<?>> fields = new LinkedHashMap<String, Class<?>>(){{{
			this.put("skuCode", (Class<?>)String.class);
			this.put("leadTimeDays", (Class<?>)Integer.class);
			this.put("modifyTime", (Class<?>)Date.class);
			this.put("credit", (Class<?>)Double.class);
		}}};
		return new QueryResult(SkuVendorLeadTimeReportSql.class, fields, data);
	}
}
