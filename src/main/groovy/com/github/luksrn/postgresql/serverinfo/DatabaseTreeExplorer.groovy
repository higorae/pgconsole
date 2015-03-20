package com.github.luksrn.postgresql.serverinfo

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

@Component
class DatabaseTreeExplorer {
	
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public DatabaseTreeExplorer(CurrentConnectionResolver currentConnectionResolver,
								SqlLookup sqlLookup) {
		this.currentConnectionResolver = currentConnectionResolver;
		this.sqlLookup = sqlLookup
	}
	
	def getTreeViewExplorer(String database){
		
		def sql = currentConnectionResolver.getCurrentSqlConnection()
		
		def schemas = []
		
		sql.rows(  sqlLookup.lookup("tree-explorer/map-schema.sql") ).each { Map row ->
			schemas << new Schema( name: row.nspname , owner : row.nspowner, comment: row.nspcomment  )
		}
			 
		String schemaTitle = "\tSchemas (${schemas.size})"
		def schemasRoot = [ title: schemaTitle , key: "schema_key", icon: '/img/schemas.png', children: [] ]
		
		
		for ( schema in schemas ) {
			String schemaName = "\t${schema.name}"
			def s = [ title : schemaName, key: schema.name , icon: '/img/schema.png' , lazy: true, data : [ type: 'schema', schema: schema.name  ] ]
			
			schemasRoot['children'] << s
			  
		}
		def root = []
		root << schemasRoot
		
		root		
	}
}
