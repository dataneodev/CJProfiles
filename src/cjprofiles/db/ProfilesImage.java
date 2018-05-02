package cjprofiles.db;

public class ProfilesImage {
	private int id;
	private byte[] profilesImage;
	
	public ProfilesImage() {}
	
	public ProfilesImage(int id, byte[] profilesImage) {
		this.id = id;
		this.profilesImage = profilesImage;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setProfilesImage(byte[] profilesImage) {
		this.profilesImage = profilesImage;
	}
	
	public int getId() {
		return id;
	}
	
	public byte[] getProfilesImage() {
		return profilesImage;
	}
}
