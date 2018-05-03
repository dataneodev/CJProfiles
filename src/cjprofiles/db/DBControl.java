package cjprofiles.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

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


class HibernateUtil{
	private static StandardServiceRegistry registry;
	private static SessionFactory sessionFactory;
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				StandardServiceRegistryBuilder registryBuilder =  new StandardServiceRegistryBuilder();

		        Map<String, String> settings = new HashMap<>();
		        settings.put("hibernate.dialect", "org.hibernate.dialect.SQLiteDialect");
		        settings.put("hibernate.connection.driver_class", "org.sqlite.JDBC");
		        settings.put("hibernate.connection.url", "jdbc:sqlite:EC.pdb");
		        settings.put("hibernate.connection.username", "");
		        settings.put("hibernate.connection.password", "");
		        settings.put("hibernate.format_sql", "true");
		        settings.put("hibernate.show_sql", "false");
		        settings.put("hibernate.hbm2ddl.auto", "validate");

		        registryBuilder.applySettings(settings);

		        registry = registryBuilder.build();

		        MetadataSources sources = new MetadataSources(registry);
		        sources.addAnnotatedClass(ProfilesNorme.class);
		        sources.addAnnotatedClass(ProfilesFamily.class);
		        sources.addAnnotatedClass(ProfilesImage.class);
		        sources.addAnnotatedClass(ProfilesProperties.class);

		        Metadata metadata = sources.getMetadataBuilder().build();

		        sessionFactory = metadata.getSessionFactoryBuilder().build();
		      } catch (Exception e) {
		        System.out.println("SessionFactory creation failed");
		        if (registry != null) {
		          StandardServiceRegistryBuilder.destroy(registry);
		        }
		      }
		    }
		    return sessionFactory;
	}
	
	public static void shutdown() {
	    if (registry != null) {
	      StandardServiceRegistryBuilder.destroy(registry);
	    }
	  }
}
