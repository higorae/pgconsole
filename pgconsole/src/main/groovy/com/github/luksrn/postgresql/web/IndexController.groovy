package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.dao.ServerGroupRepository
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.web.form.NewConnectionForm

@Controller
class IndexController {

	ConnectionRepository connectionRepository
	
	ServerGroupRepository serverGroupRepository
	
	@Autowired
	public IndexController(ConnectionRepository connectionRepository, ServerGroupRepository serverGroupRepository){
		this.connectionRepository = connectionRepository 
		this.serverGroupRepository = serverGroupRepository
	}
	
	@RequestMapping(value="/")
	String index(Model model, Principal principal){		
		def user = new User(username: principal.name )
		model.addAttribute("connections", connectionRepository.findByUser( user) ) 
		model.addAttribute("connection", new NewConnectionForm() )
		model.addAttribute("serverGroups", serverGroupRepository.findAllByUser( user ))

		"console"
	}
}
