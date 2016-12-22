package com.tofi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by ulian_000 on 12.12.2016.
 */
@Import({FinanceAssistantApplication.class})
@Configuration
@PropertySource("classpath:hibernate.properties")
@EntityScan(basePackages = {"model"})
public class HibernateConfig {

    private final static Logger log = LoggerFactory.getLogger(HibernateConfig.class);

//    @Autowired
//    private Environment env;
//
//
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
//
//        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(dataSource);
//
//        //sessionFactory.setPackagesToScan("model");
//        sessionFactory.setHibernateProperties(hibernateProperties());
//        return sessionFactory;
//    }
//
//    @Bean
//    @Autowired
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager txManager = new HibernateTransactionManager();
//        txManager.setSessionFactory(sessionFactory);
//
//        return txManager;
//    }
//
//    @Bean
//    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//    }
//
//    Properties hibernateProperties() {
//        return new Properties() {
//            {
//                setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
//                setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
//            }
//        };
//    }

}
