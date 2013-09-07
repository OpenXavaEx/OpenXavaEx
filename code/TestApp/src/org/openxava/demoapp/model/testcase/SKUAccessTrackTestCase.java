package org.openxava.demoapp.model.testcase;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Query;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openxava.annotations.PostCreate;
import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;
import org.openxava.annotations.Tabs;
import org.openxava.ex.model.base.BaseMasterDataModel;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Users;

@Entity
@Table(name="TEST_MD_SKU")
@Tabs({
	@Tab(baseCondition = "e.enabled=true", properties="code, name, price, descr")
})
public class SKUAccessTrackTestCase extends BaseMasterDataModel{
	private static final Log log = LogFactory.getLog(SKUAccessTrackTestCase.class);
	
	@Stereotype("MONEY")
	private BigDecimal price;
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	/** BP: Use OpenXava Persist Callback @PostCreate to handle the data insert event(instead of JPS's @PostPersist);
	 *  BP: @PostCreate method must be public
	 */
	@PostCreate
	public void onInsert(){
		logThis("Inserted");
		logStat("after-insert");
		//BP: validate data in @PostUpdate within Transaction 
		if (this.getCode().equals(this.getName())){
			throw new RuntimeException("Code must be different to Name");
		}
	}

	/** BP: Use @PreUpdate to handle the data update event */
	@PreUpdate
	private void onUpdate(){
		logStat("before-update");
		logThis("Updating");
	}
	@PostUpdate
	private void onPostUpdate(){
		logStat("after-update");
		logThis("Updated");
		//BP: validate data in @PostUpdate within Transaction 
		if (this.getCode().equals(this.getName())){
			throw new RuntimeException("Code must be different to Name");
		}
	}

	/** BP: Use @PreRemove to handle the data delete event */
	@PreRemove
	private void onRemove(){
		logStat("before-remove");
		logThis("Removing");
	}
	
	private void logThis(String action) {
		SKUChangeLog l = new SKUChangeLog();
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
			
			Query q = XPersistence.getManager().createNativeQuery("SELECT COUNT(*) FROM TEST_MD_SKU");
			Object o = q.getSingleResult();
			Query u = XPersistence.getManager().createNativeQuery(
					"INSERT INTO TEST_SKU_CHANGE_LOG (id,action,changeTime,skuId,skuName,userName) VALUES(?,?,?,?,?,?)");
			u.setParameter(1, System.currentTimeMillis());
			u.setParameter(2, action);
			u.setParameter(3, new Timestamp(System.currentTimeMillis()));
			u.setParameter(4, "<STAT>");
			u.setParameter(5, "Total records: " + o);
			u.setParameter(6, Users.getCurrentUserInfo().getGivenName());
			u.executeUpdate();
		}finally{
			XPersistence.getManager().setFlushMode(oldType);
		}
	}
}
