package com.github.luksrn.postgresql.serverinfo


class Schema {
	String name
	
	String owner
	
	String comment
	
	Database database

	List<Table> tables = []
	
	List<View> views = []
	

}
