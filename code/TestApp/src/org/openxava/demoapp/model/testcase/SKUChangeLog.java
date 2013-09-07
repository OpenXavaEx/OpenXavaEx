package org.openxava.demoapp.model.testcase;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Tab;
import org.openxava.annotations.Tabs;
import org.openxava.demoapp.system.DemoDataSourceMonitor;
import org.openxava.ex.datasource.IDataSourceMonitor;

/**
 * BP: Support VPD with {@link IDataSourceMonitor}, see {@link DemoDataSourceMonitor} for detail
 */
@Entity
@Table(name="TEST_SKU_CHANGE_LOG")
@Tabs({
	@Tab(defaultOrder="e.changeTime desc"),	
	@Tab(name="ShowCurrentUserOnly", baseCondition="e.userName=SYS_CONTEXT('DEMO_APP_CTX', 'USER')", defaultOrder="e.changeTime desc")
})
public class SKUChangeLog {
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)
	private String id;
	
	private String skuId;
	private String skuName;
	private String userName;
	private String action;	//Insert, Update, Remove, ...
	
	private Timestamp changeTime;
	
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Timestamp getChangeTime() {
		return changeTime;
	}
	public void setChangeTime(Timestamp changeTime) {
		this.changeTime = changeTime;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
