package com.github.luksrn.postgresql.web

import spock.lang.Specification

import com.github.luksrn.postgresql.domain.SqlStatement
import com.github.luksrn.postgresql.helper.SqlFormatter

class SqlFormatterControllerSpec extends Specification {

	def "Formatter DML SELECT"(){
		given:
			def sql = "SELECT kind, sum(len) AS total FROM films GROUP BY kind"
			def sqlFormatted
		when: 
			def queries = new SqlStatement(statement: sql )
			sqlFormatted = new SqlFormatter().formatter( queries )
		then:
			sqlFormatted == """
    SELECT
        kind,
        sum(len) AS total 
    FROM
        films 
    GROUP BY
        kind;
""" 
	}
}