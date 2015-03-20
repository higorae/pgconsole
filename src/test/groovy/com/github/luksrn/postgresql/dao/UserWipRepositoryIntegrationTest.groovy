package com.github.luksrn.postgresql.dao

import static org.hamcrest.Matchers.greaterThan
import static org.hamcrest.Matchers.hasSize
import static org.hamcrest.Matchers.is
import static org.junit.Assert.assertThat

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

import com.github.luksrn.postgresql.Application

@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes=Application)	
class UserWipRepositoryIntegrationTest {

	@Autowired
	UserWipRepository userWipRepository
	
	@Test
	void testIfFindUserWorkInProgressForGivenConnection(){
		Assert.assertTrue(userWipRepository != null)	
	}
}
