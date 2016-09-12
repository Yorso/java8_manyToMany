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

public class MainInverseEnd {

	public static void main(String[] args) {
		BasicConfigurator.configure(); // Necessary for configure log4j. It must be the first line in main method
								       // log4j.properties must be in /src directory
		
		Logger  logger = Logger.getLogger(MainInverseEnd.class.getName());
		logger.debug("log4j configured correctly and logger set");

		logger.debug("getting session factory from HibernateUtil.java");
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction txn = session.getTransaction();
		
		try {
			
			logger.debug("beginning inverse end persist transaction");
			txn.begin(); 

			logger.debug("getting movie data");
			Movie movie = (Movie) session.get(Movie.class, 1L);
			
			logger.debug("getting actor data");
			Actor actor = (Actor) session.get(Actor.class, 2L);
			
			// Remember that Movie is the Owner of the relationship
			logger.debug("updating data from inverse end side, not from owner? =>>> NO WAY!!!");
			actor.getMovies().add(movie); // Updating inverse end??? => The inverse end will be ignored when updating the relationship values in the join table.
			                              // Remember that inverse end is not responsible for the relationship, so it will not update 'movie_actor' join table, it will have no effect in that table
			logger.debug("updating data from owner? =>>> OK!!!");
			movie.getActors().add(actor); // Updating owner => this is the way to update the join table, new row in 'movie_actor' join table
			
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
