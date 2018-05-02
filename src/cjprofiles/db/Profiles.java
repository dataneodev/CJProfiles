package cjprofiles.db;

public class Profiles {
	private int id;
	private int profileNormeId;
	private int profileFamilyId;
	private String profileName;
		
	public Profiles() {
		
	}
	
	public Profiles(int id, int profileNormeId, int profileFamilyId, String profileName) {
		this.id = id;
		this.profileNormeId = profileNormeId;
		this.profileFamilyId = profileFamilyId;
		this.profileName = profileName;
	}
	
	public int getId() {
		return id;
	}
	
	public int getProfileNormeId() {
		return profileNormeId;
	}
	
	public int getProfileFamilyId() {
		return profileFamilyId;
	}
	
	public String getProfileName() {
		return profileName;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setProfileNormeId(int profileNormeId) {
		this.profileNormeId = profileNormeId;
	}
	
	public void setProfileFamilyId(int profileFamilyId) {
		this.profileFamilyId = profileFamilyId;
	}
	
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	}
