package com.github.luksrn.postgresql.helper

import groovy.sql.Sql

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.domain.Connection

@Component
@Scope("prototype")
class CurrentConnectionResolver {

	ConnectionHolder connectionHolder
	
	ConnectionResolver connectionResolver
		
	@Autowired
	public CurrentConnectionResolver(ConnectionHolder connectionHolder,
			ConnectionResolver connectionResolver) {
		super();
		this.connectionHolder = connectionHolder;
		this.connectionResolver = connectionResolver;
	}


	Sql getCurrentSqlConnection(){
		Connection currentConnection = connectionHolder.currentConnection
		
		if ( !currentConnection ){
			throw new IllegalStateException("???? ????");
		}
		
		connectionResolver.getSqlConnection( currentConnection )
	}
}
