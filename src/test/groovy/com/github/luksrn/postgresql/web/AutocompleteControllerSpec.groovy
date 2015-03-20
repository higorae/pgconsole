package com.github.luksrn.postgresql.web

import groovy.sql.Sql
import spock.lang.Specification

import com.github.luksrn.postgresql.dao.ConnectionRepository;
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.helper.ConnectionResolver
import com.github.luksrn.postgresql.helper.SqlLookup;

class AutocompleteControllerSpec extends Specification {

	AutocompleteController instance
	ConnectionResolver connectionResolver
	SqlLookup sqlLookup
	ConnectionRepository connectionRepository
	Sql sql
	
	def setup(){
		connectionResolver = Mock()
		sqlLookup = Mock()
		sql = Mock()
		connectionRepository = Mock()
		instance = new AutocompleteController(connectionRepository, connectionResolver, sqlLookup)
	}
	
	def "Auto complete schema/database information"(){
		def connection = new Connection()
		def sqlAutocomplete = "SOME SELECT"
		def params = [ 'table']
		when:
			def result = instance.complete( 1 , "table" )	
			
		then:			
			1 * connectionRepository.findOne(1) >> connection
			1 * connectionResolver.getSqlConnection( connection ) >> sql
			1 * sqlLookup.lookup( "autocomplete.sql" ) >> sqlAutocomplete
			1 * sql.rows( sqlAutocomplete , params ) >> [ [ table_schema : 'schema' , table_name : 'table' ] ]
			result.size() == 1
			result[0].word == 'schema.table'
			result[0].freq == 1
			result[0].score == 300
			result[0].flags == 'schema'
			result[0].syllables == 1
					
	}
	
}
