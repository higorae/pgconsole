package com.github.luksrn.postgresql.web

import java.security.Principal

import spock.lang.Specification
import spock.lang.Unroll

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.UserWipRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.domain.UserWip

class AutoSaveControllerSpec extends Specification {
	
	AutoSaveController instance
	ConnectionRepository connectionRepository
	UserWipRepository userWipRepository
	Principal principal
	Connection connection
	
	def setup(){		
		connectionRepository = Mock()
		userWipRepository = Mock()
		principal = Mock()
		connection = Mock()
		instance = new AutoSaveController(connectionRepository, userWipRepository)	
	}
	
  
	@Unroll
	def "ConsoleController must autosave work in progress idConnection #idConnection returns #message"(){
		when:
			def result = instance.autoSave( idConnection , "SELECT 1" , principal )
		then:
			result['message'].startsWith( message )
			interactions[0] * connectionRepository.findOne(idConnection) >> connection
			interactions[1] * principal.getName() >> 'luksrn'
			interactions[2] * userWipRepository.findByUserAndConnection( _ as User , connection ) >> wip
			interactions[3] * userWipRepository.save( _ as UserWip )
		where:
			user	 					| wip  			| idConnection 		| connection 		| 	message		| interactions	
			new User(username:'luksrn') | null 			| 1					| new Connection() 	|  'Save at'	| [ 1 , 1 , 1 , 1 ]				
			new User(username:'luksrn') | new UserWip() | 1					| new Connection() 	|  'Save at'    | [ 1 , 1 , 1 , 1 ] 
			new User(username:'luksrn') | null 			| null				| null				| 'To save automatic, please choose a connection'| [ 0 , 0 , 0 , 0 ] 
			
	}
}
