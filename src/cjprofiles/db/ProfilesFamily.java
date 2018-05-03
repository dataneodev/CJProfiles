package cjprofiles.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TFAMILY")
public class ProfilesFamily {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "fname")
	private String profileName;
	
	@Column(name = "normeid")
	private int profileNormeId;
	
	@Column(name = "imageid")
	private int profileImageId;
	
	@Column(name = "drawerid")
	private int profileDrawerId;
	
	public ProfilesFamily() {}
	
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
