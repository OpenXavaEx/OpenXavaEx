package org.openxava.ex.at.impl;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

import org.openxava.ex.at.AbstractAccessTrackingListener;
import org.openxava.ex.at.AccessTrackingException;
import org.openxava.ex.at.EntityDescribable;
import org.openxava.ex.at.model.AccessTrackingEntityBase;
import org.openxava.ex.utils.Misc;
import org.openxava.jpa.XPersistence;
import org.openxava.util.Labels;
import org.openxava.util.Users;

import com.alibaba.fastjson.JSON;

public class EntityUtil {
	public static Object getPK(Object model){
		Map<String, Object> pks = new HashMap<String, Object>();
		
		Class<?> c = model.getClass();
		Entity en = c.getAnnotation(Entity.class);
		if (null==en){
			throw new AccessTrackingException("Class ["+c.getName()+"] is NOT an Entity");
		}
		
		Object lastIdVal = null;	//To remember the last field value of @Id
		try{
			Class<?> tmp = c;
			do {
				Field[] fs = tmp.getDeclaredFields();
				for (int i = 0; i < fs.length; i++) {
					Field f = fs[i];
					Id id = f.getAnnotation(Id.class);
					EmbeddedId eid = f.getAnnotation(EmbeddedId.class);
					if (null!=id || null!=eid){
						String key = f.getName();
						f.setAccessible(true);
						Object val = f.get(model);
						if (null!=id){
							lastIdVal = val;
						}
						pks.put(key, val);
					}
				}
				//Check the super class
				tmp = tmp.getSuperclass();
			}while(null!=tmp);
		}catch(Exception ex){
			Misc.throwRuntime(ex);
		}
		Object result;
		if (null!=lastIdVal && pks.size()==1){
			result = lastIdVal;
		}else{
			result = JSON.toJSONString(pks);
		}
		return result;
	}
	
	public static void doTrack(
			String pk, Object currentModel, String prevModelJson,
			String optType, AbstractAccessTrackingListener<? extends AccessTrackingEntityBase> lnr){
		AccessTrackingEntityBase ate = lnr.getAccessTrackingEntity();
		String className = currentModel.getClass().getName();
		ate.setModelType(className);
		String modelName = Labels.get(className);
		ate.setModelName(modelName);
		ate.setRawRecordId(pk);
		if (currentModel instanceof EntityDescribable){
			EntityDescribable d = (EntityDescribable)currentModel;
			ate.setRecordId(d.describableRecordId());
		}else{
			ate.setRecordId(pk);
		}
		ate.setUserName(Users.getCurrentUserInfo().getGivenName());
		ate.setOperationTime(new Timestamp(System.currentTimeMillis()));
		ate.setOperationType(optType);
		ate.setBeforeObj(prevModelJson);
		ate.setAfterObj(JSON.toJSONString(currentModel, true));
		EntityManager em = XPersistence.getManager();
		//EntityManager: Use "merge" instead of "persist"
		em.merge(ate);
	}
}
