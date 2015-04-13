package com.github.luksrn.postgresql.dao

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import com.github.luksrn.postgresql.domain.Connection;
import com.github.luksrn.postgresql.domain.SavedSql
import com.github.luksrn.postgresql.domain.SqlHistory;
import com.github.luksrn.postgresql.domain.User;

@Repository
interface SavedSqlRepository  extends  CrudRepository<SavedSql, Integer> {
	
	Page<SavedSql> findByUserAndConnectionOrderByDateCreatedDesc( User user, Connection connection , Pageable page )
}
