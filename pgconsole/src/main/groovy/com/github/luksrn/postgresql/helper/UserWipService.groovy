package com.github.luksrn.postgresql.helper

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.dao.UserWipRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.User

@Component
@Scope("prototype")
class UserWipService {

	UserWipRepository userWipRepository
	
	@Autowired
	UserWipService(UserWipRepository userWipRepository){		
		this.userWipRepository = userWipRepository
	}
	
	def getUserWip( Connection connection ,Principal principal ) {
		def user = new User( username: principal.getName() )
		
		userWipRepository.findByUserAndConnection( user, connection )
	}
}
