package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.ui.Model

import spock.lang.Specification

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.SqlHistoryRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.SqlHistory
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.domain.UserWip
import com.github.luksrn.postgresql.utils.PageWrapper

class UserHistoryControllerSpec extends Specification {

	UserHistoryController instance
	Model model
	Principal principal
	SqlHistoryRepository repository
	ConnectionRepository connectionRepository
	org.springframework.data.domain.Pageable page
	
	def setup(){
		model = Mock()
		principal = Mock()
		repository = Mock()
		page = Mock()
		connectionRepository = Mock()
		instance = new UserHistoryController( repository , connectionRepository  )
	}
	
	def "When provided a connection, must show the history of the logged in user"(){
		def content = [ new SqlHistory() ]
		Page<SqlHistory> pageResult = new PageImpl<SqlHistory>(content)
			 
		when:
			def result = instance.history( 1 , principal, model , page )
		then:			
			1 * connectionRepository.findOne( 1 ) >> new Connection()
			1 * principal.getName() >> "luksrn"
			1 * repository.findByUserAndConnection( _ as User, _ as Connection, page ) >> pageResult
			1 * model.addAttribute("userHistory", pageResult.content )
			1 * model.addAttribute("userHistoryPage",  _ as PageWrapper )
			result == '_output_history :: user_history'	
	}
}
