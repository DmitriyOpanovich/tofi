package com.tofi.config;

import com.tofi.service.*;
import com.tofi.service.impl.*;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@SpringBootApplication(
		scanBasePackages = {
				"com.tofi.rest"
		}
)
@EnableJpaRepositories("com.tofi.repository")
@EntityScan("com.tofi.model")
@EnableAsync
public class FinanceAssistantApplication {


	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(FinanceAssistantApplication.class, args);

	}

	@Bean
	@ConfigurationProperties(prefix="spring.datasource")
	@Primary
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}


	@Bean
	public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(false);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.tofi.model");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}


	@Bean
	public CreditService creditService() {
		return new CreditServiceImpl();
	}

	@Bean
	public DepositService depositService() {return new DepositServiceImpl();}

	@Bean
	public FilterService filterService() {
		return new FilterServiceImpl();
	}

	@Bean
	public EnumService enumService() {return new EnumServiceImpl();}

	@Bean
	public UserService userService() {return new UserServiceImpl();}

	@Bean
	public FeedbackService feedbackService() {return new FeedbackServiceImpl();}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper mapper = new ModelMapper();
		return mapper;
	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		MethodValidationPostProcessor processor =
				new MethodValidationPostProcessor();
		processor.setValidator(validator());
		return processor;
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

}
