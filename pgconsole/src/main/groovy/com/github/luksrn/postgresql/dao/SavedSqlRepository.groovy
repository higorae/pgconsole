package com.github.luksrn.postgresql.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.SavedSql
import com.github.luksrn.postgresql.domain.Tag
import com.github.luksrn.postgresql.domain.User

@Repository
interface SavedSqlRepository  extends  CrudRepository<SavedSql, Integer> {
	
	Page<SavedSql> findByUserAndConnectionOrderByDateCreatedDesc( User user, Connection connection , Pageable page )
	
	@Query("select distinct s from SavedSql s join s.tags t where s.user = ?1 and s.connection = ?2 and ( s.title like ?3 or s.code like ?4 )")
	Page<SavedSql> findByParameters( User user, Connection connection, String title, String code, Pageable page);
	
	@Query("select distinct s from SavedSql s join s.tags t where s.user = ?1 and s.connection = ?2 and ( s.title like ?3 or s.code like ?4 or t.name in (?5) )")
	Page<SavedSql> findByParameters( User user, Connection connection, String title, String code, List<String> tags , Pageable page);
}
