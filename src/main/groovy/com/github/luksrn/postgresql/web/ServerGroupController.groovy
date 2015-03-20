package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.luksrn.postgresql.dao.ServerGroupRepository
import com.github.luksrn.postgresql.domain.ServerGroup

@Controller
@RequestMapping(value="servergroup")
class ServerGroupController {
 
	ServerGroupRepository serverGroupRepository
	
	@Autowired
	ServerGroupController(ServerGroupRepository serverGroupRepository){
		this.serverGroupRepository = serverGroupRepository
	}
	
	@RequestMapping(value="create")
	@ResponseBody
	def create( @ModelAttribute("serverGroup") ServerGroup serverGroup ){
		serverGroupRepository.save( serverGroup )	
		serverGroup	
	}
	
	@RequestMapping(value="list")
	@ResponseBody
	def list(){
		['rows' : serverGroupRepository.findAll() ]
	}
	
	
}
