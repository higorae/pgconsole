package com.github.luksrn.postgresql.helper

import org.hibernate.engine.jdbc.internal.BasicFormatterImpl
import org.hibernate.engine.jdbc.internal.DDLFormatterImpl
import org.springframework.stereotype.Component

import com.github.luksrn.postgresql.domain.SqlStatement

@Component
class SqlFormatter {
	
	def dmlFormatter = new BasicFormatterImpl()
	
	def ddlFormatter = new DDLFormatterImpl()
	
	def formatter( SqlStatement query ){
		def queries = [ query ]
		formatter( queries )
	}
	
	def formatter( List<SqlStatement> queries ){
		def codeSqlFormatted = ""
		for ( query in queries ){
			if ( query.isDDL() ){
				codeSqlFormatted += ddlFormatter.format(query.statement) + ";\n"
			} else if ( query.isDML() || query.isSelect() ){
				codeSqlFormatted += dmlFormatter.format(query.statement) + ";\n"
			} else {
				codeSqlFormatted +=  query.statement + ";\n"
			}
			 
		}
		codeSqlFormatted
	}
	
}
