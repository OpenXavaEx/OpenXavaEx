package org.openxava.oxdemo.model;

import java.math.*;
import java.util.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.calculators.*;
import org.openxava.model.*;

@Entity
@Table(name="DEMO_INVOICE")
@Views({
	@View(members=
		"year, number, date, vatPercentage;" +
		"customer;" +
		"details;" +
		"remarks"
	),
	@View(name="WithSections", members=
		"year, number, date, vatPercentage;" +
		"customer { customer }" +
		"details { details }" +
		"remarks { remarks } "			
	)	
})
@Tab(properties="year, number, date, customer.name, remarks")
public class Invoice extends Identifiable {
	
	@DefaultValueCalculator(CurrentYearCalculator.class)
	@Column(length=4) @Required
	private int year;
	
	@Column(length=6, name="invNo") @Required
	private int number;
	
	@Column(name="invDate") @Required @DefaultValueCalculator(CurrentDateCalculator.class) 
	private Date date;
	
	@Column(length=2) @Required
	@DefaultValueCalculator(value=IntegerCalculator.class, properties=@PropertyValue(name="value", value="18"))
	private int vatPercentage;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	private Customer customer;
	
	@OneToMany(mappedBy="invoice", cascade=CascadeType.REMOVE)
	@ListProperties("product.number, product.description, product.unitPrice, quantity, amount[invoice.sum, invoice.vat, invoice.total]")
	private Collection<InvoiceDetail> details;
	
	@Stereotype("TEXT_AREA")
	private String remarks;
	
	public BigDecimal getSum() {
		BigDecimal sum = BigDecimal.ZERO;
		for (InvoiceDetail detail: details) {
			sum = sum.add(detail.getAmount());
		}
		return sum;
	}
	
	public BigDecimal getVat() {
		return getSum().multiply(new BigDecimal(getVatPercentage()).divide(new BigDecimal(100))).setScale(2, RoundingMode.UP);
	}
	
	public BigDecimal getTotal() {
		return getSum().add(getVat()).setScale(2, RoundingMode.UP);
	}
	

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getVatPercentage() {
		return vatPercentage;
	}

	public void setVatPercentage(int vatPercentage) {
		this.vatPercentage = vatPercentage;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Collection<InvoiceDetail> getDetails() {
		return details;
	}

	public void setDetails(Collection<InvoiceDetail> details) {
		this.details = details;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
