package mh.dev.blog.multi.core;

import mh.dev.blog.multi.common.model.Test;
import mh.dev.blog.multi.common.repository.TestRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.IOException;

    @ComponentScan("mh.dev.blog.multi")
    @Configuration
    @EnableJpaRepositories(basePackages = {"mh.dev.blog.multi.common.repository"})
    @EnableAutoConfiguration
    @EnableTransactionManagement
    public class Application {

        public static void main(String[] args) throws IOException {
            ConfigurableApplicationContext context = SpringApplication.run(Application.class);
            TestRepository repository = context.getBean(TestRepository.class);
            Test test = new Test();
            test.setText("test");
            repository.save(test);
            repository.findAll().iterator().forEachRemaining(f -> System.out.println(f));
            context.close();
        }
    }
