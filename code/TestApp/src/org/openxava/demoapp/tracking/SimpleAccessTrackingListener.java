package org.openxava.demoapp.tracking;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

import org.openxava.annotations.PostCreate;
import org.openxava.ex.at.AbstractAccessTrackingListener;

public class SimpleAccessTrackingListener extends AbstractAccessTrackingListener<AccessTrackingEntity> {

	@Override
	public AccessTrackingEntity getAccessTrackingEntity() {
		return new AccessTrackingEntity();
	}

	@Override
	@PostCreate
	public void trackCreateAccess(Object model) {
		super.trackCreateAccess(model);
	}

	@Override
	@PostLoad
	public void trackReadAccess(Object model) {
		super.trackReadAccess(model);
	}

	@Override
	@PostUpdate
	public void trackUpdateAccess(Object model) {
		super.trackUpdateAccess(model);
	}

	@Override
	@PreRemove
	public void trackDeleteAccess(Object model) {
		super.trackDeleteAccess(model);
	}

}
