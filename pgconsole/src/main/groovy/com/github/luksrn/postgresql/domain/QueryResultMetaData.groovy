package com.github.luksrn.postgresql.domain


class QueryResultMetaData {
	
	SqlStatement query;
		
	def result = []
		
	Date startTime
	
	Date endTime
	
	Boolean executedWithSuccess
	
	String message
	
	Integer numberOfRowsAffected
	
	Boolean appendLimit = false
	
	String getStatement(){
		if ( query?.isSelect() ){
			return query?.statementWithLimit
		}
		query?.statement
	}
	
	Integer getNumberOfRows(){
		numberOfRowsAffected
	}
	 
	Integer getNumberOfCols(){
		getColumnNames().size()
	}
	
	List getColumnNames(){
		if ( !result){
			[]
		}
		def firstRow = result[0]
		firstRow.collect {
			it.key
		}
	}	 
	
	List getRows(){
		result
	}
	 
	Long getExecutionTime(){
		endTime?.minus(startTime)
	}
	
	String toString(){
		getStatement() + " - " + getExecutionTime();
	}
}
