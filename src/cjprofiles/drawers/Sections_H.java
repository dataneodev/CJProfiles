package cjprofiles.drawers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import cjprofiles.db.ProfileItem;
import cjprofiles.db.ProfilesProperties;
import cjprofiles.drawers.Dxf.lineType;

public class Sections_H implements DxfDrawer {
	private static final Logger logger = Logger.getLogger( Sections_H.class.getName() );
	private ProfilesProperties profileData;
	private int drawerId;
	
	public void setData(ProfilesProperties profileProperties, int darwerId) {
		this.profileData = profileProperties;
		this.drawerId = darwerId;
	}
	
	public String getDxfBody(boolean topView, boolean frontView, boolean sideView) throws IllegalArgumentException{
		logger.fine("getDxfBody.");
	
		HSectionCordinate Cor = null;
		switch(drawerId) {
			case 1:
				Cor = new HSectionCordinateEC();
			break;
		}
		
		if(Cor == null) {
			logger.warning("getDxfBody: cordinate == null");
			throw new IllegalArgumentException("getDxfBody: cordinate == null");			
		}
		
		Cor.setProfilesProperties(profileData); // IllegalArgumentException
			
		Dxf dxfFile = new Dxf();	
		float B = Cor.getB();
		float H = Cor.getH();
		float tf = Cor.getTf();
		float tw = Cor.getTw();
		float r = Cor.getR();

		// section generate
		//top view
		if(frontView) {
			double buckleA =  -1* Math.tan(Math.toRadians(90/4));
			// left side
			dxfFile.polyLineStart(lineType.lineContinue);
			dxfFile.polyLineAdd(-1*B/2, 0, 0);
			dxfFile.polyLineAdd(B/2, 0, 0);
			dxfFile.polyLineAdd(B/2, tf, 0);
			dxfFile.polyLineAdd(tw/2+r, tf, buckleA);
			dxfFile.polyLineAdd(tw/2, tf+r, 0);
			dxfFile.polyLineAdd(tw/2, tf+r+Cor.getH1(), buckleA);
			dxfFile.polyLineAdd(tw/2+r, H-tf, 0);
			dxfFile.polyLineAdd(B/2, H-tf, 0);
			dxfFile.polyLineAdd(B/2, H, 0);

			// right side
			dxfFile.polyLineAdd(-1*B/2, H, 0);
			dxfFile.polyLineAdd(-1*B/2, H-tf, 0);
			dxfFile.polyLineAdd(-1*(tw/2+r), H-tf, buckleA);
			dxfFile.polyLineAdd(-1*tw/2, tf+r+Cor.getH1(), 0);
			dxfFile.polyLineAdd(-1*tw/2, tf+r, buckleA);
			dxfFile.polyLineAdd(-1*(tw/2+r), tf, 0);
			dxfFile.polyLineAdd(-1*B/2, tf, 0);
			dxfFile.polyLineAdd(-1*B/2, 0, 0);
			dxfFile.polyLineEnd();
		}
		
		if(topView) {
			float startX = 0;
			float startY = H + B;
			dxfFile.polyLineStart(lineType.lineContinue);
			dxfFile.polyLineAdd(-1*B/2+startX, 0 + startY, 0);
			dxfFile.polyLineAdd(B/2+startX, 0 + startY, 0);
			dxfFile.polyLineAdd(B/2+startX, 2*B + startY, 0);
			dxfFile.polyLineAdd(-1*B/2+startX, 2*B + startY, 0);
			dxfFile.polyLineAdd(-1*B/2+startX, 0 + startY, 0);
			dxfFile.polyLineEnd();
			// web
			dxfFile.polyLineStart(lineType.lineHidden);
			dxfFile.polyLineAdd(-1*tw/2+startX, 0 + startY, 0);
			dxfFile.polyLineAdd(-1*tw/2+startX, 2*B + startY, 0);
			dxfFile.polyLineEnd();
			dxfFile.polyLineStart(lineType.lineHidden);
			dxfFile.polyLineAdd(tw/2+startX, 0 + startY, 0);
			dxfFile.polyLineAdd(tw/2+startX, 2*B + startY, 0);
			dxfFile.polyLineEnd();			
		}
		
		if(sideView) {
			float startX = B+ 0.5F*B;
			float startY = 0;	
			dxfFile.polyLineStart(lineType.lineContinue);
			dxfFile.polyLineAdd(0 + startX, 0 + startY, 0);
			dxfFile.polyLineAdd(2*B + startX, 0 + startY, 0);
			dxfFile.polyLineAdd(2*B + startX, H + startY, 0);
			dxfFile.polyLineAdd(0 + startX, H + startY, 0);
			dxfFile.polyLineAdd(0 + startX, 0 + startY, 0);
			dxfFile.polyLineEnd();
			
			dxfFile.polyLineStart(lineType.lineContinue);
			dxfFile.polyLineAdd(0 + startX, tf + startY, 0);
			dxfFile.polyLineAdd(2*B + startX, tf + startY, 0);
			dxfFile.polyLineEnd();
			
			dxfFile.polyLineStart(lineType.lineContinue);
			dxfFile.polyLineAdd(0 + startX, H - tf + startY, 0);
			dxfFile.polyLineAdd(2*B + startX, H - tf + startY, 0);
			dxfFile.polyLineEnd();
		}
		
		dxfFile.endDxf();
		return dxfFile.getDxfBody();
	}
}

class HSectionCordinateEC extends HSectionCordinateSource implements HSectionCordinate {
	HSectionCordinateEC(){
		logger.fine("HSectionCordinateEC. Map definition");
		ParametrsMap = new ArrayList<HSectionMap>();
		ParametrsMap.add(new HSectionMap(HSectionDim.h, "h", "mm"));
		ParametrsMap.add(new HSectionMap(HSectionDim.b, "b", "mm"));
		ParametrsMap.add(new HSectionMap(HSectionDim.tf, "tg", "mm"));
		ParametrsMap.add(new HSectionMap(HSectionDim.tw, "ts", "mm"));
		ParametrsMap.add(new HSectionMap(HSectionDim.r, "r", "mm"));
	}	
}

interface HSectionCordinate{
	void setProfilesProperties(ProfilesProperties profile) throws IllegalArgumentException;
	float getH();
	float getB();
	float getTf();
	float getTw();
	float getR();
	default float getH1() {
		return (getH()-2*getTf()-2*getR());
	}
}

enum HSectionDim{h, b, tf, tw, r}

class HSectionMap {
	HSectionDim dim;
	String dimNameFamily;
	String dimUnitFamily;
	boolean dimExists;
	float dimValue;
	
	HSectionMap(HSectionDim dim, String dimNameFamily, String dimUnitFamily){
		this.dim = dim;
		this.dimNameFamily = dimNameFamily;
		this.dimUnitFamily = dimUnitFamily;
		this.dimExists = false;	
	}
	public void setExists() {
		this.dimExists = true;
	}
}

abstract class HSectionCordinateSource implements HSectionCordinate {
	protected final Logger logger = Logger.getLogger( HSectionCordinateEC.class.getName() );
	protected List<HSectionMap> ParametrsMap; 
		
	public void setProfilesProperties(ProfilesProperties profile) throws IllegalArgumentException {
		logger.fine("setProfilesProperties");
		procesProfileData(profile);
	}
	
	private void procesProfileData(ProfilesProperties profileData) throws IllegalArgumentException {
		logger.fine("procesProfileData");
		if(ParametrsMap == null) {	
			throw new IllegalArgumentException("ParametrsMap == null");
		}
		
		if(ParametrsMap.size() == 0) {
			throw new IllegalArgumentException("ParametrsMap.size() == 0");
		}
		
		if(profileData == null) {
			throw new IllegalArgumentException("profileData == null");
		}
		
		List<ProfileItem> profileParams = profileData.getProfileCharacteristicList();
		
		if(profileParams.size() == 0) {	
			throw new IllegalArgumentException("profileParams.size() == 0");
		}
		
		for(int a=0; a < ParametrsMap.size(); a++) {
			for(int b=0; b < profileParams.size(); b++) {
				if(
					(ParametrsMap.get(a).dimNameFamily.equals(profileParams.get(b).getParamName())) &&
					(ParametrsMap.get(a).dimUnitFamily.equals(profileParams.get(b).getParamUnit()))
				  ) {
					ParametrsMap.get(a).setExists();
					try {
						ParametrsMap.get(a).dimValue = Float.parseFloat( profileParams.get(b).getParamValue());
					} catch(NumberFormatException e) {
						throw new IllegalArgumentException("procesProfileData: getMapValue: "+profileParams.get(b).getParamValue()+" is not a float");
					}	
					break;
				}
			}
		}
		
		for(int a=0; a < ParametrsMap.size(); a++) {
			if(ParametrsMap.get(a).dimExists == false) {
				throw new IllegalArgumentException("procesProfileData: map check failed");
			}
		}
	}
	
	private float getMapValue(HSectionDim dim) {
		logger.fine("getMapValue");
		for(int a=0; a < ParametrsMap.size(); a++) 
			if(dim == ParametrsMap.get(a).dim) { 
				return ParametrsMap.get(a).dimValue; 
			}
		return 0;
	}
		
	public float getH() {
		return getMapValue(HSectionDim.h);
	}
	public float getB() {
		return getMapValue(HSectionDim.b);
	}
	public float getTf() {
		return getMapValue(HSectionDim.tf);
	}
	public float getTw() {
		return getMapValue(HSectionDim.tw);
	}
	public float getR() {
		return getMapValue(HSectionDim.r);
	}
}
