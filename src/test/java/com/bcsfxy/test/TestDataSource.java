package com.bcsfxy.test;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.bcsfxy.boot.SpringBootStart;

@SpringBootTest(classes= SpringBootStart.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class TestDataSource {
	@Resource
	private DataSource dataSource;
	@Test
	public void testConnection() {
			System.out.println(dataSource);
	}
}
