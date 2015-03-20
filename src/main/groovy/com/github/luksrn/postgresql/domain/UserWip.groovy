package com.github.luksrn.postgresql.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator

@Entity
class UserWip {
	
	@Id
	@SequenceGenerator(name="user_wip_seq",sequenceName="user_wip_seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="user_wip_seq")
	Integer id

	@ManyToOne
	@JoinColumn(name="id_user")
	User user
	
	@ManyToOne
	@JoinColumn(name="id_connection")
	Connection connection
	
	Date lastUpdated
		
	String code		
}
