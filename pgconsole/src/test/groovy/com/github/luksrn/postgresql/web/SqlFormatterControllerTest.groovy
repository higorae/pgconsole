package com.github.luksrn.postgresql.web

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration

import com.github.luksrn.postgresql.Application
import com.jayway.restassured.authentication.FormAuthConfig;

import static com.jayway.restassured.RestAssured.*
import static com.jayway.restassured.matcher.RestAssuredMatchers.*
import static com.jayway.restassured.module.jsv.JsonSchemaValidator.*
import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.*
import static org.hamcrest.Matchers.*

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
class SqlFormatterControllerTest {
	 
	
	@Test
	public void testIfCanFormatSingleSql() {
		 		
		given().auth().form("admin","admin",new FormAuthConfig("/login", "username", "password")).
		given().			 
				param("code", "SELECT 1 FROM TABLE;").
		when().
				get("/console/sqlformatter").
		then().
				statusCode(200).
				body("code", equalTo(
"""
    SELECT
        1 
    FROM
        TABLE;
"""))
		 
	}

}  
