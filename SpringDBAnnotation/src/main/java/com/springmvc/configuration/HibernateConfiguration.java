package com.springmvc.configuration;

import java.util.Properties;
import javax.sql.DataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan({ "com.springmvc.configuration" })
@PropertySource(value = { "classpath:application.properties" })
public class HibernateConfiguration {
	@Autowired
	private Environment env;

	@Bean
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sfb = new LocalSessionFactoryBean();
		sfb.setDataSource(dataSource());
		sfb.setPackagesToScan(new String[] { "com.springmvc.model" });
		sfb.setHibernateProperties(hibernateProperties());
		return sfb;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource src = new DriverManagerDataSource();
		src.setDriverClassName(env.getRequiredProperty("jdbc.driverClassName"));
		src.setUrl(env.getRequiredProperty("jdbc.url"));
		src.setUsername(env.getRequiredProperty("jdbc.username"));
		src.setPassword(env.getRequiredProperty("jdbc.password"));
		return src;
	}

	private Properties hibernateProperties() {
		Properties pr = new Properties();
		pr.put("hibernate.dialect", env.getRequiredProperty("hibernate.dialect"));
		pr.put("hibernate.show_sql", env.getRequiredProperty("hibernate.show_sql"));
		pr.put("hibernate.format_sql", env.getRequiredProperty("hibernate.format_sql"));
		return pr;
	}

	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory s) {
		HibernateTransactionManager tm = new HibernateTransactionManager();
		tm.setSessionFactory(s);
		return tm;
	}
}
