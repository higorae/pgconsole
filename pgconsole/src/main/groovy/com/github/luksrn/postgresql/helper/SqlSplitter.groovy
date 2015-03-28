package com.github.luksrn.postgresql.helper

import java.util.regex.Pattern

import org.flywaydb.core.internal.dbsupport.postgresql.PostgreSQLSqlStatementBuilder
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.domain.SqlStatement

@Component
class SqlSplitter {

	List<SqlStatement> split( String text ){
		def queries = []
		if( text == null || text?.empty ){
			return queries
		}
		
		def onlySql = removeComments(text) 
		
		def statementBuilder = new PostgreSQLSqlStatementBuilder()

		onlySql.eachLine { line ->			
			statementBuilder.addLine( line )

			if ( statementBuilder.terminated ){
				processStatement( statementBuilder, queries )				 	
				statementBuilder = new PostgreSQLSqlStatementBuilder()
			}
		}
		// allow last statement without statement delimiter ;
		if ( !statementBuilder.empty ){
			processStatement( statementBuilder, queries )
		}
 
		queries
	}
	private String removeComments( text ){
		def multiLinePattern = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL) // remove all occurance streamed comments (/*COMMENT */) from string
		def singleLine = Pattern.compile("--.*?(\n|\$)", Pattern.DOTALL) // remove all occurance --
		
		def temp = multiLinePattern.matcher(text).replaceAll("")
		singleLine.matcher(temp).replaceAll("")		
	}
	
	private processStatement( statementBuilder , queries ){
		def sqlStatement = buildSqlStatement( statementBuilder )
		if ( sqlStatement ){
			queries << sqlStatement
		}
	}
	
	private SqlStatement buildSqlStatement( statementBuilder ) {
		def parsedSql = statementBuilder.sqlStatement.sql
		def sql = parsedSql.replaceAll("^\\s+", "").replaceAll("\\s+\$", "")
		if ( sql.empty ) {
			return null
		}
		new SqlStatement(statement: sql)
	}
}
