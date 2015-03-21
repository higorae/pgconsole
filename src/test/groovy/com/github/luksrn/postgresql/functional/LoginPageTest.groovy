package com.github.luksrn.postgresql.functional

import geb.spock.GebSpec
 
class LoginPageTest extends GebSpec {
	
	 
	def "cat user get login page and sing in with default admin user?"() {
		when:
        	to LoginPage
 
		then:
       		assert at(LoginPage)
		   
		 when:
		 	loginForm.with {
				 username = "admin"
				 password = "admin"
			 }

			 loginButton.click()		 
 
		then:
			assert at(ConsolePage)
			
	 
		 
    }
}