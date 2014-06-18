package org.openxava.demoapp.model.testcase.acl;

import java.sql.Timestamp;
import java.util.Iterator;

import javax.persistence.PostUpdate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.engine.ActionQueue;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.PostCollectionUpdateEvent;
import org.hibernate.event.PostCollectionUpdateEventListener;
import org.hibernate.persister.collection.CollectionPersister;
import org.openxava.util.Users;

/**
 * Add property "hibernate.ejb.event.post-collection-update" in persistence.xml to register this hibernate event listener;
 * BP: {@link PostUpdate} works before "collectionUpdates", so you can't do following work in PostUpdate action,
 *      see {@link ActionQueue#executeActions()} for reference.
 */
public class ACL_U_C_EX_PostCollectionUpdate implements PostCollectionUpdateEventListener{
	private static final long serialVersionUID = 20140616L;
	
	private static final Log log = LogFactory.getLog(ACL_U_C_EX_PostCollectionUpdate.class);
	
	public void onPostUpdateCollection(PostCollectionUpdateEvent event) {
		Object owner = event.getAffectedOwnerOrNull();
		if (null!=owner && owner instanceof UserShowCompanyWithExFields){
			UserShowCompanyWithExFields u = (UserShowCompanyWithExFields)owner;
			
			SessionImplementor sesImr = (SessionImplementor) event.getSession();
			PersistentCollection persistentCollection = event.getCollection();
			CollectionPersister collectionPersister = 
					sesImr.getPersistenceContext().getCollectionEntry(persistentCollection).getCurrentPersister();
			@SuppressWarnings("unchecked")
			Iterator<? extends Object> iterator = persistentCollection.entries(collectionPersister);
			while(iterator.hasNext()) {
	            Object o = iterator.next();
	            if (o instanceof CompanyWithExFields){
	            	CompanyWithExFields c = (CompanyWithExFields)o;
	            	String uid = u.getId();
	            	String cid = c.getId();
	            	
	            	String userName = null;
	            	Timestamp time = null;
	            	
	            	ACL_U_C_EX info = u.exInfo4User.get(cid);
	            	if (null!=info){
	            		userName = info.getCreator();
	            		time = info.getCreateTime();
	            	}
	            	
	            	//For new added record, logging the creator and createTime
	            	if (null==userName) userName = Users.getCurrent();
	            	if (null==time) time = new Timestamp(System.currentTimeMillis());
	            	if (null==userName){
	            		throw new RuntimeException("Can't get current user when logging creator in table TEST_ACL_USER_EX");
	            	}
	            	
	            	String sql = "UPDATE TEST_ACL_U_C_EX SET CREATOR=:creator, CREATETIME=:createTime WHERE USERID=:uid AND COMPANYID=:cid";
	            	Session session = event.getSession();
	            	SQLQuery query = session.createSQLQuery(sql);
					query.setString("creator", userName);
					query.setTimestamp("createTime", time);
					query.setString("uid", uid);
					query.setString("cid", cid);
	            	int cnt = query.executeUpdate();
					String msg = "logging the creator and createTime in table TEST_ACL_USER_EX for company ["+cid+"]/user ["+uid+"]: " + cnt;
	            	if (cnt != 1){
	            		throw new RuntimeException("Fail "+msg);
	            	}
	            	log.info("Success "+msg);
	            }
	        }
		}
	}

}
