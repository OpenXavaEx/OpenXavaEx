package org.openxava.school.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Required;
import org.openxava.calculators.CurrentDateCalculator;
import org.openxava.calculators.IJDBCCalculator;
import org.openxava.util.IConnectionProvider;

/**
 * 
 * @author Javier Paniza
 */

@Entity
@Table(name="M_Teacher")
public class Teacher {
	
	@Id @Column(length=20) @Required @ReadOnly
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
		
		public Object calculate() throws Exception {
			Connection conn = this.provider.getConnection();
			Statement stmt = null;
			ResultSet rs = null;
			try {
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT MAX(REPLACE(ID, 'TH', '')) as the_next, COUNT(*) as the_count FROM M_Teacher");
				rs.next();
				Integer id = rs.getInt("the_next");
				
				DecimalFormat df = new DecimalFormat("0000000000"); 
				return "TH" + df.format(id+1);
			} finally {
				if (null!=rs)rs.close();
				if (null!=stmt)stmt.close();
			}
		}

	}
}
