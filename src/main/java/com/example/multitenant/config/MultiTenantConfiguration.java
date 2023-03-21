package com.example.multitenant.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MultiTenantConfiguration {

    @Value("${defaultTenant:tenant1}")
    private String defaultTenant;

    @Bean
    public DataSource dataSource() {
        Map<Object, Object> resolvedDataSources = new HashMap<>();
        for (Tenant tenant : Tenant.getAllTenant()) {
            try {
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                dataSourceBuilder.username(tenant.getUsername());
                dataSourceBuilder.password(tenant.getPassword());
                dataSourceBuilder.url(tenant.getUrl());
                resolvedDataSources.put(tenant.getId(), dataSourceBuilder.build());
            } catch (Exception exp) {
                throw new RuntimeException("Exception occurred while adding datasource:" + exp);
            }
        }

        AbstractRoutingDataSource multiTenantDataSource = new MultiTenantDataSource();
        multiTenantDataSource.setDefaultTargetDataSource(resolvedDataSources.get(defaultTenant));
        multiTenantDataSource.setTargetDataSources(resolvedDataSources);

        multiTenantDataSource.afterPropertiesSet();
        return multiTenantDataSource;
    }

}