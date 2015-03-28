package com.github.luksrn.postgresql.web

import groovy.sql.Sql

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.dao.ConnectionRepository;
import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.helper.ConnectionResolver
import com.github.luksrn.postgresql.helper.SqlLookup

@Controller
class AutocompleteController {
	ConnectionResolver connectionResolver
	ConnectionRepository connectionRepository
	SqlLookup sqlLookup
	
	@Autowired
	AutocompleteController(ConnectionRepository connectionRepository,ConnectionResolver connectionResolver, SqlLookup sqlLookup){
		this.connectionResolver = connectionResolver
		this.connectionRepository = connectionRepository
		this.sqlLookup = sqlLookup	
	}
	
	@RequestMapping(value="/console/autocomplete", method=RequestMethod.GET)
	@ResponseBody
	def complete( @RequestParam("idConnection") Integer idConnection ,@RequestParam("token") String token ){
		def connection = connectionRepository.findOne(idConnection)
	
		Sql sql = connectionResolver.getSqlConnection(connection)
		String queryAutocomplete = sqlLookup.lookup("autocomplete.sql")
		
		def result = []
		sql.rows( queryAutocomplete , [ token ] ).each { Map row ->
			result <<  [ word: row.table_schema + '.' + row.table_name, freq : 1 , score: 300, flags : row.table_schema, syllables : 1 ]				
		}
		result
	}
}
