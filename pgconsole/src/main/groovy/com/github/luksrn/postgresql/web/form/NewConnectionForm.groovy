package com.github.luksrn.postgresql.web.form

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.github.luksrn.postgresql.domain.Connection

class NewConnectionForm {

	@NotEmpty
	String host = 'localhost'
	
	@NotEmpty
	String port = '5432'
	
	@NotEmpty
	String username = 'postgres'
	
	@NotEmpty
	String password
	
	String label
	
	@NotEmpty
	String database
	
	@NotNull
	Integer groupId;
	
	def buildObject(){
		
		def connection = new Connection(host: host, 
											port: port, 
											username: username, 
											password: password, 
											label: label, 
											database: database)
		connection.group.id = groupId
		
		connection		
	}
}
