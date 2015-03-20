package com.github.luksrn.postgresql.serverinfo


class Table {
	
	String name
	
	String owner
	
	String comment
	
	String tablespace
	
	Schema schema
	
	List<Column> columns = []
	
	List<Constraint> constraints = []
}
