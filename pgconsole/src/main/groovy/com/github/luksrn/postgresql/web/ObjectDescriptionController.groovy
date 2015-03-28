package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.serverinfo.TableObjectDescriptipon

@Controller
class ObjectDescriptionController {
	
	TableObjectDescriptipon tableObjectDescriptipon
		
	@Autowired
	public ObjectDescriptionController(TableObjectDescriptipon tableObjectDescriptipon) {
		this.tableObjectDescriptipon = tableObjectDescriptipon;
	}


	@RequestMapping(value="/serverExplorer/objectDescription")
	@ResponseBody
	def objectDescription(@RequestParam("type") String type, 
					@RequestParam(value="database",required=false) String database ,
					@RequestParam(value="schema",required=false) String schema,
					@RequestParam(value="table",required=false) String table,
					@RequestParam(value="view",required=false) String view ){

		switch ( type ){
			case 'table':
				return tableObjectDescriptipon.getDescription(schema, table)
			default:
				return 'Not implemented.'				
		}
	}
}
