package org.openxava.ex.at.model;

import java.sql.Timestamp;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.annotations.GenericGenerator;
import org.openxava.actions.OnChangePropertyBaseAction;
import org.openxava.annotations.Editor;
import org.openxava.annotations.Hidden;
import org.openxava.annotations.OnChange;
import org.openxava.annotations.Stereotype;
import org.openxava.view.View;

@MappedSuperclass
public abstract class AccessTrackingEntityBase {
	@Id @GeneratedValue(generator="system-uuid") @Hidden
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	@Column(name="ID", length=32)
	private String id;

	@OnChange(InitChangeAction.class)
	@Column(name="MODEL_TYPE", length=255)
	private String modelType;

	@Column(name="MODEL_NAME", length=255)
	private String modelName;
	
	@Column(name="RAW_RECORD_ID", length=500)
	private String rawRecordId;
	
	@Column(name="RECORD_ID", length=500)
	private String recordId;
	
	@Column(name="USER_NAME", length=255)
	private String userName;
	
	@Column(name="OPT_TIME")
	private Timestamp operationTime;
	
	@Column(name="OPT_TYPE", length=255)
	private String operationType;

	@Column(name="BEFORE_OBJ", columnDefinition="CLOB")
	@Hidden
	@Stereotype("MEMO")
	@Lob
	private String beforeObj;

	@Column(name="AFTER_OBJ", columnDefinition="CLOB")
	@Hidden
	@Stereotype("MEMO")
	@Lob
	private String afterObj;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Timestamp getOperationTime() {
		return operationTime;
	}
	public void setOperationTime(Timestamp operationTime) {
		this.operationTime = operationTime;
	}

	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getBeforeObj() {
		return beforeObj;
	}
	public void setBeforeObj(String beforeObj) {
		this.beforeObj = beforeObj;
	}

	public String getAfterObj() {
		return afterObj;
	}
	public void setAfterObj(String afterObj) {
		this.afterObj = afterObj;
	}
	
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	
	public String getRawRecordId() {
		return rawRecordId;
	}
	public void setRawRecordId(String rawRecordId) {
		this.rawRecordId = rawRecordId;
	}

	@Transient
	@Editor("HtmlText")
	public String getDiff(){
		String after = this.afterObj;
		String before = this.beforeObj;
		if (null!=after){
			if (null==before) before=after;
		}else if (null!=before){
			if (null==after) after=before;
		}else{
			before = "";
			after = "";
		}

		diff_match_patch d = new diff_match_patch();
		LinkedList<Diff> ds = d.diff_main(before, after);
		d.diff_cleanupSemantic(ds);
		String html = d.diff_prettyHtml(ds);
		return "<pre>" + html + "</pre>";
	}

	public static class InitChangeAction extends OnChangePropertyBaseAction{
		public void execute() throws Exception {
			View v = this.getView();
			if (null==v.getViewName()){	//ReadOnly for default view
				FieldUtils.writeDeclaredField(v, "readOnly", Boolean.TRUE, true);
			}
		}
	}
}
