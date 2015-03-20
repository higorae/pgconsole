package com.github.luksrn.postgresql.dao

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.luksrn.postgresql.Application;
import com.github.luksrn.postgresql.dao.ConnectionRepository;

@Ignore
@RunWith(SpringJUnit4ClassRunner)
@SpringApplicationConfiguration(classes=Application)
class ConnectionRepositoryIntegrationTest {

	@Autowired
	ConnectionRepository connectionRepository
	
	
}
