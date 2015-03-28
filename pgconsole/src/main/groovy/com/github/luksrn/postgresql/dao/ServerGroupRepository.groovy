package com.github.luksrn.postgresql.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository;

import com.github.luksrn.postgresql.domain.ServerGroup
import com.github.luksrn.postgresql.domain.User;

@Repository
interface ServerGroupRepository extends CrudRepository<ServerGroup, Integer>{

	List<ServerGroup> findAllByUser(User user)
	
}
