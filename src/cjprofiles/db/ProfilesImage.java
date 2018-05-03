package cjprofiles.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TIMAGE")
public class ProfilesImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "imagedata")
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
