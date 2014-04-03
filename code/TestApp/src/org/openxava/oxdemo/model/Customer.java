package org.openxava.oxdemo.model;

import javax.persistence.*;

import org.openxava.annotations.*;
import org.openxava.demoapp.tracking.SimpleAccessTrackingListener;
import org.openxava.ex.at.EntityDescribable;

@Entity
@EntityListeners(SimpleAccessTrackingListener.class)
@Table(name="DEMO_CUSTOMER")
public class Customer implements EntityDescribable{
	
	@Id @Column(name="cNo")
	private int number;
	
	@Column(length=40) 
	private String name;
	
	@Stereotype("PHOTO")
	@Lob
	private byte [] photo;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte [] getPhoto() {
		return photo;
	}

	public void setPhoto(byte [] photo) {
		this.photo = photo;
	}

	public String describableRecordId() {
		return "Customer: " + this.number;
	}
	
}
