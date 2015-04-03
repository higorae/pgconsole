package com.github.luksrn.postgresql.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.serverinfo.SequenceObjectDescription;
import com.github.luksrn.postgresql.serverinfo.TableObjectDescriptipon
import com.github.luksrn.postgresql.serverinfo.ViewObjectDescription;

@Controller
class ObjectDescriptionController {
	
	TableObjectDescriptipon tableObjectDescriptipon
	
	ViewObjectDescription viewObjectDescription
	
	SequenceObjectDescription sequenceObjectDescription
		
	@Autowired
	public ObjectDescriptionController(TableObjectDescriptipon tableObjectDescriptipon,
				ViewObjectDescription viewObjectDescription,
				SequenceObjectDescription sequenceObjectDescription	) {
		this.tableObjectDescriptipon = tableObjectDescriptipon;
		this.viewObjectDescription = viewObjectDescription
		this.sequenceObjectDescription = sequenceObjectDescription
	}


	@RequestMapping(value="/serverExplorer/objectDescription")
	@ResponseBody
	def objectDescription(@RequestParam("type") String type, 
					@RequestParam(value="database",required=false) String database ,
					@RequestParam(value="schema",required=false) String schema,
					@RequestParam(value="table",required=false) String table,
					@RequestParam(value="view",required=false) String view,
					@RequestParam(value="sequence",required=false) String sequence  ){

		switch ( type ){
			case 'table':
				return tableObjectDescriptipon.getDescription(schema, table)
			case 'view':
				return viewObjectDescription.getDescription(schema, view)
			case 'sequence':
				return sequenceObjectDescription.getDescription(schema,sequence)
			default:
				return 'Not implemented.'				
		}
	}
}
