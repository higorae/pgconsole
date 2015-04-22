package com.github.luksrn.postgresql.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.User

@Repository
interface ConnectionRepository extends CrudRepository<Connection, Integer>{

	List<Connection> findByUser(User user)
}
