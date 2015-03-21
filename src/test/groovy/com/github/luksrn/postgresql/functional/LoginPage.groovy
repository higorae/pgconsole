package com.github.luksrn.postgresql.functional

import geb.Page

class LoginPage extends Page {
	
	static at = { title.startsWith("PGConsole - Login Page") }
	
	static content = {
		
		loginForm { $("form", name: "login") }
		
		loginButton(to: ConsolePage) { 
			$("input", type: "submit", value: "Sign in") 
		}		
	}
}