package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import com.github.luksrn.postgresql.helper.SqlSplitter

@RestController
class SqlSplitterController {
	 
	def splitter
	
	@Autowired
	SqlSplitterController( SqlSplitter splitter ){
		this.splitter = splitter
	}
	
	@RequestMapping(value="/split", produces = "application/json")
	def splitter( @RequestParam("code") String code ){						
		splitter.split( code )
	}
}
