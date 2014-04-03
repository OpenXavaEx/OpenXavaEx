package org.openxava.demoapp.model.md;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.openxava.annotations.Tab;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.openxava.demoapp.tracking.SimpleAccessTrackingListener;
import org.openxava.ex.model.base.BaseMasterDataModel;

/**
 * The unit of measure master data
 * @author root
 *
 */
@Entity
@EntityListeners(SimpleAccessTrackingListener.class)
@Table(name="MD_Vendor")
@Tab(baseCondition = "enabled=true", properties="code, name, leadTimeDays, descr")
@Views({
	@View(name="V-Vendor-Simple", members="code; name")
})
public class Vendor extends BaseMasterDataModel{
	@Column
	private int leadTimeDays;

	public int getLeadTimeDays() {
		return leadTimeDays;
	}

	public void setLeadTimeDays(int leadTimeDays) {
		this.leadTimeDays = leadTimeDays;
	}
}
