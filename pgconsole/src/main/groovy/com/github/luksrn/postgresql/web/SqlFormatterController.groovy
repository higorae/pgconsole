package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.luksrn.postgresql.helper.SqlFormatter
import com.github.luksrn.postgresql.helper.SqlSplitter

@Controller
class SqlFormatterController {
	
	SqlFormatter formatter
	SqlSplitter converter
	
	@Autowired
	SqlFormatterController( SqlFormatter formatter, SqlSplitter converter ){
		this.formatter = formatter;
		this.converter = converter;
	}

	@RequestMapping(value="console/sqlformatter", produces = "application/json")
	@ResponseBody
	def formatter( @RequestParam("code") String code ){
		def queries = converter.split( code )
		def codeFormatted = formatter.formatter( queries )
		
		[ 'code' : codeFormatted ]
	}
}
