package cjprofiles.db;

import javafx.beans.property.SimpleStringProperty;

public class ProfileItem {
	private final SimpleStringProperty paramName;
	private final SimpleStringProperty paramValue;
	private final SimpleStringProperty paramUnit;

	public ProfileItem(String paramName, String paramValue, String paramUnit) {
		this.paramName =new SimpleStringProperty(paramName);
		this.paramValue = new SimpleStringProperty(paramValue);
		this.paramUnit = new SimpleStringProperty(paramUnit);
	}
	
	public String getParamName() {
		return paramName.getValue();
	}
	
	public String getParamValue() {
		return paramValue.getValue();
	}
	
	public String getParamUnit() {
		return paramUnit.getValue();
	}
	
	public void setParamName(String ParamName) {
		this.paramName.set(ParamName);
	}
	
	public void setParamValue(String ParamValue) {
		this.paramValue.set(ParamValue);
	}
	
	public void setParamUnit(String ParamUnit) {
		this.paramUnit.set(ParamUnit);
	}
}




