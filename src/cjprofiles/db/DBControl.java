package cjprofiles.db;

import java.util.List;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import cjprofiles.forms.MainApp;
import javafx.collections.ObservableList;

public final class DBControl {
	
	/* load norme from db */ 
	public static boolean loadProfilesNorme(ObservableList<ProfilesNorme> profilesNormeOL) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Transaction tx = session.beginTransaction();
			List<ProfilesNorme> normeList = session.createQuery("FROM ProfilesNorme").list();
			profilesNormeOL.clear();
			for(ProfilesNorme a : normeList) {
				profilesNormeOL.add( new ProfilesNorme(a.getId(), a.getNormeName(), a.getCode()) );
			}
			tx.commit();
		}
		catch(Exception e) {
			  e.printStackTrace(); 
		}
		finally {
			session.close();
		}
		return true;
	}
	
	// load familes from db
	public static boolean loadProfilesFamily(ProfilesNorme selectedNorme, ObservableList<ProfilesFamily> profilesFamilyOL) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			List<ProfilesFamily> familyList = session.createQuery("FROM ProfilesFamily WHERE profileNormeId = " + selectedNorme.getId() ).list();
			profilesFamilyOL.clear();
			for(ProfilesFamily a : familyList) {
				profilesFamilyOL.add(a);
			} /// new ProfilesFamily( a.getId(), a.getProfileName(), a.getProfileNormeId(), a.getProfileDrawerId() )
			
			tx.commit(); 
		}
		catch(Exception e) {
			  e.printStackTrace(); 
		}
		finally {
			session.close();
		}
		return true;
	}	
	
	// load profiles list
	public static boolean loadProfiles(ProfilesFamily selectedFamily, ObservableList<Profiles> profilesListOL) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			List<Profiles> profilesList = session.createQuery("SELECT new cjprofiles.db.Profiles(p.id, p.profileNormeId, p.profileFamilyId, p.profileName) "
					+ "FROM ProfilesProperties p WHERE profileFamilyId = " + selectedFamily.getId() ).list();
			
			profilesListOL.clear();
			for(Profiles a : profilesList) {
				profilesListOL.add( new Profiles( a.getId(), a.getProfileFamilyId(), a.getProfileNormeId(), a.getProfileName() ));
			}
			
			tx.commit(); 
		}
		catch(Exception e) {
			  e.printStackTrace(); 
		}
		finally {
			session.close();
		}
		return true;
	}
	
	// load profile Properties
	public static ProfilesProperties loadProfilesProperties(Profiles selectedProfile) {
			//return false;
			ProfilesProperties result = null;
			Session session = HibernateUtil.getSessionFactory().openSession();
			try {
				Transaction tx = session.beginTransaction();
				List<ProfilesProperties> profilesList = session.createQuery("FROM ProfilesProperties WHERE id = " + selectedProfile.getId() ).list();
				if(profilesList.size() > 0) { 
					result = profilesList.get(0); 
				}
				tx.commit(); 
			}
			catch(Exception e) {
				  e.printStackTrace(); 
			}
			finally {
				session.close();
			}
			
			return result;
		}	
	
	// load profile Image
	public static ProfilesImage loadProfilesImage(ProfilesFamily selectedFamily) {
		ProfilesImage result = null;
		if(selectedFamily == null) {
			return result;
			
		}
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Transaction tx = session.beginTransaction();
			List<ProfilesImage> images = session.createQuery("FROM ProfilesImage WHERE id = " + selectedFamily.getProfileImageId() ).list();
			if(images.size() > 0) { 
				result = images.get(0); 
			} 
			tx.commit(); 
		}
		catch(Exception e) {
			  e.printStackTrace(); 
		}
		finally {
			session.close();
		}
		
		return result;
	}
}

class HibernateUtil {	 
    private static final SessionFactory sessionFactory = buildSessionFactory();
 
    private static SessionFactory buildSessionFactory() {
    	try {
    		java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.WARNING);
    		String hibernateCfgFile = MainApp.isProgramRunnedFromJar() ? "/src/hibernate.cfg.xml" : "hibernate.cfg.xml";
    		StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure(hibernateCfgFile).build();
    	    Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
    	    return metaData.getSessionFactoryBuilder().build();
    	} catch (Throwable th) {
    		System.err.println("Enitial SessionFactory creation failed" + th);
   	        throw new ExceptionInInitializerError(th);
    	}
    }
 
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
