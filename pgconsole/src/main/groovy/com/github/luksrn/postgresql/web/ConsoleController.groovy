package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.SqlHistoryRepository
import com.github.luksrn.postgresql.domain.SqlHistory
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.helper.QueryExecutor

@Controller
@RequestMapping(value="console")
class ConsoleController {
	
	QueryExecutor queyExecutor
	
	ConnectionRepository connectionRepository
	
	SqlHistoryRepository sqlHistoryRepository
	
	@Autowired
	ConsoleController ( QueryExecutor queryExecutor , ConnectionRepository connectionRepository , SqlHistoryRepository sqlHistoryRepository){
		this.queyExecutor = queryExecutor	
		this.connectionRepository = connectionRepository
		this.sqlHistoryRepository = sqlHistoryRepository
	}
	
	@RequestMapping(value="run",method=RequestMethod.POST)
	def run( @RequestParam("queries") String queries , 
					@RequestParam("idConnection") Integer idConnection,
					Model model, 
					Principal principal ){
		def user = new User( username: principal.getName() )
		def connection = connectionRepository.findOne( idConnection )
		
		def queriesMetaData = queyExecutor.execute( connection, queries )
	
		def sqls = queriesMetaData*.statement
		def sqlsHistory = sqls.collect { new SqlHistory( user: user , connection: connection, sql: it , dateCreated: new Date() ) }
		sqlHistoryRepository.save( sqlsHistory )
		
		model.addAttribute("queriesResultMetaData", queriesMetaData )
		
		"_output_resultset :: resultset"
	}	 
}
