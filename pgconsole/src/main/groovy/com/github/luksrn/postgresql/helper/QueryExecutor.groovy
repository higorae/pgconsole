package com.github.luksrn.postgresql.helper

import groovy.sql.Sql

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.QueryResultMetaData;
import com.github.luksrn.postgresql.domain.SqlStatement
import com.github.luksrn.postgresql.exceptions.DatabaseConnectionException

@Component
class QueryExecutor {
	
	@Autowired
	SqlSplitter splitter
	
	@Autowired
	ConnectionResolver conResolver;
	
	
	List<QueryResultMetaData> execute( Connection con, String userInput  ){
		
		List<SqlStatement> queries = splitter.split(userInput)
		List<QueryResultMetaData> executionMetaData =  []
		Sql connection = conResolver.getSqlConnection( con )
		try {
			if( connection == null ){
				throw new DatabaseConnectionException('Cant find connection. Please, choose one connection or create a new one' )
			}
			connection.withTransaction {
				for ( SqlStatement query : queries ){
					QueryResultMetaData queryResultMetaDeta = new QueryResultMetaData( query: query , startTime: new Date() , executedWithSuccess: true)
					try {
						if ( query.isSelect() ){
							connection.rows( query.statement ).each { Map row ->
								queryResultMetaDeta.result << row
							}
							queryResultMetaDeta.numberOfRowsAffected = queryResultMetaDeta.result.size
						} else 	if ( query.isInsert() ) {
							connection.executeInsert( query.statement )
							queryResultMetaDeta.numberOfRowsAffected = 1
						} else 	if ( query.isUpdate() || query.isDelete() ){
							queryResultMetaDeta.numberOfRowsAffected = connection.executeUpdate( query.statement )
						} else {
							connection.execute( query.statement )
							queryResultMetaDeta.numberOfRowsAffected = 0
						}
					} catch ( Exception e ){
						queryResultMetaDeta.executedWithSuccess = false
						queryResultMetaDeta.message = "${e.message}."
						throw e;
					} finally {
						queryResultMetaDeta.endTime = new Date()
					
						executionMetaData << queryResultMetaDeta
					}
					
				}
			}
		} catch( e ){

			if (e.class.isAssignableFrom(DatabaseConnectionException.class)) {
				executionMetaData << new QueryResultMetaData( executedWithSuccess: false,  message: e.message )
			} else {
				// handle transaction rollback
				if ( !executionMetaData.empty ){
					def errorCause = executionMetaData.last()
					for ( rmd in executionMetaData ){
						if ( rmd != errorCause && !rmd.query.isSelect() ){
							rmd.executedWithSuccess = false
							rmd.numberOfRowsAffected = 0
							rmd.result = []
							rmd.message = 'Undone due to error in SQL ' + errorCause.statement
						}
					}
				}
			}
		}
		
		executionMetaData
	}
 
}
