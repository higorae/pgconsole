package com.github.luksrn.postgresql.web
 
import java.security.Principal

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

import com.github.luksrn.postgresql.dao.ConnectionRepository
import com.github.luksrn.postgresql.domain.User
import com.github.luksrn.postgresql.web.form.NewConnectionForm

@RestController
@RequestMapping(value="connection")
class ConnectionController {

	static final String ID_CONNECTION_KEY = 'id_connection'
	
	ConnectionRepository connectionRepository
	
	@Autowired
	ConnectionController(ConnectionRepository connectionRepository){
		this.connectionRepository = connectionRepository
	}
	
	@RequestMapping(value="create",method=RequestMethod.POST)
	@ResponseBody
	Map create( @ModelAttribute("connection") @Valid NewConnectionForm connectionForm, 
				BindingResult bindingResult, 
				Principal principal){
				
		if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid connection", bindingResult);
        }
		
		def connection = connectionForm.buildObject()
		connection.user = new User( username: principal.getName() ) 
		
		connectionRepository.save(connection)
		[ 'result' : connection ]
	}
}
