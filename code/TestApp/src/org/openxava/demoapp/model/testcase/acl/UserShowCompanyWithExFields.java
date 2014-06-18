package org.openxava.demoapp.model.testcase.acl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.openxava.annotations.Hidden;
import org.openxava.annotations.ListProperties;
import org.openxava.annotations.Tab;
import org.openxava.ex.model.base.BaseMasterDataModel;
import org.openxava.jpa.XPersistence;

/**
 * See {@link ACL_U_C_EX_PostCollectionUpdate}, which handle the "post-collection-update" event to make creator and createTime
 * should be recorded in table "TEST_ACL_U_C_EX";
 */
@Entity
@Table(name="TEST_ACL_USER_EX")
@Tab(defaultOrder="code")
public class UserShowCompanyWithExFields extends BaseMasterDataModel{
	@ManyToMany
	@JoinTable(name="TEST_ACL_U_C_EX",
		joinColumns=@JoinColumn(name="userId"),
		inverseJoinColumns=@JoinColumn(name="companyId")
	)
	@ListProperties("code, name, creator, createTime")
	private Collection<CompanyWithExFields> companies;

	public Collection<CompanyWithExFields> getCompanies() {
		return companies;
	}
	public void setCompanies(Collection<CompanyWithExFields> companies) {
		this.companies = companies;
	}
	
	/**
	 * Every user has different TEST_ACL_U_C_EX informations, now cache it by company Id
	 */
	@Transient @Hidden
	protected Map<String, ACL_U_C_EX> exInfo4User = new HashMap<String, ACL_U_C_EX>();
	
	/**
	 * Need refresh creator and createTime every loading
	 */
	@PostLoad
    public void queryCreateInfo(){
		EntityManager em = XPersistence.getManager();
		for (CompanyWithExFields c: this.companies){
			ACL_U_C_EX exInfo = exInfo4User.get(c.getId());
			if (null==exInfo){
				exInfo = new ACL_U_C_EX();
	    		exInfo.setUserId(getId());
	    		exInfo.setCompanyId(c.getId());
	    		exInfo = em.find(ACL_U_C_EX.class, exInfo);
				exInfo4User.put(c.getId(), exInfo);
			}
    		c.setCreator(exInfo.getCreator());
    		c.setCreateTime(exInfo.getCreateTime());
		}
    }
}
