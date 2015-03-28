package com.github.luksrn.postgresql.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="users")
class User {
	  
	@Id
	String username
	String password
	boolean enabled = true
	 
}
