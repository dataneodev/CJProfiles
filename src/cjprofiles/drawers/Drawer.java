package cjprofiles.drawers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import cjprofiles.db.ProfilesFamily;
import cjprofiles.db.ProfilesProperties;

public final class Drawer {
	private static final Logger logger = Logger.getLogger( Drawer.class.getName() );
	
	public static boolean isFamilyHasDrawer(ProfilesFamily selectedFamily){
		if(selectedFamily == null) { 
			logger.log( Level.WARNING, "isFamilyHasDrawer: Object ProfilesFamily is null" );
			return false;
		}
		// checking is family has drawer
		logger.fine("isFamilyHasDrawer ProfilesFamily.getProfileDrawerId()=" + selectedFamily.getProfileDrawerId() );
		if(selectedFamily.getProfileDrawerId() > 0) { return true; }
		return false;
	}
	
	public static void OpenDxf(ProfilesFamily selFamily, ProfilesProperties profileProp, boolean topView,
								boolean frontView, boolean sideView){
		logger.fine("OpenDxf.");
		if(!isFamilyHasDrawer(selFamily)) {
			logger.warning("OpenDxf:!isFamilyHasDrawer(selectedFamily)");
			return;
		}	
	
		String dxfBody = "";
		try {
			dxfBody = genDxf(profileProp, selFamily.getProfileDrawerId(), topView, frontView, sideView);	
		} catch(NullPointerException | IllegalArgumentException e) {
			logger.warning("OpenDxf: "+e);
			return;			
		}
		
		if(dxfBody.length() == 0) {
			logger.warning("OpenDxf:dxfBody.length() == 0");
			return;
		}
		
		String tmpDir = System.getProperty("java.io.tmpdir"); 
		String filePath = tmpDir+profileProp.getProfileName()+"_"+".dxf";
		
		try (PrintWriter out = new PrintWriter(filePath)) {
			   out.print(dxfBody);
			   out.close();
		}catch ( FileNotFoundException e) {
			logger.warning("OpenDxf:FileNotFoundException");
			return;
		}

		File file = new File(filePath);
		Desktop desktop = Desktop.getDesktop();
		try {
			desktop.open(file);
		} catch(IOException e) {
			logger.warning("OpenDxf:FileNotFoundException");	
		}
	}
	
	public static void dxfToFile(String filePath, ProfilesFamily selFamily, ProfilesProperties profileProp, boolean topView, 
								boolean frontView, boolean sideView){
		logger.fine("dxfToFile.");
		if(!isFamilyHasDrawer(selFamily)) {
			logger.warning("dxfToFile:!isFamilyHasDrawer(selectedFamily)");
			return;
		}
		
		String dxfBody = "";
		try {
			dxfBody = genDxf(profileProp, selFamily.getProfileDrawerId(), topView, frontView, sideView);	
		} catch(NullPointerException | IllegalArgumentException e) {
			logger.warning("OpenDxf:"+e);
			return;			
		}
		
		if(dxfBody.length() == 0) {
			logger.warning("dxfToFile:dxfBody.length() == 0");
			return;
		}
		
		PrintWriter out = null;
		try{
			out = new PrintWriter(filePath);
			out.print(dxfBody);
		}catch(FileNotFoundException e) {
			logger.warning("dxfToFile:" + e);
		}
		finally {
			out.close();
		}
	}
	
	private static String genDxf(ProfilesProperties profileProperties, int drawerId, boolean topView,
									boolean frontView, boolean sideView)  throws NullPointerException, IllegalArgumentException {
		logger.fine("genDxf");
		if(profileProperties == null) {
			throw new NullPointerException("genDxf: profileProperties == null");
		}
		DxfDrawer dxfBody;
		
		switch(drawerId) {
			case 1:
				dxfBody = new Sections_H(); 
				break;
			default:
				dxfBody = null;
			}
		
		if(dxfBody == null) {
			throw new NullPointerException("genDxf: dxfBody == null");
		}
		
		dxfBody.setData(profileProperties, drawerId);
		return dxfBody.getDxfBody(topView, frontView, sideView);
	}
}

interface DxfDrawer{
	void setData(ProfilesProperties profileProperties, int darwerId);
	String getDxfBody(boolean topView, boolean frontView, boolean sideView) throws IllegalArgumentException;
}



