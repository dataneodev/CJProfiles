package cjprofiles.db;

import java.util.LinkedList;
import java.util.List;


public class ProfilesProperties extends Profiles {
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
