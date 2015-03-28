package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.UserWipRepository
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.domain.UserWip

@Controller
@RequestMapping("console")
class AutoSaveController {
	 
	def connectionRepository
	
	def userWipRepository
	
	@Autowired
	AutoSaveController(ConnectionRepository connectionRepository, UserWipRepository userWipRepository){
		this.connectionRepository = connectionRepository
		this.userWipRepository = userWipRepository		
	}
	
	@RequestMapping(value="autoSave", method=RequestMethod.POST)
	@ResponseBody
	def autoSave( @RequestParam("idConnection") Integer idConnection , @RequestParam("code") String code , Principal principal ){
		
		if ( idConnection && code ){
			def connection = connectionRepository.findOne( idConnection )
			def user = new User( username: principal.getName() )
			def workInProgress = userWipRepository.findByUserAndConnection( user  , connection )
			if ( !workInProgress ){
				workInProgress = new UserWip( user: user, connection : connection )
			}
			workInProgress.lastUpdated = new Date()
			workInProgress.code = code
			userWipRepository.save( workInProgress )
			return [ message: "Save at " + workInProgress.lastUpdated.format('dd/MM/yyyy hh:mm:ss') ]
		} else {
			return [ message:'To save automatic, please choose a connection' ]
		}
	}
}
