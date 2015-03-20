package com.github.luksrn.postgresql.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository;

import com.github.luksrn.postgresql.domain.Connection;
import com.github.luksrn.postgresql.domain.User;
import com.github.luksrn.postgresql.domain.UserWip

@Repository
interface UserWipRepository extends CrudRepository<UserWip, Integer> {

	UserWip findByUserAndConnection( User u , Connection c )
}
