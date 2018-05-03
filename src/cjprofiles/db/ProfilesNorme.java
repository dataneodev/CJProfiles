package cjprofiles.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TNORME")
public class ProfilesNorme {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nname")
	private String normeName;
	
	@Column(name = "code")
	private String code;
	
	public ProfilesNorme() {}
	
	public ProfilesNorme(int id, String normeName, String code) {
		this.id = id;
		this.normeName = normeName;
		this.code = code;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNormeName() {
		return normeName;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNormeName(String normeName) {
		this.normeName = normeName;
	}	
	
	public void setCode(String code) {
		this.code = code;
	}
}