package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.serverinfo.DatabaseTreeExplorer
import com.github.luksrn.postgresql.serverinfo.SchemaTreeExplorer
import com.github.luksrn.postgresql.serverinfo.ServerTreeExplorer
import com.github.luksrn.postgresql.serverinfo.TableTreeExplorer

@Controller
class ServerTreeExplorerController {
	 
	ServerTreeExplorer serverTreeExplorer

	DatabaseTreeExplorer databaseTreeExplorer
	
	SchemaTreeExplorer schemaTreeExplorer
	
	TableTreeExplorer tableTreeExplorer
	
	@Autowired
	public ServerTreeExplorerController(ServerTreeExplorer serverTreeExplorer,
					DatabaseTreeExplorer databaseTreeExplorer,
					SchemaTreeExplorer schemaTreeExplorer,
					TableTreeExplorer tableTreeExplorer) {
		this.serverTreeExplorer = serverTreeExplorer;
		this.schemaTreeExplorer = schemaTreeExplorer;
		this.databaseTreeExplorer = databaseTreeExplorer
		this.tableTreeExplorer = tableTreeExplorer
	}

	@RequestMapping(value="/serverExplorer")
	@ResponseBody
	def load(){
		serverTreeExplorer.treeViewExplorer
	}
	
	@RequestMapping(value="/serverExplorer/lazyLoading")
	@ResponseBody
	def lazyLoading(@RequestParam("type") String type, 
					@RequestParam(value="database",required=false) String database ,
					@RequestParam(value="schema",required=false) String schema,
					@RequestParam(value="table",required=false) String table,
					@RequestParam(value="view",required=false) String view ){
		switch ( type ){
			case 'database':
				return databaseTreeExplorer.getTreeViewExplorer()
			case 'schema':
				return schemaTreeExplorer.getTreeViewExplorer( schema )
			case 'table':
				return tableTreeExplorer.getTreeViewExplorer( schema, table )
		}
		[]
	}					
}
