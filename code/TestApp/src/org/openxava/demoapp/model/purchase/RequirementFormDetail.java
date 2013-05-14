package org.openxava.demoapp.model.purchase;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openxava.annotations.Depends;
import org.openxava.annotations.Editor;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.NoFrame;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.demoapp.base.BaseBillDetailModel;
import org.openxava.demoapp.model.md.SKU;
import org.openxava.ex.editor.shell.IShellEditor;
import org.openxava.ex.editor.shell.ShellEditorContext;
import org.openxava.jpa.XPersistence;

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
	@Editor("PR_RequireDate_Editor")
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
		public String render(ShellEditorContext ctx) {
			@SuppressWarnings("unchecked")
			String skuId = ((Map<String,String>)ctx.getView().getValue("sku")).get("id");
			SKU sku = XPersistence.getManager().find(SKU.class, skuId);
			return "<div style='background-color:CornflowerBlue;width:240px;height:160px'>"+sku.getDescr()+"</div>";
		}

		public String renderReadOnly(ShellEditorContext ctx) {
			return ctx.getRawValue();
		}
		
	}
}
