package com.github.luksrn.postgresql.serverinfo

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver
import com.github.luksrn.postgresql.helper.SqlLookup

/**
 * TODO http://www.postgresql.org/docs/9.3/static/sql-createview.html
 *
 */
class ViewObjectDescriptionSpec extends Specification {

	ViewObjectDescription instance;
		
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	Sql sql 
	
	def setup(){
		sql = Mock()
		currentConnectionResolver = Mock()
		sqlLookup = Mock()
		instance = new ViewObjectDescription(currentConnectionResolver, sqlLookup)
		
	}
	
	def "Test if get correct description of a view"(){
		 
		when:
			def result = instance.getDescription('public','vw_test')
		
		then:
			1 * currentConnectionResolver.getCurrentSqlConnection() >> sql
			1 * sqlLookup.lookup('tree-explorer/view-description.sql') >> "SQL"
			1 * sql.firstRow ( [ param_schema: 'public', param_view: 'vw_test' ] , "SQL" ) >> row
			result == view
		where:
			view	|	row
			VIEW_A	|	ROW_A
			VIEW_B	|	ROW_B
	}
	
	@Shared
	def VIEW_A = """-- View: vw_test

-- DROP VIEW vw_test;

CREATE OR REPLACE VIEW vw_test AS 
SELECT teste.nome FROM teste;

ALTER TABLE vw_test
  OWNER TO lucas;
COMMENT ON VIEW vw_test
  IS 'teste';
"""
	@Shared
	def ROW_A = [ relname: 'vw_test', 
						nspname: 'public', 
						relowner: 'lucas', 
						vwdefinition: 'SELECT teste.nome FROM teste;', 
						relcomment: 'teste' ]
	
	
	@Shared
	def VIEW_B = """-- View: vw_test

-- DROP VIEW vw_test;

CREATE OR REPLACE VIEW vw_test AS 
SELECT teste.nome FROM teste;

ALTER TABLE vw_test
  OWNER TO lucas;"""
	@Shared
	def ROW_B = [ relname: 'vw_test',
						nspname: 'public',
						relowner: 'lucas',
						vwdefinition: 'SELECT teste.nome FROM teste;',
						relcomment: null ]
}
 