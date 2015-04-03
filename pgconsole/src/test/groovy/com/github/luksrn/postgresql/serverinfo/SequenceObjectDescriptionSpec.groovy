package com.github.luksrn.postgresql.serverinfo

import groovy.sql.Sql;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

import spock.lang.Shared;
import spock.lang.Specification;

class SequenceObjectDescriptionSpec extends Specification {
	
	SequenceObjectDescription instance;
		
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	Sql sql
	
	def setup(){
		sql = Mock()
		currentConnectionResolver = Mock()
		sqlLookup = Mock()
		instance = new SequenceObjectDescription(currentConnectionResolver, sqlLookup)
		
	}
	
	def "Test if get correct description of a sequence"(){
		 
		when:
			def result = instance.getDescription('public','test_seq')
		
		then:
			1 * currentConnectionResolver.getCurrentSqlConnection() >> sql
			1 * sqlLookup.lookup('tree-explorer/sequence-description.sql') >> "SQL"
			1 * sql.firstRow ( [ param_schema: 'public', param_sequence: 'test_seq' ] , "SQL" ) >> row
			result == view
		where:
			view	|	row
			VIEW_A	|	ROW_A
			VIEW_B	|	ROW_B
	}

@Shared
def VIEW_A = """-- Sequence: public.sequencia_teste

-- DROP SEQUENCE public.sequencia_teste;

CREATE SEQUENCE public.sequencia_teste
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.sequencia_teste
  OWNER TO lucas;
COMMENT ON SEQUENCE public.sequencia_teste
  IS 'teste';
"""
@Shared
def ROW_A = [ seqname: 'sequencia_teste',
			sequence_name : 'sequencia_teste_seq',
			last_value: 1, 
			start_value: 1,
			increment_by: 1, 
			max_value: 9223372036854775807, 
			min_value: 1, 
			cache_value: 1, 
			log_cnt: 0, 
			is_cycled: false, 
			is_called: false, 
			seqcomment: 'teste', 
			seqowner: 'lucas',
			nspname: 'public' ]


@Shared
def VIEW_B = """-- Sequence: public.sequencia_teste

-- DROP SEQUENCE public.sequencia_teste;

CREATE SEQUENCE public.sequencia_teste
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.sequencia_teste
  OWNER TO lucas;"""
@Shared
def ROW_B = [ seqname: 'sequencia_teste',
			sequence_name : 'sequencia_teste_seq',
			last_value: 1,
			start_value: 1,
			increment_by: 1,
			max_value: 9223372036854775807,
			min_value: 1,
			cache_value: 1,
			log_cnt: 0,
			is_cycled: false,
			is_called: false,
			seqcomment: null,
			seqowner: 'lucas',
			nspname: 'public' ]
	
	 

}
