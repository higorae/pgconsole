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
	
	boolean containsLimit(){
		statement?.contains("LIMIT") || statement?.contains("limit")
	}
	
	String getStatementWithLimit(){
		if ( !isSelect() ){
			throw new IllegalStateException("Statement is not a SELECT. Statement is: ${statement}")
		}
		if ( containsLimit() ){
			return statement
		}
		"${statement} LIMIT 100"
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
