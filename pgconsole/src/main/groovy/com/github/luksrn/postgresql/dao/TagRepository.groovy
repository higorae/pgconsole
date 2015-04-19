package com.github.luksrn.postgresql.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import com.github.luksrn.postgresql.domain.Tag

@Repository
interface TagRepository extends CrudRepository<Tag, Integer>{
	
	Tag findByName(String name)
	
	List<Tag> findByNameIn(List<String> names)

}
