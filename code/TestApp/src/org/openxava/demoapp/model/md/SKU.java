package org.openxava.demoapp.model.md;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;
import org.openxava.demoapp.base.BaseMasterDataModel;

@Entity
@Table(name="MD_SKU")
//BP: Can use a non-persistence in @Tab and @View
@Tab(baseCondition = "enabled=true", properties="code, name, vender, uom.displayName, price, descr")
public class SKU extends BaseMasterDataModel{
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@ReferenceView("CodeAndName")	//Code and name
	private UOM uom;
	
	@Stereotype("MONEY")
	private BigDecimal price;
	
	//BP: Use image field
	@Stereotype("PHOTO")
	private byte [] photo;
	
	@Column(length=64)
	private String vender;

	public UOM getUom() {
		return uom;
	}

	public void setUom(UOM uom) {
		this.uom = uom;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getVender() {
		return vender;
	}

	public void setVender(String vender) {
		this.vender = vender;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}
