package com.github.luksrn.postgresql.serverinfo

import java.util.Map;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

@Component
class SequenceObjectDescription {
	
	def log = LoggerFactory.getLogger(SequenceObjectDescription)
	
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public SequenceObjectDescription(CurrentConnectionResolver currentConnectionResolver, SqlLookup sqlLookup){
		this.currentConnectionResolver = currentConnectionResolver
		this.sqlLookup = sqlLookup
	}
	
	String getDescription(String schema, String sequence){
		
		log.info "Get Sequence description: Schema name: ${schema} Sequence name: ${sequence}"
		
		def sql = currentConnectionResolver.getCurrentSqlConnection();
		
		def viewRow = sql.firstRow (  [ param_schema: schema , param_sequence: sequence] , sqlLookup.lookup('tree-explorer/sequence-description.sql')  )
		
		getDefinition(viewRow)
	}
	 
	
	private String getDefinition(Map viewRow){
		def definition = """-- Sequence: ${viewRow.nspname}.${viewRow.seqname}

-- DROP SEQUENCE ${viewRow.nspname}.${viewRow.seqname};

CREATE SEQUENCE ${viewRow.nspname}.${viewRow.seqname}
  INCREMENT ${viewRow.increment_by}
  MINVALUE ${viewRow.min_value}
  MAXVALUE ${viewRow.max_value}
  START ${viewRow.start_value}
  CACHE ${viewRow.cache_value};
ALTER TABLE ${viewRow.nspname}.${viewRow.seqname}
  OWNER TO ${viewRow.seqowner};"""
		if( viewRow.seqcomment ){
			definition = definition + """
COMMENT ON SEQUENCE ${viewRow.nspname}.${viewRow.seqname}
  IS '${viewRow.seqcomment}';
"""
		}
		definition
	}

	
}
