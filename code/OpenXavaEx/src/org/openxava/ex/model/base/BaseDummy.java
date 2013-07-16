package org.openxava.ex.model.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * This is the base class of "Dummy" model, a dummy model only used for mark a package "managed"
 * @author root
 *
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@Table(name="OX_SYS_Dummy")
public class BaseDummy {
	@Id
	@Column(length=32, columnDefinition="VARCHAR(31) DEFAULT 'OK'")
	private String id;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
