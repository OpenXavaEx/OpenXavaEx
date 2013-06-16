package org.openxava.ex.model.base;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.openxava.annotations.Hidden;

@MappedSuperclass
public class BaseBillDetailModel {
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)
	private String dtlid;

	public String getDtlid() {
		return dtlid;
	}

	public void setDtlid(String dtlid) {
		this.dtlid = dtlid;
	}
}
