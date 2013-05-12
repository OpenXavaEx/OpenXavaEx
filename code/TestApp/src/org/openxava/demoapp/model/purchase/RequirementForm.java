package org.openxava.demoapp.model.purchase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openxava.annotations.ListProperties;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;
import org.openxava.demoapp.base.BaseBillModel;

@Entity
@Table(name="PO_PRH")
@Tab(properties="billNo, department, applicant, applyDate, totalAmount, reason")
public class RequirementForm extends BaseBillModel{
	@Column(length=64)
	@Required
	private String department;
	
	@Column(length=64)
	@Required
	private String applicant;
	
	@Column
	@Required
	private Date applyDate;
	
	@Column(length=64)
	private String reason;
	
	@OneToMany(mappedBy=BaseBillModel.RELATIONSHIP_FIELD_BILLHEAD, cascade=CascadeType.ALL)
	//BP: Can use "OrderBy" to change the default record order of detail data
	@OrderBy("requireDate")
	@ListProperties("skuDisplayName, qty, amount, requireDate")
	private Collection<RequirementFormDetail> details = new ArrayList<RequirementFormDetail>();
	
	@OneToMany(mappedBy=BaseBillModel.RELATIONSHIP_FIELD_BILLHEAD, cascade=CascadeType.ALL)
	@ListProperties("type, remark")
	private Collection<RequirementFormRemark> remarks = new ArrayList<RequirementFormRemark>();
	
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getApplicant() {
		return applicant;
	}

	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Collection<RequirementFormDetail> getDetails() {
		return details;
	}

	public void setDetails(Collection<RequirementFormDetail> details) {
		this.details = details;
	}

	public Collection<RequirementFormRemark> getRemarks() {
		return remarks;
	}

	public void setRemarks(Collection<RequirementFormRemark> remarks) {
		this.remarks = remarks;
	}

	/** BP: Calculate TotalAmount with @Transient field */
	//TODO: In default, calculate field display at the end of page.
	@Transient
	@Stereotype("MONEY")
	public BigDecimal getTotalAmount(){
		BigDecimal result = new BigDecimal("0.00");
		if (null==this.getDetails()){
			return result;
		}
		
		for(RequirementFormDetail detail: this.getDetails()){
			result = result.add(detail.getAmount());
		}
		return result;
	}
}
