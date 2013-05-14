package org.openxava.demoapp.model.md;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openxava.annotations.Tab;
import org.openxava.demoapp.base.BaseMasterDataModel;

/**
 * The unit of measure master data
 * @author root
 *
 */
@Entity
@Table(name="MD_Vendor")
@Tab(baseCondition = "enabled=true", properties="code, name, descr")
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
