package com.github.luksrn.postgresql.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name="tag")
class Tag {
		
	@Id
	@SequenceGenerator(name="tag_seq",sequenceName="tag_seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="tag_seq")
	Integer id

	@ManyToOne
	@JoinColumn(name="id_user")
	User user
	
	String name
	
	String toString(){
		name
	}	
}
