package com.github.luksrn.postgresql

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

import com.github.luksrn.postgresql.helper.SqlLookup

@Configuration
@ComponentScan
@EnableAutoConfiguration
class Application { 
	
	@Bean
	public SqlLookup sqlLookup(){
		CacheManager cm = new ConcurrentMapCacheManager()
		String path = "postgresqls"
		Cache cache = cm.getCache("sqlLocatorTest");
		new SqlLookup( cache , path );
	}

    static void main(String[] args) {
        SpringApplication.run Application, args
    }
}
