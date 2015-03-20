package com.github.luksrn.postgresql.domain

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
class ServerGroup {
	
	@Id
	@GeneratedValue
	Integer id
	
	@NotEmpty
	String name
	
	@ManyToOne
	@JoinColumn(name="id_user")
	User user
	
	@OneToMany
	List<Connection> connections;

 	
	String toString(){
		name
	}
}
