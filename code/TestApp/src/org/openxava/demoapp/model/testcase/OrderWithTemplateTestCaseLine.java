package org.openxava.demoapp.model.testcase;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.openxava.actions.OnChangePropertyBaseAction;
import org.openxava.annotations.OnChange;
import org.openxava.annotations.Required;
import org.openxava.demoapp.etc._Link;
import org.openxava.ex.model.base.BaseBillDetailModel;
import org.openxava.view.View;

@Entity
@Table(name="TEST_OrderWithTemplate_Line")
public class OrderWithTemplateTestCaseLine extends BaseBillDetailModel {
	@ManyToOne
	private OrderWithTemplateTestCase billHead;
	
	public static enum PlanType {
		Absolute /*指定绝对日期*/, Relative /*指定相对日期*/;
		public static final String FIRST_LETTERS = "AR";
	}

	@Required
	private String taskName;
	
	@Required
	@Type(type=_Link.EnumLetterType, parameters={
			@Parameter(name="letters", value=PlanType.FIRST_LETTERS),
			@Parameter(name="enumType", value="org.openxava.demoapp.model.testcase.OrderWithTemplateTestCaseLine$PlanType")
	})
	@OnChange(PlanTypeChangeAction.class)
	private PlanType planType;
	
	private Date planCompleteDate;
	
	private Integer planCompleteDays;

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

	public Integer getPlanCompleteDays() {
		return planCompleteDays;
	}
	public void setPlanCompleteDays(Integer planCompleteDays) {
		this.planCompleteDays = planCompleteDays;
	}
	public PlanType getPlanType() {
		return planType;
	}
	public void setPlanType(PlanType planType) {
		this.planType = planType;
	}
	
	public static class PlanTypeChangeAction extends OnChangePropertyBaseAction{
		public void execute() throws Exception {
			//BP: Set fields visible/editable/value dynamic when value change
			View v = this.getView();
			PlanType pt = (PlanType) v.getValue("planType");
			if (PlanType.Absolute.equals(pt)){
				v.setHidden("planCompleteDays", true);
				v.setHidden("planCompleteDate", false);
			}else{
				v.setHidden("planCompleteDays", false);
				v.setHidden("planCompleteDate", true);
			}
		}
	}
}