package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.SqlHistoryRepository
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.domain.UserWip
import com.github.luksrn.postgresql.utils.PageWrapper

@Controller
class UserHistoryController {
	SqlHistoryRepository sqlHistoryRepository
	ConnectionRepository connectionRepository
	
	@Autowired
	UserHistoryController(SqlHistoryRepository sqlHistoryRepository, ConnectionRepository connectionRepository){
		this.sqlHistoryRepository = sqlHistoryRepository;
		this.connectionRepository = connectionRepository
	}
	
	@RequestMapping(value="console/history",method=RequestMethod.GET)
	def history( @RequestParam("idConnection") Integer idConnection , Principal principal , Model model, Pageable page){
		def connection = connectionRepository.findOne( idConnection )
		if ( !connection ){
			model.addAttribute("userHistory", [] )
		
			return "_output_history :: user_history"
		}
		
		def user = new User(username: principal.getName())
		
		Page history = sqlHistoryRepository.findByUserAndConnectionOrderByDateCreatedDesc( user, connection,  page )
 
		model.addAttribute("userHistory", history.content )
		model.addAttribute("userHistoryPage", new PageWrapper<UserWip>( history , "/console/history") )
		
		"_output_history :: user_history"
	}
}
