package com.github.luksrn.postgresql.web

import java.security.Principal

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import ch.qos.logback.classic.Logger

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.SavedSqlRepository
import com.github.luksrn.postgresql.domain.SavedSql
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.utils.PageWrapper

@Controller
class QuerySavedSqlController {
	
	Logger logger = LoggerFactory.getLogger(QuerySavedSqlController)

	SavedSqlRepository savedSqlRepository	
	ConnectionRepository connectionRepository
	
	@Autowired
	QuerySavedSqlController(SavedSqlRepository savedSqlRepository,
							ConnectionRepository connectionRepository){
		this.savedSqlRepository = savedSqlRepository
		this.connectionRepository = connectionRepository
	}
	
	@RequestMapping(value="savedsql/search",method=RequestMethod.GET)
	def search( @RequestParam("idConnection") Integer idConnection, 
					Principal principal , Model model, Pageable page){
					
		def connection = connectionRepository.findOne( idConnection )
		if ( !connection ){
			model.addAttribute("savedSqls", [] )
		
			return '_output_saved_sql :: saved_sqls'
		}
		
		def user = new User(username: principal.getName())
		
		logger.info ("Searching saved sql for ${user.username} and ${connection.url}")
		Page savedSqls = savedSqlRepository.findByUserAndConnectionOrderByDateCreatedDesc( user, connection,  page )
		logger.info ("Found ${savedSqls}")
		
		model.addAttribute("savedSqls", savedSqls.content )
		model.addAttribute("savedSqlsPage", new PageWrapper<SavedSql>( savedSqls , "/savedsql/search") )
		
		'_output_saved_sql :: saved_sqls'
	}
}
