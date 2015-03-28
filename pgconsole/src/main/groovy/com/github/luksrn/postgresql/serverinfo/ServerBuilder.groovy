package com.github.luksrn.postgresql.serverinfo

class ServerBuilder {

	Server server;
	
	ServerBuilder using( Server server ){
		this.server = server
		this
	}
	
	ServerBuilder database(){
		
		this
	}
	
	ServerBuilder schemas(){
		
		this
	}
	
	
	
	Server build(){
		if ( server == null ){
			throw new IllegalStateException("Can not load server tree. Server is null")
		}
		
	}
}
