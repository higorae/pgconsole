package com.github.luksrn.postgresql.helper

import spock.lang.IgnoreRest;
import spock.lang.Specification;

class SqlSplitterSpec extends Specification {
	
	void "test conversion of null text"() {
		given:
			def result
		when:
			 result = new SqlSplitter().split(null)
		
		then:
			result.empty
	}
	
	void "test conversion of blank text"() {
		given:
			def query = ""
			def result
		when:
			 result = new SqlSplitter().split(query)
		
		then:
			result.empty
	}
	
	void "test conversion of one query"() {
		given:
			def query = "select * from foo;"
			def result
		when:
			 result = new SqlSplitter().split(query)
		
		then:
			result.size == 1
			result[0].statement == "select * from foo"
	}

	void "test conversion of two queries in one line"() {
		given:
			def query = "select * from foo; select 1;"
			def result
		when:
			 result = new SqlSplitter().split(query)
		
		then:
			result.size == 1
			result[0].statement == "select * from foo; select 1"
	}
	
	void "test conversion of one query without ;"() {
		given:
			def query = "select * from foo;"
			def result
		when:
			 result = new SqlSplitter().split(query)
		
		then:
			result.size == 1
			result[0].statement == "select * from foo"
	}
	
	
	void "test conversion of multiple queries, separeted by ;"() {
		given:
			def query = """
			SELECT * FROM foo;

			SELECT * FROM foo f
				INNER JOIN  bar b on f.id = b.id
			WHERE x = y;

			UPDATE scheme.table SET x = 3 WHERE 3
			"""
			def result
		when:
			 result = new SqlSplitter().split(query)
		
		then:
			result.size == 3
			result[0].statement == "SELECT * FROM foo"
			result[1].statement == """SELECT * FROM foo f
				INNER JOIN  bar b on f.id = b.id
			WHERE x = y"""
			result[2].statement == "UPDATE scheme.table SET x = 3 WHERE 3"
				
	}

	void "test conversion with comments"(){
		given:
			def query = """
		INSERT IGNORE INTO users (firstname,lastname) VALUES ('x','y');
		/*
			INSERT IGNORE INTO users (firstname,lastname) VALUES ('a','b');
			*/
		"""
			def result
		when:
			result = new SqlSplitter().split(query)
		then:
			result.size == 1
			result[0].statement == "INSERT IGNORE INTO users (firstname,lastname) VALUES ('x','y')"
	}

	void "test conversion with comments side "(){
		given:
			def query = """
		-- INSERT IGNORE INTO users (firstname,lastname) VALUES ('t','t');

		INSERT IGNORE INTO users (firstname,lastname) VALUES ('x','y'); -- comment
		 
		INSERT IGNORE INTO users (firstname,lastname) VALUES ('a','b'); /* comment */"""
			def result
		when:
			result = new SqlSplitter().split(query)
		then:
			result.size == 2
			result[0].statement == "INSERT IGNORE INTO users (firstname,lastname) VALUES ('x','y')"
			result[1].statement == "INSERT IGNORE INTO users (firstname,lastname) VALUES ('a','b')"
	}
	
	
	void "test conversion of create functions using language SQL or PL/SQL with \$\$ delimiter"(){
		given:
			def query = """CREATE FUNCTION one() RETURNS integer AS \$\$
				SELECT 1 AS result;
			\$\$ LANGUAGE SQL;
			 
			
			SELECT one();

			CREATE FUNCTION tf1 (accountno integer, debit numeric) RETURNS integer AS \$block\$
				UPDATE bank
					SET balance = balance - debit
					WHERE accountno = tf1.accountno;
				SELECT balance FROM bank WHERE accountno = tf1.accountno;
			\$block\$ LANGUAGE SQL;

			--
			CREATE FUNCTION check_password(uname TEXT, pass TEXT)
			RETURNS BOOLEAN AS \$\$
			DECLARE passed BOOLEAN;
			BEGIN
					SELECT  (pwd = \$2) INTO passed
					FROM    pwds
					WHERE   username = \$1;
			
					RETURN passed;
			END;
			\$\$  LANGUAGE plpgsql
				SECURITY DEFINER
				SET search_path = admin, pg_temp;
			"""
			def result
		when:
			result = new SqlSplitter().split(query)
		then:
			result.size == 4
			result[0].statement == """CREATE FUNCTION one() RETURNS integer AS \$\$
				SELECT 1 AS result;
			\$\$ LANGUAGE SQL"""
		
			result[1].statement == "SELECT one()"
			result[2].statement == """CREATE FUNCTION tf1 (accountno integer, debit numeric) RETURNS integer AS \$block\$
				UPDATE bank
					SET balance = balance - debit
					WHERE accountno = tf1.accountno;
				SELECT balance FROM bank WHERE accountno = tf1.accountno;
			\$block\$ LANGUAGE SQL"""
			result[3].statement == """CREATE FUNCTION check_password(uname TEXT, pass TEXT)
			RETURNS BOOLEAN AS \$\$
			DECLARE passed BOOLEAN;
			BEGIN
					SELECT  (pwd = \$2) INTO passed
					FROM    pwds
					WHERE   username = \$1;
			
					RETURN passed;
			END;
			\$\$  LANGUAGE plpgsql
				SECURITY DEFINER
				SET search_path = admin, pg_temp"""
	}
}
