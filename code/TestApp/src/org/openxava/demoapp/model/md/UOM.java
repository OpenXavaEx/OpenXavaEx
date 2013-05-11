package org.openxava.demoapp.model.md;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.openxava.annotations.Required;
import org.openxava.annotations.Tab;
import org.openxava.annotations.View;
import org.openxava.demoapp.base.BaseMasterDataModel;
import org.openxava.demoapp.model.md.enums.MeasureCatogory;

/**
 * The unit of measure master data
 * @author root
 *
 */
@Entity
@Table(name="MD_UOM")
@Tab(baseCondition = "enabled=true", properties="code, name, category, descr")
@View(name="CodeAndName", members="code, name")
public class UOM extends BaseMasterDataModel{
	
	@Type(type=_Link.EnumLetterType, parameters={
			@Parameter(name="letters", value=MeasureCatogory.LETTERS),
			@Parameter(name="enumType", value=_Link.MeasureCatogory)
	})
	@Column(length=2) @Required
	private MeasureCatogory category;

	public MeasureCatogory getCategory() {
		return category;
	}

	public void setCategory(MeasureCatogory category) {
		this.category = category;
	}
	
	@Transient
	public String getDisplayName(){
		return this.getCode() + " - " + this.getName();
	}
}
