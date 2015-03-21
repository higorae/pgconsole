package com.github.luksrn.postgresql.functional

import geb.Page

class LoginPage extends Page {
	static at = { title.startsWith("PGConsole - Login Page") }
}