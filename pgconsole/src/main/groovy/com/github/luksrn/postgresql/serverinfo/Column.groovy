package com.github.luksrn.postgresql.serverinfo


class Column {
	
	Table table
	
	Integer number // attnum
	String name // attname
	String datatype // type
	Integer atttypmod
	String nullable // attnotnull
	String atthasdef // atthasdef
	String columnDefault // adsrc
	String attstattarget // attstattarget
	String attstorage // attstorage
	String typstorage
	Boolean attisserial
	String comment
		 
}
