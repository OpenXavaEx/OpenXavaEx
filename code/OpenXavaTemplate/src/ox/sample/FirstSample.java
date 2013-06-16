package ox.sample;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.openxava.annotations.Stereotype;
import org.openxava.annotations.Tab;
import org.openxava.ex.model.base.BaseMasterDataModel;

@Entity
@Table(name="SAMPLE_FirstSample")
@Tab(baseCondition = "enabled=true", properties="code, name, descr")
public class FirstSample extends BaseMasterDataModel{
	@Stereotype("PHOTO")
	private byte [] photo;

	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}
