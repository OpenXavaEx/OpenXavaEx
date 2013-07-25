package org.openxava.ex.model.base;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.openxava.annotations.DefaultValueCalculator;
import org.openxava.annotations.Hidden;
import org.openxava.calculators.CurrentTimestampCalculator;

/**
 * The base model for all kinds of MasterData
 * @author root
 *
 */
@MappedSuperclass
public class BaseSimpleMasterDataModelNoId {
	
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
	
	

	public Timestamp getVersion() {
		return version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

}
