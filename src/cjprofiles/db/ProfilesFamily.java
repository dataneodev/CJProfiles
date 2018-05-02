package cjprofiles.db;

 public class ProfilesFamily {
	private int id;
	private String profileName;
	private int profileNormeId;
	private int profileImageId;
	private int profileDrawerId;
	
	public ProfilesFamily() {
		
	}
	
	public ProfilesFamily(int id, String profileName, int profileNormeId, int profileImageId, int profileDrawerId) {
		this.id = id;
		this.profileName = profileName;
		this.profileNormeId = profileNormeId;
		this.profileImageId = profileImageId;
		this.profileDrawerId = profileDrawerId;
	}
	
	public int getId() {
		return id;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public int getProfileNormeId() {
		return profileNormeId;
	}
	
	public int getProfileImageId() {
		return profileImageId;
	}
	
	public int getProfileDrawerId() {
		return profileDrawerId;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	public void setProfileNormeId(int profileNormeId) {
		this.profileNormeId = profileNormeId;
	}
	
	public void setProfileImageId(int profileImageId) {
		this.profileImageId = profileImageId;
	}
	
	public void setProfileDrawerId(int profileDrawerId) {
		this.profileDrawerId = profileDrawerId;
	}
}
