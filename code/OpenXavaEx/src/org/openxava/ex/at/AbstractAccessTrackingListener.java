package org.openxava.ex.at;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

import org.openxava.annotations.PostCreate;
import org.openxava.ex.at.model.AccessTrackingEntityBase;

/**
 * The Abstract Adapter for AccessTracking to a listener suitable for JPA. <p>
 * NOTE: It's subclass MUST re-annotate @PostPersist/@PostLoad/@PostUpdate/@PreRemove events, such as:
 * <pre>
 * @PostPersist
 *  public void trackCreateAccess(Object model) {
 *      super.trackCreateAccess(model);
 *  }
 * </pre> 
 */

public abstract class AbstractAccessTrackingListener<T extends AccessTrackingEntityBase> {
    private AccessTrackingManager mgr = AccessTrackingManager.getInstance();
    
    @PostCreate
    public void trackCreateAccess(Object model) {
        mgr.onCreate(this, model);
    }
    
    @PostLoad
    public void trackReadAccess(Object model) {
        mgr.onRead(this, model);
    }
    
    @PostUpdate
    public void trackUpdateAccess(Object model) {
        mgr.onUpdate(this, model);
    }
    
    @PreRemove
    public void trackDeleteAccess(Object model) {
        mgr.onDelete(this, model);
    }
    
    /**
     * Return the real AccessTrackingEntity(MUST be subclass of {@link AbstractAccessTrackingListener}) Object
     * @return
     */
    public abstract T getAccessTrackingEntity();
}
