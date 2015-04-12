package com.github.luksrn.postgresql.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator

@Entity
class Connection {
	
	@Id
	@SequenceGenerator(name="connection_seq",sequenceName="connection_seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="connection_seq")	
	Integer id
	
	String host = 'localhost'
	
	String port = '5432'
	
	String username = 'postgres'
	
	String password
	
	String label
	
	String database
	
	@ManyToOne
	@JoinColumn(name="id_server_group")
	ServerGroup group = new ServerGroup()	
	
	@ManyToOne
	@JoinColumn(name="id_user")
	User user = new User()
	
	String color
	
	String getDescription(){
		"${group} - jdbc:postgresql://${host}:${port}/${database} - User: ${username}"
	}
	
	String getUrl(){
		"jdbc:postgresql://${host}:${port}/${database}"
	}
	
	String toString(){
		getUrl()
	}
}
