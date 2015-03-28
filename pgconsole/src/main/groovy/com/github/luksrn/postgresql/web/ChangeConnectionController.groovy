package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.helper.ConnectionHolder
import com.github.luksrn.postgresql.helper.UserWipService

@Controller
@RequestMapping('/console')
class ChangeConnectionController {

	def connectionRepository
	ConnectionHolder connectionHolder
	UserWipService userWipService
	
	@Autowired
	ChangeConnectionController(ConnectionRepository connectionRepository, ConnectionHolder connectionHolder , UserWipService userWipService){
		this.connectionRepository = connectionRepository
		this.connectionHolder = connectionHolder
		this.userWipService = userWipService
	}
	
	
	@RequestMapping(value="changeConnection", method=RequestMethod.POST)
	@ResponseBody
	def changeConnection( @RequestParam("idConnection") Integer idConnection , @RequestParam("code") String code, Principal principal ){
		def connection = connectionRepository.findOne( idConnection )
		if ( !connection ){
			return [message: 'It was not possible to change the connection. Connection not found.', code: code ]
		}
		
		// restore WIP code for selected connection
		def wip = userWipService.getUserWip( connection , principal)		
		
		connectionHolder.setCurrentConnection( connection )		 
		
		return [ connection : connection.description , code: wip?.code ]
	}
	
	
}
