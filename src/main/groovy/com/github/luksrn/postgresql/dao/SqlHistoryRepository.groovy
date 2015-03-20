package com.github.luksrn.postgresql.dao

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

import com.github.luksrn.postgresql.domain.Connection
import com.github.luksrn.postgresql.domain.SqlHistory
import com.github.luksrn.postgresql.domain.User

@Repository
interface SqlHistoryRepository extends PagingAndSortingRepository<SqlHistory, Integer>{

	Page<SqlHistory> findByUserAndConnection( User user, Connection connection , Pageable page )
}
