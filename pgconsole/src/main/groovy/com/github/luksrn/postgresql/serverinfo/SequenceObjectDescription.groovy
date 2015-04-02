package com.github.luksrn.postgresql.serverinfo

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

@Component
class SequenceObjectDescription {
	
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public SequenceObjectDescription(CurrentConnectionResolver currentConnectionResolver, SqlLookup sqlLookup){
		this.currentConnectionResolver = currentConnectionResolver
		this.sqlLookup = sqlLookup
	}
	
	String getDescription(String schema, String sequence){
		
		def sql = currentConnectionResolver.getCurrentSqlConnection();
		
		def viewRow = sql.firstRow (  [ param_schema: schema , param_sequence: sequence] , sqlLookup.lookup('tree-explorer/sequence-description.sql')  )
		
		getDefinition(viewRow)
	}
	 
	
	private String getDefinition(Map viewRow){
		def definition = """-- Sequence: ${viewRow.sequence_name}

-- DROP SEQUENCE ${viewRow.sequence_name};

CREATE SEQUENCE ${viewRow.sequence_name}
  INCREMENT ${viewRow.increment_by}
  MINVALUE ${viewRow.min_value}
  MAXVALUE ${viewRow.max_value}
  START ${viewRow.start_value}
  CACHE ${viewRow.cache_value};
ALTER TABLE ${viewRow.sequence_name}
  OWNER TO ${viewRow.seqowner};"""
		if( viewRow.seqcomment ){
			definition = definition + """
COMMENT ON SEQUENCE ${viewRow.sequence_name}
  IS '${viewRow.seqcomment}';
"""
		}
		definition
	}

	
}
