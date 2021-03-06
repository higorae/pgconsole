package com.github.luksrn.postgresql.domain

class SqlStatement {
	String statement
		
	boolean isDML(){
		return isInsert() || isUpdate() || isDelete()
	}
	// TODO Trabalhar com possibilidade de "Create      table"
	boolean isDDL(){
		return checkIfStartWith( "create table" )  || checkIfStartWith( "alter table" ) || checkIfStartWith(  "comment on" )
	}
	
	boolean isInsert(){
		checkIfStartWith("insert")
	}
	
	boolean isUpdate(){
		checkIfStartWith("update")
	}
	
	boolean isDelete(){
		checkIfStartWith("delete")
	}
	
	boolean isSelect(){
		checkIfStartWith("select")
	}
	
	private boolean checkIfStartWith( String text ){
		getStatementNormalized().startsWith( text )
	}
	private String getStatementNormalized(){
		statement?.toLowerCase().replaceAll("^\\s+", "")
	}
	 
	String toString(){
		statement
	}
}
