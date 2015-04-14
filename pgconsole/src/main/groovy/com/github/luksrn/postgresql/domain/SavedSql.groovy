package com.github.luksrn.postgresql.domain

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.SequenceGenerator
import javax.persistence.Table

@Entity
@Table(name="saved_sql")
class SavedSql {
		
	@Id
	@SequenceGenerator(name="saved_sql_seq",sequenceName="saved_sql_seq",allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator="saved_sql_seq")
	Integer id

	@ManyToOne
	@JoinColumn(name="id_user")
	User user
	
	@ManyToOne
	@JoinColumn(name="id_connection")
	Connection connection
	
	String title
	
	String description
			
	Date dateCreated
		
	String code
	
	@OneToMany
	@JoinTable(name="saved_sql_tag",
		joinColumns=@JoinColumn(name="id_saved_sql", referencedColumnName="id"),
		inverseJoinColumns=	@JoinColumn(name="id_tag", referencedColumnName="id"))
	List<Tag> tags = new ArrayList<Tag>()
	
	def getFullDescription(){
		"""
--Title: ${title}
--Description: ${description}
-- tags: ${tags}

${code}
		"""
	}
}
