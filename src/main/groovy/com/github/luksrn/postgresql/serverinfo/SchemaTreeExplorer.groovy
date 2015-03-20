package com.github.luksrn.postgresql.serverinfo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver
import com.github.luksrn.postgresql.helper.SqlLookup;

@Component
class SchemaTreeExplorer {
		
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public TableTreeExplorer(CurrentConnectionResolver currentConnectionResolver,
								SqlLookup sqlLookup) {
		this.currentConnectionResolver = currentConnectionResolver;
		this.sqlLookup = sqlLookup
	}
								
	def getTreeViewExplorer(String schema){
		
		def sql = currentConnectionResolver.getCurrentSqlConnection()
		
		def tables = []
		sql.rows( sqlLookup.lookup('tree-explorer/map-table.sql') , [ param_schema: schema ] ).each { Map row ->
			tables << new Table( name: row.relname , owner: row.relowner , comment: row.relcomment , tablespace: row.tablespace   )
		}
		 
		tables
		
		def schemaChildrens = []
		
		def tablesRoot = [ title : (String) "\tTables (${tables.size})" , key: 'tables_' + schema , icon: '/img/table_multiple.png']
		 
		def tableMap = []
		for ( table in tables ){
			def t = [ title: (String) "\t${table.name}" ,
				key : table.name ,
				icon: '/img/table.png' ,
				lazy: true,
				data : [ type: 'table', schema: schema , table: table.name  ]]
			
			tableMap << t
			
		}
		tablesRoot['children'] = tableMap
		
		schemaChildrens << tablesRoot
		
		schemaChildrens
	}

}
