package com.github.luksrn.postgresql.serverinfo

import org.springframework.beans.factory.annotation.Autowired;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

class ViewObjectDescription {

	
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public ViewObjectDescription(CurrentConnectionResolver currentConnectionResolver, SqlLookup sqlLookup){
		this.currentConnectionResolver = currentConnectionResolver
		this.sqlLookup = sqlLookup
	}
	
	String getDescription(String schema, String view){
		
		def sql = currentConnectionResolver.getCurrentSqlConnection();
		
		def viewRow = sql.firstRow (  [ param_schema: schema , param_view: view] , sqlLookup.lookup('tree-explorer/view-description.sql')  )
		
		getDefinition(viewRow)
	}
	 
	
	private String getDefinition(Map viewRow){	

		
		def desc = """-- View: ${viewRow.relname}

-- DROP VIEW ${viewRow.relname};

CREATE OR REPLACE VIEW ${viewRow.relname} AS 
${viewRow.vwdefinition}

ALTER TABLE ${viewRow.relname}
  OWNER TO ${viewRow.relowner};"""
	if ( viewRow.relcomment )
	desc += """\nCOMMENT ON VIEW ${viewRow.relname}
  IS '${viewRow.relcomment}';
"""
		desc
	}
	
	
}
