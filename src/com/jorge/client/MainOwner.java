package com.jorge.client;


import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jorge.entity.Movie;
import com.jorge.entity.Actor;
import com.jorge.util.HibernateUtil;

/**
 * This is a MANY TO MANY bidirectional relationship
 * 
 * In this example:
 * 		We have Movie that can have many actors in it
 * 		Actor can act in many movies
 * 
 * These things make a many to many bidireccional relationship
 * 
 */

public class MainOwner {

	public static void main(String[] args) {
		BasicConfigurator.configure(); // Necessary for configure log4j. It must be the first line in main method
								       // log4j.properties must be in /src directory
		
		Logger  logger = Logger.getLogger(MainOwner.class.getName());
		logger.debug("log4j configured correctly and logger set");

		logger.debug("getting session factory from HibernateUtil.java");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = session.getTransaction();
		
		try {
			
			logger.debug("beginning owner persist transaction");
			txn.begin(); 

			logger.debug("setting movie data");
			Movie movie1 = new Movie("Batman Begins");
			Movie movie2 = new Movie("The Dark Knight");
			
			logger.debug("setting actor data");
			Actor actor1 = new Actor("Christian Bale");
			Actor actor2 = new Actor("Gary Oldman");
			
			// Remember that Movie is the Owner of the relationship
			logger.debug("persisting data from owner side");
			movie1.getActors().add(actor1);
			
			movie2.getActors().add(actor1);
			movie2.getActors().add(actor2);
			
			logger.debug("persisting data");
			session.persist(movie1);
			session.persist(movie2);
		
			logger.debug("making commit");
			txn.commit();
			
		} catch (Exception e) {
			if (txn != null) {
				logger.error("something was wrong, making rollback of transactions");
				txn.rollback(); // If something was wrong, we make rollback
			}
			logger.error("Exception: " + e.getMessage().toString());
		} finally {
			if (session != null) {
				logger.debug("close session");
				session.close();
			}
		}
	}

}
