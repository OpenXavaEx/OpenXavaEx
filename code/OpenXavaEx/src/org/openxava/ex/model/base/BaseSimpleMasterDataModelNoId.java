package org.openxava.ex.model.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.PreCreate;
import org.openxava.annotations.ReadOnly;
import org.openxava.calculators.CurrentTimestampCalculator;
import org.openxava.ex.calculators.CurrentUserCalculator;
import org.openxava.util.Users;

/**
 * The base model for all kinds of MasterData
 * @author root
 *
 */
@MappedSuperclass
public class BaseSimpleMasterDataModelNoId {
	
	@Version
	private Timestamp version;
	

	@Column(name="insert_date",length=15)
	@ReadOnly
	@DefaultValueCalculator(CurrentTimestampCalculator.class)
	private Timestamp insertDate;
	
	@Column(name="update_date",length=15)
	@ReadOnly
	@DefaultValueCalculator(CurrentTimestampCalculator.class)
	private Timestamp updateDate;
	
	@Column(name="insert_user",length=15)
	@ReadOnly
	@DefaultValueCalculator(CurrentUserCalculator.class)
	private String insertUser;
	
	@Column(name="update_user",length=15)
	@ReadOnly
	@DefaultValueCalculator(CurrentUserCalculator.class)
	private String updateUser;
	
	@PreUpdate
	public void Updateing() {
		this.updateDate=new java.sql.Timestamp (new java.util.Date().getTime());
		this.updateUser=Users.getCurrentUserInfo().getGivenName();
	}
	@PreCreate
	public void Inserting() {
		this.updateDate=new java.sql.Timestamp (new java.util.Date().getTime());
		this.insertDate=this.updateDate;
		this.updateUser=Users.getCurrentUserInfo().getGivenName();
		this.insertUser=this.updateUser;
	}

	public Timestamp getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Timestamp insertDate) {
		this.insertDate = insertDate;
	}

	public Timestamp getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}

	public String getInsertUser() {
		return insertUser;
	}

	public void setInsertUser(String insertUser) {
		this.insertUser = insertUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

}
