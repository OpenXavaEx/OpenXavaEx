package org.openxava.school.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.Table;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Required;
import org.openxava.calculators.CurrentDateCalculator;
import org.openxava.calculators.ICalculator;
import org.openxava.calculators.IJDBCCalculator;
import org.openxava.jpa.XPersistence;
import org.openxava.util.IConnectionProvider;

/**
 * 
 * @author Javier Paniza
 */

@Entity
@Table(name="M_Teacher")
public class Teacher {
	
	@Id @Column(length=5) @Required
	@DefaultValueCalculator(AutoNo.class)
	private String id;
	
	@Column(length=40) @Required
	private String name;
	
	@Column(length=255)
	private String descr;
	
	@Column
	@DefaultValueCalculator(CurrentDateCalculator.class)
	private Date registerDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String remarks) {
		this.descr = remarks;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	@SuppressWarnings("serial")
	public static class AutoNo implements IJDBCCalculator{
		private IConnectionProvider provider;

		public void setConnectionProvider(IConnectionProvider provider) {
			this.provider = provider;
		}
		
		@SuppressWarnings("unchecked")
		public Object calculate() throws Exception {
			Query q = XPersistence.getManager().createQuery("SELECT MAX(ID) as _max, COUNT(*) as _count FROM M_Teacher");
			List<Object> res = q.getResultList();
			return null;
		}

	}
}
