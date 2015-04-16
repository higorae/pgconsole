package com.github.luksrn.postgresql.web.form

import com.github.luksrn.postgresql.domain.User

import javax.validation.constraints.AssertTrue;
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

	Integer groupId

    String groupLabel
	
	@NotEmpty
	String color;
	
	def buildObject(){
		
		def connection = new Connection(host: host, 
											port: port, 
											username: username, 
											password: password, 
											label: label, 
											database: database,
											color: color)
		// TODO Need implement cross field validations. 
		if ( groupLabel ) {
            connection.group.name = groupLabel
        } else {
            connection.group.id = groupId
        }

		connection		
	}
}
