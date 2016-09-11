package com.jorge.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//OWNER of this bidirectional relationship
//The owner of the relationship is responsible for the association column(s) update
//We decide this class is the owner

@Entity
@Table(name="movie")
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	//@Column(name="name")
	private String name;
	
	@ManyToMany(cascade={CascadeType.PERSIST})
	@JoinTable( // This creates a 'movie_actor' intermediate table/join table/link table whose fields are the IDs of movie and actor tables
			name="movie_actor",
			joinColumns={@JoinColumn(name="movie_id")},
		    inverseJoinColumns={@JoinColumn(name="actor_id")}
	)
    									        
	private Set<Actor> actors = new HashSet<Actor>();
	
	public Movie() {}
	
	public Movie(String name) {
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
	
	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", name=" + name + ", actors=" + actors + "]";
	}
	
}
