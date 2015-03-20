package com.github.luksrn.postgresql.serverinfo

import com.github.luksrn.postgresql.domain.Connection


/**
 * Representa uma instância de uma conexão com o banco
 * Via: 
 * @author lucas
 *
 */
class Server {
	
	String name
	
	Connection connection

	Database database
	
	public Server(Connection connection ){
		this.connection = connection
		name = connection?.label ? connection.label : "Server Connection " + new Date().time
		database = new Database( name: connection.database , server: this )
	}
}
