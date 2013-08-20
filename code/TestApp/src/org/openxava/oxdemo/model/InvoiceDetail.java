package org.openxava.oxdemo.model;

import java.math.*;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.model.*;

@Entity
@Table(name="DEMO_INVOICE_DTL")
public class InvoiceDetail extends Identifiable {
	
	@ManyToOne
	private Invoice invoice;
	
	@ManyToOne(optional=false, fetch=FetchType.LAZY)
	@NoFrame
	private Product product;
	
	@Required
	private int quantity;
	
	@Depends("product.unitPrice, quantity")
	public BigDecimal getAmount() {
		return new BigDecimal(getQuantity()).multiply(getProduct().getUnitPrice());
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
