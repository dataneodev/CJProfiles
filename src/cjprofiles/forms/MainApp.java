package cjprofiles.forms;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import cjprofiles.db.DBControl;
import cjprofiles.db.ProfileItem;
import cjprofiles.db.Profiles;
import cjprofiles.db.ProfilesFamily;
import cjprofiles.db.ProfilesNorme;
import cjprofiles.db.ProfilesProperties;
import cjprofiles.drawers.Drawer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public final class MainApp extends Application {
	private static final Logger logger = Logger.getLogger( MainApp.class.getName() );
	public final static String AppName = "CJProfiles";
	public final static float AppVersion = 1.0f;
	public final static String AppAutor ="dataneo";
	
	public final static String DBFileExt = ".pdb";
		
	private ObservableList<ProfileItem> profilePropertiesOL = FXCollections.observableArrayList();
	private ObservableList<ProfilesNorme> profilesNormeOL = FXCollections.observableArrayList();
	private ObservableList<ProfilesFamily> profilesFamilyOL = FXCollections.observableArrayList();
	private ObservableList<Profiles> profilesListOL = FXCollections.observableArrayList();
	
	// controls from mainForm
	private Stage primaryStage;
	private MainFormController mFormController;
		
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		if(initMainForm(primaryStage)) {
			initProgram();
		}
	}
	
	private boolean initMainForm(Stage primaryStage){
		logger.fine("initMainForm");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle(MainApp.AppName + " " + AppVersion + " @" + AppAutor);
        	
        String sourceXMLFile = isProgramRunnedFromJar() ? "/src/cjprofiles/forms/MainForm.fxml" : "MainForm.fxml";
            
        FXMLLoader loader = new FXMLLoader(getClass().getResource(sourceXMLFile));
        AnchorPane mainForm = null;
        try {
        	mainForm = (AnchorPane) loader.load();
        } catch (IOException e) {
        	logger.warning("initMainForm: " + e);
            e.printStackTrace();
            return false;
        }                
        
        mFormController = (MainFormController) loader.getController();
        mFormController.setMainAppReference(this);
        mFormController.setOBListToForm(profilePropertiesOL, profilesNormeOL, profilesFamilyOL, profilesListOL);
             
        Scene scene = new Scene(mainForm);
        primaryStage.setScene(scene);
        primaryStage.setWidth(580);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.show();
        return true;
	}
	
	// main program Tread
	protected void initProgram() {
		logger.fine("initProgram");
		DBControl.loadProfilesNorme(profilesNormeOL);
		mFormController.setProfilesNormeCBFirstIndex();
	}
	
	public void changeNorme(ProfilesNorme selectedNorme) {
		if( selectedNorme == null) {return;}
		DBControl.loadProfilesFamily(selectedNorme, profilesFamilyOL);
		profilesListOL.clear();
		mFormController.setProfilesFamilyCBFirstIndex(); 	
	}
	
	public void changeFamily(ProfilesFamily selectedFamily) {
		mFormController.setDxfOptionDisable(true);
		if( selectedFamily == null) {return;}
		
		DBControl.loadProfiles(selectedFamily, profilesListOL); 
		mFormController.setProfilesFirstIndex();
		mFormController.setFamilyImage( DBControl.loadProfilesImage(selectedFamily));
		if(Drawer.isFamilyHasDrawer(selectedFamily)) { mFormController.setDxfOptionDisable(false); }
	}
	
	public void changeProfile(Profiles selectedProfile) {
		profilePropertiesOL.clear();
		if( selectedProfile == null) {return;}
		
		final ProfilesProperties ProfilesProperties = DBControl.loadProfilesProperties(selectedProfile);
		if( ProfilesProperties == null) {return;}
		List<ProfileItem> parametrs = ProfilesProperties.getProfileCharacteristicList();
		if(parametrs.size() == 0) { return; }
		for(ProfileItem item: parametrs) {
			profilePropertiesOL.add(item);
		}
	}
	
	public void openDxf(ProfilesFamily selectedFamily, Profiles selectedProfile, boolean topView, 
						boolean frontView, boolean sideView) {
		final ProfilesProperties ProfilesProp = DBControl.loadProfilesProperties(selectedProfile);	
		Drawer.OpenDxf(selectedFamily, ProfilesProp, topView, frontView, sideView); 
	}
	
	public void saveDxf(ProfilesFamily selectedFamily, Profiles selectedProfile, boolean topView, boolean frontView, 
						boolean sideView) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialFileName( selectedProfile.getProfileName() + ".dxf");
		ExtensionFilter fileFilter = new ExtensionFilter("dxf file", "*.dxf");
		fileChooser.getExtensionFilters().add(fileFilter);
		fileChooser.setTitle("Open Resource File");
		
		File file = fileChooser.showSaveDialog(primaryStage);
		if(file != null) {
			final ProfilesProperties ProfilesProp = DBControl.loadProfilesProperties(selectedProfile);
			Drawer.dxfToFile(file.getAbsolutePath(), selectedFamily, ProfilesProp, topView, frontView, sideView); 
		}
	}
	
	public static boolean isProgramRunnedFromJar() {
	    File x = getCurrentJarFileLocation();
	    if(x.getAbsolutePath().contains("target"+File.separator+"classes")){
	        return false;
	    } else {
	        return true;
	    }
	}
	static File getCurrentJarFileLocation() {
	        try {
	            return new File(MainApp.class.getProtectionDomain().getCodeSource().getLocation().getPath());
	        } catch(NullPointerException e){
	            e.printStackTrace();
	            return null;
	        }
	}
}
