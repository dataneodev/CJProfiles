package cjprofiles.db;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Profiles {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "normeid")
	private int profileNormeId;
	
	@Column(name = "familyid")
	private int profileFamilyId;
	
	@Column(name = "profilename")
	private String profileName;
		
	public Profiles() {}
	
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
