package org.openxava.demoapp.model.testcase;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openxava.annotations.Required;
import org.openxava.ex.model.base.BaseBillDetailModel;

@Entity
@Table(name="TEST_OrderWithTemplate_Line")
public class OrderWithTemplateTestCaseLine extends BaseBillDetailModel {
	@ManyToOne
	private OrderWithTemplateTestCase billHead;
	
	@Required
	private String taskName;
	
	@Required
	private Date planCompleteDate;

	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getPlanCompleteDate() {
		return planCompleteDate;
	}
	public void setPlanCompleteDate(Date planCompleteDate) {
		this.planCompleteDate = planCompleteDate;
	}
	public OrderWithTemplateTestCase getBillHead() {
		return billHead;
	}
	public void setBillHead(OrderWithTemplateTestCase billHead) {
		this.billHead = billHead;
	}
}