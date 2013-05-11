package org.openxava.demoapp.model.md;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.openxava.annotations.Required;
import org.openxava.demoapp.base.BaseMasterDataModel;
import org.openxava.demoapp.model.md.enums.MeasureCatogory;

/**
 * The unit of measure master data
 * @author root
 *
 */
@Entity(name="MD_UOM")
public class UOM extends BaseMasterDataModel{
	
	@Type(type=_Link.EnumLetterType, parameters={
			@Parameter(name="letters", value="LVWQ"),
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
}
