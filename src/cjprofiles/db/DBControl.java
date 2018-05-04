package cjprofiles.db;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import javafx.collections.ObservableList;

public final class DBControl {
	private static final Logger logger = Logger.getLogger( DBControl.class.getName() );
	
	public static void shutdown() {
		logger.fine("shutdown");
		HibernateUtil.shutdown();
	}
	
	// load norme list from db
	public static void loadProfilesNorme(ObservableList<ProfilesNorme> profilesNormeOL) throws NullPointerException {
		logger.fine("loadProfilesNorme");
		if(profilesNormeOL == null) {
			throw new NullPointerException("loadProfilesNorme: profilesNormeOL = null");
		}
		profilesNormeOL.clear();
		Session session = null;
	    Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			List<ProfilesNorme> normeList = (List<ProfilesNorme>) session.createQuery("FROM ProfilesNorme").list();
			for(ProfilesNorme item : normeList) {
				profilesNormeOL.add(item);
			}
			transaction.commit();
		}
		catch(Exception e) {
			logger.warning("loadProfilesNorme: " + e.getMessage()); 
			if (transaction != null) {
	            transaction.rollback();
	         }
		}
		finally {
			if (session != null) {
	            session.close();
	        }
		}
	}
	
	// load familes list from db
	public static void loadProfilesFamily(ProfilesNorme selectedNorme, 
							ObservableList<ProfilesFamily> profilesFamilyOL) throws NullPointerException {
		logger.fine("loadProfilesFamily");
		if(selectedNorme == null) {
			throw new NullPointerException("loadProfilesFamily: selectedNorme = null");
		}
		if(profilesFamilyOL == null) {
			throw new NullPointerException("loadProfilesFamily: profilesFamilyOL = null");
		}
		profilesFamilyOL.clear();
		Session session = null;
	    Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			List<ProfilesFamily> familyList = (List<ProfilesFamily>) session.createQuery(
							"FROM ProfilesFamily WHERE profileNormeId = " + selectedNorme.getId() ).list();
			for(ProfilesFamily item : familyList) {
				profilesFamilyOL.add(item);
			} 
			transaction.commit(); 
		}
		catch(Exception e) {
			logger.warning("loadProfilesFamily: " + e.getMessage()); 
			if (transaction != null) {
	            transaction.rollback();
	         }
		}
		finally {
			if (session != null) {
	            session.close();
	        }
		}
	}	
	
	// load profiles list from db
	public static void loadProfiles(ProfilesFamily selectedFamily,
							ObservableList<Profiles> profilesListOL) throws NullPointerException {
		logger.fine("loadProfiles");
		if(selectedFamily == null) {
			throw new NullPointerException("loadProfiles: selectedFamily = null");
		}
		if(profilesListOL == null) {
			throw new NullPointerException("loadProfiles: profilesListOL = null");
		}
		profilesListOL.clear();
		Session session = null;
	    Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			List<Profiles> profilesList = (List<Profiles>) session.createQuery("SELECT new cjprofiles.db.Profiles"+
										"(p.id, p.profileNormeId, p.profileFamilyId, p.profileName) FROM ProfilesProperties"+
										" p WHERE profileFamilyId = " +	selectedFamily.getId() ).list();
			for(Profiles item : profilesList) {
				profilesListOL.add(item);
			}
			transaction.commit(); 
		} 
		catch(Exception e) {
			logger.warning("loadProfiles: " + e.getMessage()); 
			if (transaction != null) {
	            transaction.rollback();
	         }
		}
		finally {
			if (session != null) {
	            session.close();
	        }
		}
	}
	
	// load profile Properties from db
	public static ProfilesProperties loadProfilesProperties(Profiles selectedProfile) throws NullPointerException {
		logger.fine("ProfilesProperties");
		if(selectedProfile == null) {
			throw new NullPointerException("loadProfilesProperties: selectedProfile = null");
		}
		ProfilesProperties result = null;
		Session session = null;
	    Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			result = session.get(ProfilesProperties.class, selectedProfile.getId());
			transaction.commit(); 
		} 
		catch(Exception e) {
			logger.warning("loadProfilesProperties: " + e.getMessage()); 
			if (transaction != null) {
	            transaction.rollback();
	         } 
		}
		finally {
			if (session != null) {
	            session.close();
	        }
		}
		return result;
	}	
	
	// load profile Image from db
	public static ProfilesImage loadProfilesImage(ProfilesFamily selectedFamily) throws NullPointerException {
		logger.fine("ProfilesImage");
		if(selectedFamily == null) {
			throw new NullPointerException("loadProfilesImage: selectedFamily == null");			
		}
		ProfilesImage result = null;
		Session session = null;
	    Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			result = session.get(ProfilesImage.class, selectedFamily.getProfileImageId());
			transaction.commit();  
		}
		catch(Exception e) {
			logger.warning("loadProfilesImage: " + e.getMessage()); 
			if (transaction != null) {
	            transaction.rollback();
	         }  
		}
		finally {
			if (session != null) {
	            session.close();
	        }
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
