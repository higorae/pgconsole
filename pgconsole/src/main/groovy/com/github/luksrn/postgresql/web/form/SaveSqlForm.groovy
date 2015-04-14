package com.github.luksrn.postgresql.web.form

import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.github.luksrn.postgresql.domain.Connection;
import com.github.luksrn.postgresql.domain.SavedSql;
import com.github.luksrn.postgresql.domain.Tag;
import com.github.luksrn.postgresql.domain.User;

class SaveSqlForm {
	
	Integer idConnection
	
	String title
	
	String description
	
	String code
	
	String tags

	def build(){
		def connection = new Connection(id: idConnection )
		SavedSql s = new SavedSql(title: title, description: description, code: code,  connection: connection, dateCreated: new Date())
		tags.split(",").each{
			s.tags <<  new Tag(name: it )
		} 
		 
		s
	}
}
