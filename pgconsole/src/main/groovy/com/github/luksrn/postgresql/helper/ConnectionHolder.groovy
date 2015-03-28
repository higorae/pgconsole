package com.github.luksrn.postgresql.helper

import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.stereotype.Component

@Component
@Scope(value="session", proxyMode=ScopedProxyMode.TARGET_CLASS)
class ConnectionHolder {

	def currentConnection
	
}
