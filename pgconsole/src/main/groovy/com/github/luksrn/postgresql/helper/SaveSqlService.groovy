package com.github.luksrn.postgresql.helper

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.luksrn.postgresql.dao.SavedSqlRepository;
import com.github.luksrn.postgresql.dao.TagRepository;
import com.github.luksrn.postgresql.domain.SavedSql;
import com.github.luksrn.postgresql.domain.User;

@Service
class SaveSqlService {
	
	TagRepository tagRepository
	SavedSqlRepository savedSqlRepository

	@Autowired
	public SaveSqlService(
		TagRepository tagRepository,
		SavedSqlRepository savedSqlRepository){
		this.tagRepository = tagRepository
		this.savedSqlRepository = savedSqlRepository		
	}

	@Transactional
	def save(SavedSql savedSql, User user){
		
		savedSql.tags.each {
			def tag = tagRepository.findByName( it.name )
			if ( tag ){
				it.id = tag.id
			} else {
				tagRepository.save( it )
			}
		}
		savedSql.user = user
		
		savedSqlRepository.save( savedSql )
	}

}
