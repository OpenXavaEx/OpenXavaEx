package org.openxava.demoapp.tracking;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.openxava.annotations.Tab;
import org.openxava.ex.at.model.AccessTrackingEntityBase;

@Entity
@Table(name="TEST_OX_SYS_ATE")
@Tab(properties="modelName, recordId, userName, operationTime, operationType", defaultOrder="operationTime DESC")
public class AccessTrackingEntity extends AccessTrackingEntityBase {

}
