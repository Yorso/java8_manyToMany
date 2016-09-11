package com.jorge.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

// INVERSED END side
// We decide this class is not the owner

@Entity
@Table(name="actor")
public class Actor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="name")
	private String name;
	
	@ManyToMany(mappedBy="actors") // 'actors' is the name of the private attribute in Movie.java class (OWNER) => private Set<Actor> actors; 
			  			           // 'mappedBy' attribute declares this class as not responsible for the relationship => inversed end
	private Set<Movie> movies = new HashSet<Movie>();
	
	public Actor() {}
	
	public Actor(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}

	@Override
	public String toString() {
		return "Actor [id=" + id + ", name=" + name + "]";
	}

}
