package com.github.luksrn.postgresql.serverinfo

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

@Component
class TableObjectDescriptipon {

	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public TableObjectDescriptipon(	CurrentConnectionResolver currentConnectionResolver, SqlLookup sqlLookup){
		this.currentConnectionResolver = currentConnectionResolver
		this.sqlLookup = sqlLookup
	}
	
	String getDescription(String schema, String table){
		
		def sql = currentConnectionResolver.getCurrentSqlConnection()
		
		
		def columns = []
		sql.rows ( sqlLookup.lookup('tree-explorer/table-columns-description.sql') , [ param_schema: schema , param_table: table]  ).each { Map row ->
			 
			columns << new Column(   number : row.attnum , name : row.attname, datatype: row.type , atttypmod : row.atttypmod , nullable : row.attnotnull ,
				atthasdef : row.atthasdef , columnDefault : row.adsrc , attstattarget : row.attstattarget , attstorage : row.attstorage ,  typstorage : row.typstorage ,
				attisserial: row.attisserial ,  comment: row.comment )
		}
		
		def constraints = [] 
		sql.rows (  sqlLookup.lookup('tree-explorer/table-constraints-description.sql') , [ param_schema: schema , param_table: table]  ).each { Map row ->
			constraints << new Constraint(   name : row.conname , description : row.consrc ,  contype : row.contype , indisclustered : row.indisclustered )
		}
		
		getDefinition( schema, table, columns, constraints)
	}
	
	private String getDefinition(schema, table, columns , constraints){	
		def definition = """ -- Table definition: ${schema}.${table}
	
-- DROP TABLE ${schema}.${table};
	
CREATE TABLE  ${schema}.${table} (\n""" 
		
		columns.eachWithIndex() { obj, i ->
			definition += "\t${obj.name} "
			
			if ( obj.attisserial && ( obj.datatype == 'integer' ||  obj.datatype == 'bigint' )){
				if ( obj.datatype == 'integer' ){
					definition += " SERIAL "
				} else {
					definition += " BIGSERIAL "
				}
			} else {
				definition += formatType( obj.datatype, obj.atttypmod )
			}
			
			if ( ! obj.nullable ){
				definition += ' NOT NULL'
			}
			
			if ( obj.columnDefault ){
				definition += " DEFAULT ${obj.columnDefault}"
			}
			if ( i < columns.size() ){
				definition += ","
			}
			if ( obj.comment ){
				definition += "-- ${obj.comment}"
			}
			definition += '\n'
		}
		
		constraints.eachWithIndex { obj, i ->
			definition += " CONSTRAINT ${obj.name} "
			if ( obj.description != null ){
				definition += " ${obj.description} "
			} else {
				switch ( obj.contype ){
					case 'p':
						definition += " PRIMARY KEY ()"
						break;
					case 'u':
						definition += " UNIQUE ()"
						break;
					
				}
			}
			if ( i < constraints.size() -1 ){
				definition += ","
			}
			definition += '\n'
		}
		
		definition += ");\n"
		
		
		// alter comments
		columns.eachWithIndex() { obj, i ->
			 if ( obj.comment ){
				definition += " COMMENT ON COLUMN ${schema}.${table}.${obj.name} IS '${obj.comment}';\n"
			}
		}
				 
		definition
	}
	
	private String formatType ( String datatype , def atttypmod ) {
		// This is a specific constant in the 7.0 source
		def varhdrsz = 4;
		
		def formatType = ''
		def isArray = false
		if ( datatype.startsWith("_") ){
			isArray = true
		}
		
		if ( datatype == 'bpchar' ){
			def len = atttypmod - varhdrsz
			formatType += 'character'
			if ( len > 1 ){
				formatType += '(${len})'
			}
		} else if ( datatype == 'varchar' ){
			formatType = 'character varying'
			if ( atttypmod != -1 ){
				formatType += "(${ atttypmod - varhdrsz })"
			}
		} else if ( datatype == 'numeric' ){
			formatType += 'numeric'
			if ( atttypmod != -1 ){
				def temptypemod = atttypmod - varhdrsz
				def precision = (temptypemod >> 16) & 0xffff
				def scale = temptypemod &  0xffff
				formatType += "( ${precision}, ${scale})"
			}
		} else {
			formatType += datatype
		}
		if ( isArray ){
			formatType += '[]'
		}
		formatType
	}
}
