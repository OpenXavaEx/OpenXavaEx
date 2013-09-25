package org.openxava.demoapp.model.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

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
import org.openxava.ex.annotation.query.FieldProp;
import org.openxava.ex.annotation.query.FieldTmpl;
import org.openxava.ex.annotation.query.FieldTmpls;
import org.openxava.ex.model.base.BaseReportQuery;
import org.openxava.ex.model.pqgrid.PQGridClientModel;
import org.openxava.ex.utils.Misc;

@View(members="#recordCount; sku, vendor; queryResult")
@Tab(properties="skuCode, skuName, VendorCode, VendorName, leadTimeDays, *")
@FieldTmpls({
	@FieldTmpl(fieldName="VendorName", value={
		@FieldProp(name=PQGridClientModel.WIDTH, value="180")
	})
})
public class SkuVendorLeadTimeReport extends BaseReportQuery{
	@Column
	private int recordCount;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=true) @NoCreate @NoModify
	@ReferenceView("V-Vendor-Simple") //@NoFrame
	private Vendor vendor;

	@ManyToOne(fetch=FetchType.LAZY, optional=true) @NoCreate @NoModify
	@ReferenceView("V-SKU-Simple") //@NoFrame
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
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int maxLeadTimeDays) {
		this.recordCount = maxLeadTimeDays;
	}
	
	@Override
	protected QueryResult doQuery(_ReportQueryAction action) {
		List<Map<String,Object>> data = new ArrayList<Map<String,Object>>();
		//Create random data
		Map<String, Map<String,Object>> randomData = new TreeMap<String, Map<String,Object>>();
		for(int i=0; i<this.recordCount; i++){
			randomData.put(UUID.randomUUID().toString(), Misc.$attrs(
					"skuCode", "001", "skuName", "电脑", "VendorCode", "HP", "VendorName", "惠普", "leadTimeDays", 30,
					"modifyTime", new Date(System.currentTimeMillis()-new Double(Math.random() * 100D).intValue()*24*60*60*1000),
					"credit", Math.random() * 100000D));
			randomData.put(UUID.randomUUID().toString(), Misc.$attrs(
					"skuCode", "002", "skuName", "打印机", "VendorCode", "HP", "VendorName", "惠普", "leadTimeDays", 30,
					"modifyTime", new Date(System.currentTimeMillis()-new Double(Math.random() * 100D).intValue()*24*60*60*1000),
					"credit", Math.random() * 100000D));
			randomData.put(UUID.randomUUID().toString(), Misc.$attrs(
					"skuCode", "011", "skuName", "存储", "VendorCode", "IBM", "VendorName", "IBM", "leadTimeDays", 60,
					"modifyTime", new Date(System.currentTimeMillis()-new Double(Math.random() * 100D).intValue()*24*60*60*1000),
					"credit", Math.random() * 100000D));
			randomData.put(UUID.randomUUID().toString(), Misc.$attrs(
					"skuCode", "000", "skuName", null, "VendorCode", null, "VendorName", null, "leadTimeDays", 0,
					"modifyTime", new Date(System.currentTimeMillis()-new Double(Math.random() * 100D).intValue()*24*60*60*1000),
					"credit", 0D));
		}
		for(Map<String,Object> line: randomData.values()){
			data.add(line);
		}
		
		//BP: Use LinkedHashMap to defind fields metadata in order
		@SuppressWarnings("serial")
		Map<String, Class<?>> fields = new LinkedHashMap<String, Class<?>>(){{{
			this.put("skuCode", (Class<?>)String.class);
			this.put("leadTimeDays", (Class<?>)Integer.class);
			this.put("modifyTime", (Class<?>)Date.class);
			this.put("credit", (Class<?>)Double.class);
		}}};
		return new QueryResult(SkuVendorLeadTimeReport.class, fields, data);
	}
}
