package org.openxava.ex.at;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.openxava.ex.at.impl.EntityUtil;
import org.openxava.ex.at.model.AccessTrackingEntityBase;

import com.alibaba.fastjson.JSON;

public class AccessTrackingManager {
	private static final AccessTrackingManager instance = new AccessTrackingManager();

	public static AccessTrackingManager getInstance() {
		return instance;
	}
	
	/** Store the ID vs Model Object's JSON String, Updated when read/create/update, and removed when delete */
	private Map<Object, String> objJsonTable = new ConcurrentHashMap<Object, String>();
	
	public void onCreate(AbstractAccessTrackingListener<? extends AccessTrackingEntityBase> lnr, Object model){
		Object pk = EntityUtil.getPK(model);
		String spk = _C(model, pk);
		//Log it
		EntityUtil.doTrack(pk+"", model, "", "Create", lnr);
		//Remember it
		String json = JSON.toJSONString(model, true);
		objJsonTable.put(spk, json);
	}
	public void onRead(AbstractAccessTrackingListener<? extends AccessTrackingEntityBase> lnr, Object model){
		Object pk = EntityUtil.getPK(model);
		//Remember it
		String json = JSON.toJSONString(model, true);
		objJsonTable.put(_C(model, pk), json);
	}
	public void onUpdate(AbstractAccessTrackingListener<? extends AccessTrackingEntityBase> lnr, Object model){
		Object pk = EntityUtil.getPK(model);
		String spk = _C(model, pk);
		//Log it
		String oldJson = objJsonTable.get(spk);
		EntityUtil.doTrack(pk+"", model, oldJson, "Update", lnr);
		//Remember it
		String json = JSON.toJSONString(model, true);
		objJsonTable.put(spk, json);
	}
	public void onDelete(AbstractAccessTrackingListener<? extends AccessTrackingEntityBase> lnr, Object model){
		Object pk = EntityUtil.getPK(model);
		String spk = _C(model, pk);
		//Log it
		String oldJson = objJsonTable.get(spk);
		EntityUtil.doTrack(pk+"", model, oldJson, "Delete", lnr);
		//Remove it
		objJsonTable.remove(spk);
	}
	
	private String _C(Object model, Object pk){
		return model.getClass().getName() + ":" + pk;
	}
}
