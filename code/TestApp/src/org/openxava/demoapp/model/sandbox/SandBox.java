package org.openxava.demoapp.model.sandbox;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.openxava.annotations.Required;
import org.openxava.ex.model.base.BaseMasterDataModel;

@Entity
@Table(name="MD_SandBox")
public class SandBox extends BaseMasterDataModel{
	
	//BP: Use "columnDefinition" to customize column
	@Column(length=255, unique=true, columnDefinition="DEFAULT 'OK'")
	@Required
	private String columnDefinitionTest;

}
