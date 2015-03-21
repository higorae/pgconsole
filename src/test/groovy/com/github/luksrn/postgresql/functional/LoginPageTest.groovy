package com.github.luksrn.postgresql.functional

import geb.spock.GebSpec
 
class LoginPageTest extends GebSpec {
	
	 
	def "cat user get login page?"() {
		when:
         to LoginPage
 
		then:
       	assert at(LoginPage)

    }
}