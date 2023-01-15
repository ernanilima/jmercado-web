package br.com.ernanilima.auth.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.env.PropertiesPropertySourceLoader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseExtension implements AfterEachCallback {

    private final String SCHEMA_TO_NOT_IGNORE = "PUBLIC";

    private final List<String> TABLES_TO_IGNORE = List.of(
            "role", "databasechangelog", "databasechangeloglock"
    );

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        DataSource dataSource = getDataSourceFromProperties("application-test.properties", "spring.datasource");
        cleanDatabase(dataSource);
    }

    private DataSource getDataSourceFromProperties(String propertiesName, String property) throws IOException {
        var propertiesPropertySourceLoader = new PropertiesPropertySourceLoader();
        PropertySource<?> propertySource = propertiesPropertySourceLoader.load(propertiesName, new ClassPathResource(propertiesName)).get(0);

        var applicationContext = new AnnotationConfigApplicationContext();
        ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
        configurableEnvironment.getPropertySources().addFirst(propertySource);

        DataSourceProperties dataSourceProperties = Binder.get(configurableEnvironment).bind(property, Bindable.of(DataSourceProperties.class)).get();

        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());

        return dataSource;
    }

    private void cleanDatabase(DataSource dataSource) {
        try {
            Connection connection = dataSource.getConnection();

            connection.setAutoCommit(Boolean.FALSE);

            List<TableData> tablesToClean = loadTablesToClean(connection);
            cleanTablesData(tablesToClean, connection);
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<TableData> loadTablesToClean(Connection connection) throws SQLException {
        DatabaseMetaData databaseMetaData = connection.getMetaData();
        ResultSet resultSet = databaseMetaData.getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

        List<TableData> tablesToClean = new ArrayList<>();
        while (resultSet.next()) {
            TableData table = new TableData(resultSet.getString("TABLE_SCHEM"), resultSet.getString("TABLE_NAME"));

            if (SCHEMA_TO_NOT_IGNORE.equals(table.getSchema()) && TABLES_TO_IGNORE.stream().noneMatch(t -> t.equalsIgnoreCase(table.getName()))) {
                tablesToClean.add(table);
            }
        }

        return tablesToClean;
    }

    private void cleanTablesData(List<TableData> tablesNames, Connection connection) throws SQLException {
        if (!tablesNames.isEmpty()) {
            // desativar a verificação de restrição de chave estrangeira
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY = 0;").execute();

            StringBuilder stringBuilder = new StringBuilder();

            for (TableData tablesName : tablesNames) {
                stringBuilder.append("DELETE FROM ").append(tablesName.getTableName()).append(";");
                connection.prepareStatement(stringBuilder.toString()).execute();
            }

            // ativar a verificação de restrição de chave estrangeira
            connection.prepareStatement("SET REFERENTIAL_INTEGRITY = 1;").execute();
        }
    }

    @Data
    @AllArgsConstructor
    private static class TableData {
        private String schema;
        private String name;

        public String getTableName() {
            return (getSchema() != null) ? getSchema() + "." + getName() : getName();
        }
    }
}
