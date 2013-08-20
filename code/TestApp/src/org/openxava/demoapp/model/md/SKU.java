package org.openxava.demoapp.model.md;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.FlushModeType;
import javax.persistence.ManyToOne;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.annotations.DescriptionsList;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.PostCreate;
import org.openxava.annotations.ReferenceView;
import org.openxava.annotations.Required;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;
import org.openxava.annotations.Tabs;
import org.openxava.annotations.View;
import org.openxava.annotations.Views;
import org.openxava.demoapp.model.purchase.RequirementFormDetail;
import org.openxava.ex.model.base.BaseMasterDataModel;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

@Entity
@Table(name="MD_SKU")
//BP: Can use a non-persistence property(field) in @Tab and @View
//BP: in @Tab baseCondition, "e" means the main data table
@Tabs({
	@Tab(baseCondition = "e.enabled=true", properties="code, name, vendor.name, uom.displayName, price, descr")
})
@Views({
	@View(name="Search", members="code; name"),	//BP: Use "Search" View as the search dialog
	@View(name="V-SKU-Simple", members="code; nameWithUom"),
	@View(name="V-SKU-code-name", members="code; nameWithUom")
})
public class SKU extends BaseMasterDataModel{
	private static final Log log = LogFactory.getLog(SKU.class);
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	@ReferenceView("V-UOM-code-name")	//Code and name
	private UOM uom;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	//BP: use @DescriptionsList to present a combobox to select
	@DescriptionsList(descriptionProperties="code, name")
	@Required
	private Vendor vendor;

	@Stereotype("MONEY")
	private BigDecimal price;
	
	//BP: Use image field
	@Stereotype("PHOTO")
	private byte [] photo;
	
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	/** BP: Let @ReferenceView({@link RequirementFormDetail#sku}) display uom.name property (can't display uom.name directly) */ 
	@Transient @Hidden
	public String getNameWithUom(){
		return this.getName() + " ("+this.getUom().getName()+")";
	}
	
	/** BP: Use @PostCreate instead of @PostPersist to handle the data insert event;
	 *  BP: @PostCreate method must be public
	 *  BP: JPA's @Post* occurs without the transaction */
	@PostCreate
	public void onInsert(){
		logThis("Insert");
		logStat("after-insert");
	}
	/** BP: Use @PreUpdate to handle the data update event */
	@PreUpdate
	private void onUpdate(){
		logStat("before-update");
		logThis("Update");
	}
	/** BP: Use @PreRemove to handle the data delete event */
	@PreRemove
	private void onRemove(){
		logStat("before-remove");
		logThis("Remove");
	}
	private void logThis(String action) {
		SkuChangeLog l = new SkuChangeLog();
		l.setAction(action);
		l.setChangeTime(new Timestamp(System.currentTimeMillis()));
		l.setSkuId(this.getId());
		l.setSkuName(this.getName());
		//BP: Get login user information
		l.setUserName(Users.getCurrentUserInfo().getGivenName());
		EntityManager em = XPersistence.getManager();
		//BP: EntityManager: Use "merge" instead of "persist"
		em.merge(l);
	}
	
	private void logStat(String action){
		log.info("Logging SKU data stat. information ...");
		
		FlushModeType oldType = XPersistence.getManager().getFlushMode();
		try{
			//BP: Change FlashMode manually, to avoid the "StackOverflow" on @PreUpdate
			XPersistence.getManager().setFlushMode(FlushModeType.COMMIT);
			
			Query q = XPersistence.getManager().createNativeQuery("SELECT COUNT(*) FROM MD_SKU");
			Object o = q.getSingleResult();
			Query u = XPersistence.getManager().createNativeQuery(
					"INSERT INTO LOG_SKU_CHANGE (id,action,changeTime,skuId,skuName) VALUES(?,?,?,?,?)");
			u.setParameter(1, System.currentTimeMillis());
			u.setParameter(2, action);
			u.setParameter(3, new Timestamp(System.currentTimeMillis()));
			u.setParameter(4, "<STAT>");
			u.setParameter(5, "Total records: " + o);
			u.executeUpdate();
		}finally{
			XPersistence.getManager().setFlushMode(oldType);
		}
	}
}
