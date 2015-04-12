package com.github.luksrn.postgresql.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

import org.hibernate.validator.constraints.NotEmpty

@Entity
@Table(schema="public",name="server_group")
class ServerGroup {
	 
	@Id
	@SequenceGenerator(name="server_group_seq",sequenceName="server_group_seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="server_group_seq")
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
