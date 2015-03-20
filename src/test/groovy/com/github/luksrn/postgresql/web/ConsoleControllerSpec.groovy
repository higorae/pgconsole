package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.ui.Model

import spock.lang.Specification
import spock.lang.Unroll

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.SqlHistoryRepository;
import com.github.luksrn.postgresql.dao.UserWipRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.domain.UserWip
import com.github.luksrn.postgresql.helper.QueryExecutor

class ConsoleControllerSpec extends Specification {

	def instance
	QueryExecutor queryExecutor
	ConnectionRepository connectionRepository
	SqlHistoryRepository sqlHistoryRepository
	Principal principal
	Model model
	
	
	def setup(){		
		queryExecutor = Mock()
		connectionRepository = Mock()
		sqlHistoryRepository = Mock()
		principal = Mock()
		model = Mock()
		instance = new ConsoleController(queryExecutor, connectionRepository, sqlHistoryRepository)
	}
	
	def "ConsoleControler must run the SQL and render the page (Fragment)"(){
	
		def connectionInstance = new Connection()
		def queryMetaDatas = []
		 
		when:
			
			def result = instance.run( "SELECT 1", 1 , model , principal )
		then:
			1 * principal.getName() >> "luksrn"
			1 * connectionRepository.findOne(1)  >> connectionInstance
			1 * queryExecutor.execute( connectionInstance , "SELECT 1" )  >> queryMetaDatas
			1 * sqlHistoryRepository.save( _ as List )
			1 * model.addAttribute("queriesResultMetaData", queryMetaDatas )
			result == '_output_resultset :: resultset'
	}
	

}
