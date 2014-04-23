package org.openxava.demoapp.model.testcase;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.Digits;
import org.openxava.annotations.ReadOnly;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;

/**
 * Test case for many kind of number format
 */
@Entity
@Table(name="TEST_Number")
@Tab(defaultOrder="id")
public class DecimalTestCase {
    @TableGenerator(
    		name = "DecimalTestCase", table = "ID_GEN",
    		pkColumnName = "GEN_NAME", pkColumnValue = "DecimalTestCase",
            valueColumnName = "GEN_VAL", allocationSize = 1
    )  
    @Id
    @ReadOnly
    @GeneratedValue(strategy=GenerationType.TABLE, generator="DecimalTestCase")  
    private Integer id;
    
    private BigDecimal bigDecimalDefault;
    
    @Digits(fractionalDigits=6, integerDigits = 20)
    private BigDecimal bigDecimalWithSixDigits;
    
    @Stereotype("MONEY")
    private BigDecimal moneyDefault;
    
    @Stereotype("MONEY")
    @Digits(fractionalDigits=6, integerDigits = 20)
    private BigDecimal moneyWithSixDigits;
    
    private Double doubleDefault;
    
    @Digits(fractionalDigits=6, integerDigits = 20)
    private Double doubleWithSixDigits;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public BigDecimal getBigDecimalDefault() {
		return bigDecimalDefault;
	}
	public void setBigDecimalDefault(BigDecimal bigDecimalDefault) {
		this.bigDecimalDefault = bigDecimalDefault;
	}
	public BigDecimal getBigDecimalWithSixDigits() {
		return bigDecimalWithSixDigits;
	}
	public void setBigDecimalWithSixDigits(BigDecimal bigDecimalWithSixDigits) {
		this.bigDecimalWithSixDigits = bigDecimalWithSixDigits;
	}
	public BigDecimal getMoneyDefault() {
		return moneyDefault;
	}
	public void setMoneyDefault(BigDecimal moneyDefault) {
		this.moneyDefault = moneyDefault;
	}
	public BigDecimal getMoneyWithSixDigits() {
		return moneyWithSixDigits;
	}
	public void setMoneyWithSixDigits(BigDecimal moneyWithSixDigits) {
		this.moneyWithSixDigits = moneyWithSixDigits;
	}
	public Double getDoubleDefault() {
		return doubleDefault;
	}
	public void setDoubleDefault(Double doubleDefault) {
		this.doubleDefault = doubleDefault;
	}
	public Double getDoubleWithSixDigits() {
		return doubleWithSixDigits;
	}
	public void setDoubleWithSixDigits(Double doubleWithSixDigits) {
		this.doubleWithSixDigits = doubleWithSixDigits;
	}
}
