package org.openxava.ex.model.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.*;
import org.openxava.annotations.*;
import org.openxava.calculators.CurrentTimestampCalculator;
import org.openxava.util.*;

/**
 * The base model for all kinds of MasterData
 * @author root
 *
 */
@MappedSuperclass
public class BaseSimpleMasterDataModel {
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(length=32)
	private String id;

	@Version
	private Timestamp version;
	
	@Column(insertable=true,updatable=false)
	@DefaultValueCalculator(CurrentTimestampCalculator.class)
	private Timestamp insert_date;
	
	@Column(insertable=true,updatable=true)
	@DefaultValueCalculator(CurrentTimestampCalculator.class)
	private Timestamp update_date;
	
	@Column
	@Hidden
	private String insert_user;
	
	@Column
	@Hidden
	private String update_user;
	
	
	@Column
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

}
