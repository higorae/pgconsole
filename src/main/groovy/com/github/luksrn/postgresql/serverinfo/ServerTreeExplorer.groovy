package com.github.luksrn.postgresql.serverinfo

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.luksrn.postgresql.domain.Connection;
import com.github.luksrn.postgresql.helper.CurrentConnectionResolver;
import com.github.luksrn.postgresql.helper.SqlLookup;

/**
 * Shows database root.
 * 
 */
@Component
class ServerTreeExplorer {
	
	CurrentConnectionResolver currentConnectionResolver
	
	@Autowired
	public ServerTreeExplorer(CurrentConnectionResolver currentConnectionResolver) {
		this.currentConnectionResolver = currentConnectionResolver;
	}

	def getTreeViewExplorer(){
		def connection = currentConnectionResolver.connectionHolder.currentConnection
		
		def sql = currentConnectionResolver.getCurrentSqlConnection()
		
		def root =[]
		
		if ( !sql ){
			return root
		}
	
		Server server = new Server( connection )
		
		def rootServer = [ title : connection.url ,
				key: connection.id ,
				icon: '/img/computer_database_small.png' ,
				children: [] ,
				lazy: true,
				data : [ type: 'database', database: server.connection.database , connectionId: server.connection.id  ] ]
		def rootServerChildrens = []
	
		root << rootServer
		
		root
	}	 
}
