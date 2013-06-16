package org.openxava.demoapp.model.purchase;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openxava.annotations.Required;
import org.openxava.ex.model.base.BaseBillDetailModel;

@Entity
@Table(name="PO_PRR")
public class RequirementFormRemark extends BaseBillDetailModel{
	@ManyToOne
	private RequirementForm billHead;
	
	@Column
	@Required
	private RemarkType type;

	@Column(length=255)
	@Required
	private String remark;
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public RequirementForm getBillHead() {
		return billHead;
	}
	public void setBillHead(RequirementForm billHead) {
		this.billHead = billHead;
	}
	public RemarkType getType() {
		return type;
	}
	public void setType(RemarkType key) {
		this.type = key;
	}
	
	/** BP: The other method to use Enum - decide value when define, {@link RequirementFormRemark#type} */
	public static enum RemarkType{
		//BP: define enum item with FULL name to make i18n easy
		RequirementFormRemark_T_price(10),
		RequirementFormRemark_T_transport(20),
		RequirementFormRemark_T_stock(30),
		RequirementFormRemark_T_accounting(90),
		RequirementFormRemark_T_others(99);
		
		private int value;    
		private RemarkType(int value) {
			this.value = value;
		}
		public int getValue() {
		    return value;
		}
	}
}
