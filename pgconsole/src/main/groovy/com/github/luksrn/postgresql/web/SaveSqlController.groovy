package com.github.luksrn.postgresql.web

import java.security.Principal

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

import com.github.luksrn.postgresql.domain.SavedSql
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.helper.SaveSqlService
import com.github.luksrn.postgresql.web.form.SaveSqlForm

@Controller
class SaveSqlController {
	SaveSqlService saveSqlService
 	
	@Autowired
	public SaveSqlController(SaveSqlService saveSqlService){
		this.saveSqlService = saveSqlService		
	}
	
	@RequestMapping(value="console/savesql",method=RequestMethod.POST)
	@ResponseBody
	def save(@ModelAttribute SaveSqlForm form, BindingResult result, Principal principal){
		
		try {
			
			SavedSql saveSql = form.build()
			User user = new User(username: principal.getName() )
			
			saveSqlService.save( saveSql, user )
			
			['result' : saveSql , 'message': 'Sql saved with success!' ]
		} catch (e){
			e.printStackTrace()
			[ 'message': 'Error!' ]
		}			
	}
	
}
