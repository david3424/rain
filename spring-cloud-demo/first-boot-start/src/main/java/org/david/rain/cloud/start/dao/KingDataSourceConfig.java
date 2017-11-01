package org.david.rain.cloud.start.dao;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

/**
 * @author xdw9486
 */
@Configuration
//@MapperScan(basePackages = KingDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "kingSqlSessionFactory")
@MapperScan(basePackages = KingDataSourceConfig.PACKAGE, sqlSessionTemplateRef = "kingSqlSessionTemplate")
public class KingDataSourceConfig {


    static final String PACKAGE = "org.david.rain.cloud.start.dao.mapper.king";
    static final String MAPPER_LOCATION = "classpath:org/david/rain/cloud/start/dao/mapper/king/*.xml";

    @Primary
    @Bean(name = "kingDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.king")
    public DataSource kingDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "kingJdbcTemplate")
    public JdbcTemplate kingJdbcTemplate(@Qualifier("kingDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean(name = "kingTransactionManager")
    PlatformTransactionManager kingTransactionManager(@Qualifier("kingDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "kingTransactionTemplate")
    public TransactionTemplate kingTransactionTemplate(
            @Qualifier("kingTransactionManager") PlatformTransactionManager transactionManager) {
        return new TransactionTemplate(transactionManager);
    }

    @Primary
    @Bean(name = "kingSqlSessionFactory")
    public SqlSessionFactory kingSqlSessionFactory(@Qualifier("kingDataSource") DataSource kingDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(kingDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(KingDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }

    @Bean(name = "kingSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate kingSqlSessionTemplate(@Qualifier("kingSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
