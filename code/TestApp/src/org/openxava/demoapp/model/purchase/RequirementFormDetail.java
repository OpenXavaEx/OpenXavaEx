package org.openxava.demoapp.model.purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;

import org.openxava.annotations.Depends;
import org.openxava.annotations.Editor;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.NoFrame;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.demoapp.model.md.SKU;
import org.openxava.ex.editor.shell.IShellEditor;
import org.openxava.ex.editor.shell.ShellEditorContext;
import org.openxava.ex.model.base.BaseBillDetailModel;
import org.openxava.formatters.IFormatter;
import org.openxava.jpa.XPersistence;

import com.alibaba.fastjson.JSON;

@Entity
@Table(name="PO_PRD")
public class RequirementFormDetail extends BaseBillDetailModel{
	@ManyToOne
	private RequirementForm billHead;

	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	//FIXME: @DescriptionsList(descriptionProperties="code, name") can't support @Depends(Change event)
	@ReferenceView("V-SKU-code-name")
	@NoFrame
	@Required
	private SKU sku;
	
	//TODO: Need a @Stereotype "QTY"
	@Column
	@Required
	private BigDecimal qty;
	
	@Column
	@Required
	@Editor("TestApp_PR_RequireDate_Editor")
	@Depends("sku.id")
	private Date requireDate;

	public SKU getSku() {
		return sku;
	}
	public void setSku(SKU sku) {
		this.sku = sku;
	}
	public BigDecimal getQty() {
		return qty;
	}
	
	/** BP: Use @Transient to define the "display only" field, to make SKU display property in {@link RequirementForm#details} */
	@Transient @Hidden
	public String getSkuDisplayName(){
		return this.getSku().getCode() + " - " + this.getSku().getName();
	}
	
	/** BP: Use @Transient to define calculated field, and auto-calculate when qty change, see {@link RequirementForm#details} */
	@Transient
	@Stereotype("MONEY")
	@Depends("sku.id, qty")
	public BigDecimal getAmount(){
		if (null==this.getQty() || null==this.getSku()){
			return new BigDecimal("0.00");
		}
		if (null==this.getSku().getPrice()){
			//FIXME: LAZY-Load cause the sku must reload here
			SKU sku = XPersistence.getManager().find(SKU.class, this.getSku().getId());
			this.getSku().setPrice(sku.getPrice());
		}
		return this.getQty().multiply(this.getSku().getPrice());
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	public Date getRequireDate() {
		return requireDate;
	}
	public void setRequireDate(Date requireDate) {
		this.requireDate = requireDate;
	}
	public RequirementForm getBillHead() {
		return billHead;
	}
	public void setBillHead(RequirementForm billHead) {
		this.billHead = billHead;
	}
	
	public static class RequireDateEditor implements IShellEditor{
		private IFormatter fmt;
		private HttpServletRequest request;
		private ArrayList<Map<String, Object>> treeData;
		private String rawValue;
		private HashSet<String> monthSet;

		private void init(ShellEditorContext ctx){
			this.fmt = ctx.getPropertyFormatter();
			this.request = ctx.getRequest();
			this.monthSet = new HashSet<String>();
			this.treeData = new ArrayList<Map<String,Object>>();
			this.rawValue = ctx.getRawValue();
		}
		public String render(ShellEditorContext ctx) {
			init(ctx);
			
			@SuppressWarnings("unchecked")
			String skuId = ((Map<String,String>)ctx.getView().getValue("sku")).get("id");
			if (null==skuId){
				//BP: The editor should consider null data(always happened when add new)
				return "<i>Please select SKU ...</i>";
			}
			SKU sku = XPersistence.getManager().find(SKU.class, skuId);
			int leadTime = sku.getVendor().getLeadTimeDays();
			
			//BP: Use jdbc connection to access data
			

			for(int i=0; i<30; i++){	//A month after leadTime
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(System.currentTimeMillis() + (leadTime+i)*24*60*60*1000L);
				
				createZTreeNode(cal);
			}
			Map<String, Object> datas = new HashMap<String, Object>();
			
			datas.put("nodes", JSON.toJSONString(this.treeData));
			datas.put("vendor", sku.getVendor().getName());
			datas.put("leadTime", leadTime);
			String result = ctx.parseFtl("RequireDateEditor.ftl", datas, this.getClass());
			
			return result;
		}

		public String renderReadOnly(ShellEditorContext ctx) {
			return ctx.getRawValue();
		}
		
		private void createZTreeNode(Calendar date){
			try {
				//First month day as the tag of month 
				Calendar month = Calendar.getInstance();
				month.setTime(date.getTime());
				month.set(Calendar.DAY_OF_MONTH, 1);
				//ID for date
				String value = fmt.format(request, date.getTime());
				int weekDay = date.get(Calendar.DAY_OF_WEEK);
				String dId = "D-" + value;
				//ID for month
				String mid = month.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
						+ ", " + month.get(Calendar.YEAR);
				
				if (! this.monthSet.contains(mid)){
					Map<String, Object> mData = new HashMap<String, Object>();
					mData.put("id", mid);
					mData.put("name", mid);
					mData.put("open", true);
					mData.put("nocheck", true);
					this.monthSet.add(mid);
					this.treeData.add(mData);
				}
				
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("id", dId);
				data.put("name", value);
				data.put("pId", mid);
				data.put("checked", value.equals(this.rawValue));
				data.put("chkDisabled", !( weekDay >= Calendar.MONDAY && weekDay <= Calendar.FRIDAY ) );
				this.treeData.add(data);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}
}
