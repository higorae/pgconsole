package com.github.luksrn.postgresql.helper

import java.security.Principal

import spock.lang.Specification

import com.github.luksrn.postgresql.dao.UserWipRepository
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.domain.UserWip

class UserWipServiceSpec extends Specification {

	UserWipService instance
	UserWipRepository userWipRepository
	Principal principal
	
	def setup(){
		principal = Mock()
		userWipRepository = Mock()
		instance = new UserWipService(userWipRepository)	
	}
	
	def "Recover work in progress for logged user"(){
		def wip
		when:
			wip = instance.getUserWip( new Connection (), principal )
		then:
			wip.code == "SELECT 1"
			1 * principal.getName() >> "user"
			1 * userWipRepository.findByUserAndConnection( _ as User , _ as Connection ) >> new UserWip(code: 'SELECT 1')		
	}
}
