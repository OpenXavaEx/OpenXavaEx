package org.openxava.demoapp.model.testcase.acl;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.openxava.annotations.ListProperties;
import org.openxava.ex.model.base.BaseMasterDataModel;

@Entity
@Table(name="TEST_ACL_USER")
public class User extends BaseMasterDataModel{
	@ManyToMany
	@JoinTable(name="TEST_ACL_U_C",
	 joinColumns=@JoinColumn(name="userId"),
	 inverseJoinColumns=@JoinColumn(name="companyId")
	)
	@ListProperties("code, name")
	private Collection<Company> companies;

	public Collection<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Collection<Company> companies) {
		this.companies = companies;
	}

}
