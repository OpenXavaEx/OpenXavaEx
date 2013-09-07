package org.openxava.ex.model.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.openxava.annotations.Hidden;
import org.openxava.annotations.ReadOnly;
import org.openxava.util.Users;

/**
 * The base model for Bills which have Create/Update Trace fields
 */
@MappedSuperclass
public class BaseBillModelWithTrace extends BaseBillModel {
	@Column(insertable=true,updatable=false)
	@Hidden @ReadOnly
	private Timestamp createTime;
	
	@Column(insertable=true,updatable=true)
	@Hidden @ReadOnly
	private Timestamp modifyTime;
	
	@Column(insertable=true,updatable=false)
	@Hidden @ReadOnly
	private String creator;
	
	@Column(insertable=true,updatable=true)
	@Hidden @ReadOnly
	private String modifier;
	
	@PrePersist
	private void onInsert(){
		this.createTime = new Timestamp(System.currentTimeMillis());
		this.creator = Users.getCurrent();
		this.onCreating();
	}
	@PreUpdate
	private void onUpdate(){
		this.modifyTime = new Timestamp(System.currentTimeMillis());
		this.modifier = Users.getCurrent();
		this.onSaving();
	}
	
	protected void onCreating(){
		//Do nothing, for override by child
	}
	protected void onSaving(){
		//Do nothing, for override by child
	}
}
