package cjprofiles.db;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TPROFILES")
public class ProfilesProperties extends Profiles {
	@Column(name = "characteristic")
	private String profileCharacteristic;
	
	public String getProfileCharacteristic() {
	    return profileCharacteristic;
	}

	public void setProfileCharacteristic(String profileCharacteristic) {
		  this.profileCharacteristic = profileCharacteristic;
	}
	
	public List<ProfileItem> getProfileCharacteristicList() {
		 List<ProfileItem> result = new LinkedList <>();
		 String[] profileLine = profileCharacteristic.split(";");
		 if(profileLine.length == 0) {
			 return result;
		 }
		 
		for(String line : profileLine) {
			String[] para = line.split("=");
			if(para.length != 3) { continue; }
			result.add(new ProfileItem(para[0], para[1], para[2]) );
		}
		 return result;
	}
}
