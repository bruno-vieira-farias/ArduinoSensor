package br.com.onurbasoft.database;

import org.apache.commons.dbcp2.BasicDataSource;

public class DataSourceFactory {
    private final static DataSourceFactory dataSourceFactory = new DataSourceFactory();
    private final static BasicDataSource dataSource = new BasicDataSource();

    private DataSourceFactory() {}

    public static DataSourceFactory getInstance() {
        return dataSourceFactory;
    }

    public BasicDataSource create() {
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("tt@aec");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setMaxTotal(2);
        dataSource.setMaxIdle(5);
        dataSource.setInitialSize(5);
        dataSource.setValidationQuery("SELECT 1");
        return dataSource;
    }

}
