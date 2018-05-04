package cjprofiles.forms;

import java.io.ByteArrayInputStream;
import cjprofiles.db.ProfileItem;
import cjprofiles.db.Profiles;
import cjprofiles.db.ProfilesFamily;
import cjprofiles.db.ProfilesImage;
import cjprofiles.db.ProfilesNorme;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.StringConverter;

public final class MainFormController{
	private MainApp mainAppReference;
	
	// mainForm controls
	
	@FXML private TableView<ProfileItem> propTable;
	@FXML private TableColumn<ProfileItem, String> propTableColKey;
	@FXML private TableColumn<ProfileItem, String> propTableColValue;
	@FXML private TableColumn<ProfileItem, String> propTableColUnit;
	@FXML private ComboBox<ProfilesNorme> profilesNormeCB;
	@FXML private ComboBox<ProfilesFamily> profilesFamilyCB;
	@FXML private ComboBox<Profiles> profilesCB;
	@FXML private ImageView profilesImageView;
	@FXML private TitledPane dxfPanel;
	@FXML private Button openDxfToClbBtn;
	@FXML private Button saveDxfToFileBtn;
	@FXML private CheckBox topViewChB;
	@FXML private CheckBox frontViewChB;
	@FXML private CheckBox sideViewChB;
	
	@FXML
    private void initialize() {
		propTableColKey.setCellValueFactory(new PropertyValueFactory<>("paramName"));
		propTableColValue.setCellValueFactory(new PropertyValueFactory<>("paramValue"));
		propTableColUnit.setCellValueFactory(new PropertyValueFactory<>("paramUnit"));
		
		StringConverter<ProfilesNorme> converter = new StringConverter<ProfilesNorme>() {
            @Override
            public String toString(ProfilesNorme profilesnorme) {
            	return profilesnorme.getCode() + " - " + profilesnorme.getNormeName();
            }
            @Override
            public ProfilesNorme fromString(String string) {
                return null;
            }
        };
        profilesNormeCB.setConverter(converter);
        
        StringConverter<ProfilesFamily> ProfilesFamilyConverter = new StringConverter<ProfilesFamily>() {
            @Override
            public String toString(ProfilesFamily profilesFamily) {
               	return profilesFamily.getProfileName();
            }
            @Override
            public ProfilesFamily fromString(String string) {
                return null;
            }
        };
        profilesFamilyCB.setConverter(ProfilesFamilyConverter);
        
        StringConverter<Profiles> ProfileConverter = new StringConverter<Profiles>() {
            @Override
            public String toString(Profiles ProfileConverter) {
            	return ProfileConverter.getProfileName();
            }
            @Override
            public Profiles fromString(String string) {
                return null;
            }
        };
        profilesCB.setConverter(ProfileConverter);
		
		profilesNormeCB.setOnAction((ActionEvent e) -> {
			mainAppReference.changeNorme( profilesNormeCB.getSelectionModel().getSelectedItem() );
		});
		
		profilesFamilyCB.setOnAction((ActionEvent e) -> {
			mainAppReference.changeFamily(profilesFamilyCB.getSelectionModel().getSelectedItem() );
		});
		
		profilesCB.setOnAction((ActionEvent e) -> {
			mainAppReference.changeProfile(profilesCB.getSelectionModel().getSelectedItem());
		});	
		
		
		openDxfToClbBtn.setOnAction((ActionEvent e) ->{
			mainAppReference.openDxf(profilesFamilyCB.getSelectionModel().getSelectedItem(), profilesCB.getSelectionModel().getSelectedItem(),
										topViewChB.isSelected(), frontViewChB.isSelected(), sideViewChB.isSelected());
		});
		
		saveDxfToFileBtn.setOnAction((ActionEvent e) ->{
			mainAppReference.saveDxf(profilesFamilyCB.getSelectionModel().getSelectedItem(), profilesCB.getSelectionModel().getSelectedItem(), 
										topViewChB.isSelected(), frontViewChB.isSelected(), sideViewChB.isSelected());
		});
		
    }
	
	public void setOBListToForm(ObservableList<ProfileItem> propTableList, ObservableList<ProfilesNorme> Normes, 
								ObservableList<ProfilesFamily> Family, ObservableList<Profiles> profile ) {
		propTable.setItems(propTableList);
		profilesNormeCB.setItems(Normes);
		profilesFamilyCB.setItems(Family);
		profilesCB.setItems(profile);
	}
	
	public void setProfilesNormeCBFirstIndex() {
		profilesNormeCB.getSelectionModel().selectFirst();
	}
	
	public void setProfilesFamilyCBFirstIndex() {
		profilesFamilyCB.getSelectionModel().selectFirst();
	}
	
	public void setDxfOptionDisable(boolean DisableDxf) {
		dxfPanel.setDisable(DisableDxf);
	}
	
	public void setProfilesFirstIndex() {
		profilesCB.getSelectionModel().selectFirst();
	}
	
	public void setFamilyImage(ProfilesImage familyImage) {
		if(familyImage == null) {
			return;
		}
		
		Image image = new Image(new ByteArrayInputStream(familyImage.getProfilesImage()));
		profilesImageView.setImage(image);
	}
	
	public void setMainAppReference(MainApp MainApp) {
		this.mainAppReference = MainApp;
	}
}
