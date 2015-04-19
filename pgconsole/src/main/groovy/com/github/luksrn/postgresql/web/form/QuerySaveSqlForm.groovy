package com.github.luksrn.postgresql.web.form

import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.SavedSql
import com.github.luksrn.postgresql.domain.Tag

class QuerySaveSqlForm {

	Integer idConnection
	
	String title = ""
	 
	String code = ""
	
	String tags = ""
	
	def isEmpty(){
		title?.empty && code?.empty && tags?.empty;
	}
	
	def getTitleLike(){
		if ( title?.empty ){
			return "";
		}
		"%${title}%"
	}
	
	def getCodeLike(){
		if ( code?.empty ){
			return "";
		}
		"%${code}%"
	}
	
	def getTagsAsList(){
		def tagsList = []		
		tags?.split(",").each {
			tagsList << it
		}
		tagsList
	}
	
	String toString(){
		"${title} ${code} ${tags}"
	}
	
}
