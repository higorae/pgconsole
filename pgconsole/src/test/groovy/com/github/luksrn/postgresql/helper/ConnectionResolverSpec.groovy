package com.github.luksrn.postgresql.helper

import org.apache.tomcat.jdbc.pool.DataSource

import spock.lang.Specification

import com.github.luksrn.postgresql.domain.Connection

class ConnectionResolverSpec extends Specification {
	
	def "test if null connections return null"(){
		when:
			def cm = new ConnectionResolver().getSqlConnection(null)
		then:
			cm == null
	}
	
	def "test if returns one new connection from an empty cache and returns it"(){
		def dsBuilder = Mock(DataSourceBuilder)		
		
		given:
			def con = buildConnection()
		when:
			def cm = new ConnectionResolver(builder:dsBuilder)
			def sql = cm.getSqlConnection(con)
		then:
			sql != null
			cm.cachedConnections.size() == 1
			1 * dsBuilder.build(_) >> new DataSource()
	}
	
	
	def "test if returns one existing connection from cache and returns it"(){
		def dsBuilder = Mock(DataSourceBuilder)
		def cm = new ConnectionResolver(builder:dsBuilder)
		
		given:
			def con = buildConnection()
		when: 
			cm.getSqlConnection(con)		
			def sql = cm.getSqlConnection(con)
		then:
			sql != null
			cm.cachedConnections.size() == 1
			1 * dsBuilder.build(_) >> new DataSource()
	}
	
	def "Tests if the same settings for the connection cache is used"(){
		def dsBuilder = Mock(DataSourceBuilder)
		def cm = new ConnectionResolver(builder:dsBuilder)
		
		given:
			def con1 = buildConnection(1)
			def con2 = buildConnection(2)
		when: 		 
			def sql1 = cm.getSqlConnection(con1)
			def sql2 = cm.getSqlConnection(con2)
		then:
			sql1 != null
			sql2 != null
			cm.cachedConnections.size() == 1
			1 * dsBuilder.build(_) >> new DataSource()
	}
	
	def "Tests whether different connection settings for the cache is used"(){
		def dsBuilder = Mock(DataSourceBuilder)
		def cm = new ConnectionResolver(builder:dsBuilder)
		
		given:
			def con1 = buildConnection(1, "localhost", "5432")
			def con2 = buildConnection(2, "localhost", "5432")
			def con3 = buildConnection(3, "remotehost", "5432")
		when:			 
			def sql1 = cm.getSqlConnection(con1)
			def sql2 = cm.getSqlConnection(con2)
			def sql3 = cm.getSqlConnection(con3)
		then:
			sql1 != null
			sql2 != null
			sql3 != null
			cm.cachedConnections.size() == 2
			2 * dsBuilder.build(_) >> new DataSource()
	}
	
	
	private buildConnection( id = 1 , host = "localhost ", port = '5432'){
		new Connection( 'id': id, 'host': host , 'port': port, username: 'postgres', password: 'postgres', label: 'localhost', database: 'public')		
	}
}
