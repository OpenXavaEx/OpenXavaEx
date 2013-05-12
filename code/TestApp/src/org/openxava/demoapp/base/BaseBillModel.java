package org.openxava.demoapp.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.Required;

/**
 * The base model for all kinds of Bills
 * @author root
 *
 */
@MappedSuperclass
public class BaseBillModel {
	/** Constant - name refers to the billHead from details */
	public static final String RELATIONSHIP_FIELD_BILLHEAD = "billHead";
	
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)
	private String id;
	
	@Column(length=32, unique=true)
	@Required
	//TODO: BillNo sequence format should be implement
	private String billNo;

	@Version
	private Timestamp version;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String no) {
		this.billNo = no;
	}
}
