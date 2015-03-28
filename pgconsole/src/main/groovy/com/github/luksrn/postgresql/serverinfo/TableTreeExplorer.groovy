package com.github.luksrn.postgresql.serverinfo

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.helper.CurrentConnectionResolver
import com.github.luksrn.postgresql.helper.SqlLookup

@Component
class TableTreeExplorer {
	
	CurrentConnectionResolver currentConnectionResolver
	
	SqlLookup sqlLookup
	
	@Autowired
	public TableTreeExplorer(CurrentConnectionResolver currentConnectionResolver,
								SqlLookup sqlLookup) {
		this.currentConnectionResolver = currentConnectionResolver;
		this.sqlLookup = sqlLookup
	}
								
	def getTreeViewExplorer(String schema, String table){
		
		def sql = currentConnectionResolver.getCurrentSqlConnection()
		
		def columns = []
		sql.rows ( sqlLookup.lookup('tree-explorer/map-columns.sql') , [ param_schema: schema , param_table: table]  ).each { Map row ->
			columns << new Column(  number : row.attnum , name : row.attname, datatype: row.type , atttypmod : row.atttypmod , nullable : row.attnotnull ,
				atthasdef : row.atthasdef , columnDefault : row.adsrc , attstattarget : row.attstattarget , attstorage : row.attstorage ,  typstorage : row.typstorage ,
				attisserial: row.attisserial ,  comment: row.comment )	 
		} 
		
		def columnsTable = []
		for ( column in columns ){
			String titleColumn = "${column.name} - <i>${column.datatype}</i>"
			def c = [ title: titleColumn, key: column.name , icon: '/img/assets/columns.png' ]
			 
			columnsTable << c
		} 
		columnsTable
		
	}
}
