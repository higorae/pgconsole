package com.github.luksrn.postgresql.web
 

import java.security.Principal

import spock.lang.Specification

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.ServerGroup
import com.github.luksrn.postgresql.domain.UserWip
import com.github.luksrn.postgresql.helper.ConnectionHolder
import com.github.luksrn.postgresql.helper.UserWipService

class ChangeConnectionControllerSpec extends Specification {
	
	ChangeConnectionController instance
	ConnectionRepository connectionRepository
	ConnectionHolder connectionHolder
	UserWipService userWipService
	Principal principal
	
	def setup(){
		connectionRepository = Mock()
		connectionHolder = Mock()
		userWipService = Mock()
		principal = Mock()
		instance = new ChangeConnectionController(connectionRepository, connectionHolder, userWipService)
	}
	
	def "Change connection and hold current connection informations and save work in progress"(){
		def result
	 
		when:
			result = instance.changeConnection( idConnection , "SELECT 1", principal )
		then:
			result.connection ==  "Server - jdbc:postgresql://localhost:5432/db - User: admin"
			result.code ==  wip.code
			1 * connectionRepository.findOne( idConnection ) >> connection 			
			1 * userWipService.getUserWip( connection , principal) >> wip
			1 * connectionHolder.setCurrentConnection(connection)
			
		where:
			idConnection	|	connection		 																												 |	currentConnection	|	wip				 
			2				|   new Connection( group: new ServerGroup(name: 'Server'), host: 'localhost', port: 5432 , database: 'db', username: 'admin' ) 	 | null					|	new UserWip(code:"SELECT 2") 	
		
	}

}
