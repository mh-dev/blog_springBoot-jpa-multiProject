package mh.dev.blog.multi.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.orm.jpa.SpringNamingStrategy;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitManager;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DatabaseConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver jpaPropertyResolver;
    @Autowired(required = false)
    private PersistenceUnitManager persistenceUnitManager;

    @Override
    public void setEnvironment(Environment environment) {
        this.jpaPropertyResolver = new RelaxedPropertyResolver(environment, "spring.jpa.");
    }

    @Bean
    @DependsOn("jdbcTemplate")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        if (persistenceUnitManager != null) {
            entityManagerFactoryBean
                    .setPersistenceUnitManager(persistenceUnitManager);
        }
        entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("mh.dev.blog.multi");
        entityManagerFactoryBean.getJpaPropertyMap().putAll(jpaPropertyResolver.getSubProperties("properties."));
        Map<String, Object> properties = entityManagerFactoryBean.getJpaPropertyMap();
        properties.put("hibernate.ejb.naming_strategy", jpaPropertyResolver.getProperty("hibernate.naming-strategy", SpringNamingStrategy.class.getName()));
        properties.put("hibernate.hbm2ddl.auto", jpaPropertyResolver.getProperty("hibernate.ddl-auto", "none"));
        return entityManagerFactoryBean;
    }
}
