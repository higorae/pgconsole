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
		
		def views = []
		
		sql.rows( sqlLookup.lookup("tree-explorer/map-views.sql") , [ param_schema : schema ]).each { Map row ->
			views << new View(name: row.table_name )
		}
		
		def viewsRoot = [ title : (String) "\tViews (${views.size})" , key: 'views_' + schema , icon: '/img/view.png']
		def viewMap = []
		for ( view in views ){
			def v = [ title: (String) "\t${view.name}" ,
				key : view.name ,
				icon: '/img/view.png' ,
				lazy: true,
				data : [ type: 'view', schema: schema , view: view.name  ]]
			
			viewMap << v			
		}
		 
		viewsRoot['children'] = viewMap
		
		// ----
		
		def sequences = []
		
		sql.rows( sqlLookup.lookup("tree-explorer/map-sequence.sql") , [ param_schema : schema ]).each { Map row ->
			sequences << new Sequence(name: row.seqname )
		}
		
		def sequencesRoot = [ title : (String) "\tSequences (${sequences.size})" , key: 'views_' + schema , icon: '/img/sequence.png']
		def sequencesMap = []
		for ( sequence in sequences ){
			def s = [ title: (String) "\t${sequence.name}" ,
				key : sequence.name ,
				icon: '/img/sequence.png' ,
				lazy: true,
				data : [ type: 'sequence', schema: schema , sequence: sequence.name  ]]
			
			sequencesMap << s
		}
		 
		sequencesRoot['children'] = sequencesMap
		
		
		
		// ---
		
		schemaChildrens << tablesRoot
		schemaChildrens << viewsRoot
		schemaChildrens << sequencesRoot
		
		schemaChildrens
	}

}
