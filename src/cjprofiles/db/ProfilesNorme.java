package cjprofiles.db;

public class ProfilesNorme {
	private int id;
	private String normeName;
	private String code;
	
	public ProfilesNorme() {
		
	}
	
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