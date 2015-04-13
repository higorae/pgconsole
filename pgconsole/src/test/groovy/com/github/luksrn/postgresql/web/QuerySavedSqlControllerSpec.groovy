package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.ui.Model

import spock.lang.Specification

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.SavedSqlRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.SavedSql
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.utils.PageWrapper


class QuerySavedSqlControllerSpec extends Specification {
	
	def instance 
	SavedSqlRepository savedSqlRepository
	ConnectionRepository connectionRepository
	Model model
	Principal principal 
	Pageable page
	
	def setup(){
		savedSqlRepository = Mock()
		connectionRepository = Mock()
		model = Mock()
		principal = Mock()
		page = Mock()
		instance = new QuerySavedSqlController(savedSqlRepository,connectionRepository)	
	}
	
	def "Show latest sql saved by the user authenticated"(){
		def idConnection = 1;
		def con = new Connection()
		def content = [ new SavedSql() ]
		Page<SavedSql> pageResult = new PageImpl<SavedSql>(content)
		
		when:
			def result = instance.search(idConnection, principal, model, page )
		then:
			1 * principal.getName() >> "luksrn"
			1 * connectionRepository.findOne(idConnection) >> con
			1 * savedSqlRepository.findByUserAndConnectionOrderByDateCreatedDesc(_ as User, _ as Connection, page ) >> pageResult			
			1 * model.addAttribute("savedSqls", pageResult.content )
			1 * model.addAttribute("savedSqlsPage",  _ as PageWrapper )
			result == '_output_saved_sql :: saved_sqls'
		
	}

}
