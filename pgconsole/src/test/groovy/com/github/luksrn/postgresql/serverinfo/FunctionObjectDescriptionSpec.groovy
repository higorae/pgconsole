package com.github.luksrn.postgresql.serverinfo

import groovy.sql.Sql
import spock.lang.Ignore;
import spock.lang.Shared
import spock.lang.Specification;
import spock.lang.Unroll;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver
import com.github.luksrn.postgresql.helper.SqlLookup

/**
 * TODO: http://www.postgresql.org/docs/9.1/static/sql-createfunction.html
 * 
 */
@Ignore
class FunctionObjectDescriptionSpec extends Specification {
	
	FunctionObjectDescription instance;
		
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	Sql sql
	
	def setup(){
		sql = Mock()
		currentConnectionResolver = Mock()
		sqlLookup = Mock()
		instance = new FunctionObjectDescription(currentConnectionResolver, sqlLookup)
		
	}
	
	@Unroll
	def "Test if get correct description of a function"(){
		 
		when:
			def result = instance.getDescription('schema','fn_sum')
		
		then:
			1 * currentConnectionResolver.getCurrentSqlConnection() >> sql
			1 * sqlLookup.lookup('tree-explorer/function-description.sql') >> "SQL"
			1 * sql.firstRow ( [ param_schema: 'schema', param_view: 'fn_sum' ] , "SQL" ) >> row
			result == function
		where:
			function	|	row
			FUNCTION_A	|	ROW_A
			FUNCTION_B	|	ROW_B
	}
	
	@Shared
	def FUNCTION_A = """-- Function: one()

-- DROP FUNCTION one();

CREATE OR REPLACE FUNCTION one()
  RETURNS integer AS
\$BODY\$
    SELECT 1 AS result;
    SELECT 2 AS result2;
\$BODY\$
  LANGUAGE sql VOLATILE
  COST 100;
ALTER FUNCTION one()
  OWNER TO lucas;
"""
	@Shared
	def ROW_A = [ prooid : '',	
					proname : '', 	
					proowner : '', 	
					proschema : '', 
					prolanguage : '', 	
					procost : '', 	
					prorows  : '',	
					proresult : '', 	
					prosrc  : '',	
					probin : '', 	
					proretset : '', 	
					proisstrict : '', 	
					provolatile : '', 	
					prosecdef : '', 
					proarguments : '', 
					proargnames : '', 	
					procomment : '', 	
					proconfig : '', 	
					proallarguments : '', 	
					proargmodes : '' ] 						

	
	@Shared
	def FUNCTION_B = """-- Function: soma(integer, integer)

-- DROP FUNCTION soma(integer, integer);

CREATE OR REPLACE FUNCTION soma(IN a integer DEFAULT 0, IN b integer DEFAULT 0, OUT c integer)
  RETURNS integer AS
'select a + b;'
  LANGUAGE sql VOLATILE
  COST 50;
ALTER FUNCTION soma(integer, integer)
  OWNER TO lucas;
COMMENT ON FUNCTION soma(integer, integer) IS 'soma dois numeros';
"""
    @Shared
	def ROW_B = [ prooid : '',	
					proname : '', 	
					proowner : '', 	
					proschema : '', 
					prolanguage : '', 	
					procost : '', 	
					prorows  : '',	
					proresult : '', 	
					prosrc  : '',	
					probin : '', 	
					proretset : '', 	
					proisstrict : '', 	
					provolatile : '', 	
					prosecdef : '', 
					proarguments : '', 
					proargnames : '', 	
					procomment : '', 	
					proconfig : '', 	
					proallarguments : '', 	
					proargmodes : '' ] 
}
