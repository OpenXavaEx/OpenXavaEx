package org.openxava.demoapp.model.testcase.acl;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * This class is an entity to create ManyToMany table "TEST_ACL_U_C_EX"
 */
@Entity
@Table(name="TEST_ACL_U_C_EX")
public class ACL_U_C_EX implements Serializable{
	private static final long serialVersionUID = 20140616L;

	@Id
	@Column(name="userId", length=32)
	private String userId;

	@Id
	@Column(name="companyId", length=32)
	private String companyId;
	
	@Column(length=32)
	private String creator;
	
	@Column
	private Timestamp createTime;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
}
