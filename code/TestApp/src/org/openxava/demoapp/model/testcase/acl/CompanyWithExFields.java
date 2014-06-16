package org.openxava.demoapp.model.testcase.acl;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openxava.annotations.Hidden;
import org.openxava.ex.model.base.BaseMasterDataModel;

/**
 * Company with extra fields in Many-to-Many list. NOTE: It use the same table as {@link Company}
 */
@Entity
@Table(name="TEST_ACL_COMPANY")
public class CompanyWithExFields extends BaseMasterDataModel{
	@Transient @Hidden
	private String creator;
	
	@Transient @Hidden
	private Timestamp createTime;

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
