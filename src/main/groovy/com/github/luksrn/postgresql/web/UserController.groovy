package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.luksrn.postgresql.dao.UserRepository
import com.github.luksrn.postgresql.domain.User

@Controller
@RequestMapping(value="user")
class UserController {

	UserRepository userRepository
	
	@Autowired
	UserController(UserRepository userRepository){
		this.userRepository = userRepository
	}
	
	@RequestMapping(value="create")
	@ResponseBody
	def create( @ModelAttribute("user") User user ){
		userRepository.save( user )
		user
	}
	
	@RequestMapping(value="list")
	@ResponseBody
	def list(){
		[ 'rows' : userRepository.findAll() ]
	}
}
