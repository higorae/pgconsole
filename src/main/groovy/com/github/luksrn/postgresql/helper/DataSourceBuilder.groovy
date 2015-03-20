package com.github.luksrn.postgresql.helper

import org.apache.tomcat.jdbc.pool.DataSource
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.domain.Connection

@Component
class DataSourceBuilder {
	
	def build( Connection con ){
		def newDs = new DataSource()
		
		newDs.driverClassName = "org.postgresql.Driver"
		newDs.url = con.toString()
		newDs.username = con.username
		newDs.password = con.password
		newDs.maxActive = 20
		newDs.maxIdle = 10
		newDs.minIdle = 5
		newDs.minEvictableIdleTimeMillis = 60000
		newDs.initialSize = 5
		newDs.timeBetweenEvictionRunsMillis = 60000
		newDs.maxWait = 10000
		newDs.maxAge = 1800 * 1000
		newDs.fairQueue = false
		newDs.name = con.description
		newDs.jdbcInterceptors="org.apache.tomcat.jdbc.pool.interceptor.ConnectionState"
		
		newDs
	}
}
