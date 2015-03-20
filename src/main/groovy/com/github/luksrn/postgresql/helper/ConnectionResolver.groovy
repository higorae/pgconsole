package com.github.luksrn.postgresql.helper

import groovy.sql.Sql

import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.domain.Connection


@Component
class ConnectionResolver {
	
	@Autowired
	DataSourceBuilder builder
	
	def cachedConnections = [:]
	
	Sql getSqlConnection(Connection settings){
		if ( !settings ){
			return null
		}
		new Sql( findDataSource(settings) )
	}
	
	void removeSqlConnecion( Connection settings ) {
		def key = getKey(settings)
		def connection = cachedConnections[ key ]
		if ( connection ){
			connection.close()
			cachedConnections[ key ] = null
		}
	}
	
	private String getKey( connection ){
		"${connection.url}_${connection.username}"
	}
	
	private DataSource findDataSource(Connection settings){
		def key = getKey(settings)
		def ds = cachedConnections[ key]
		if ( ds ){
			return ds
		}
		cachedConnections[ key ] = builder.build( settings )
		return cachedConnections[ key ]
	}
}
