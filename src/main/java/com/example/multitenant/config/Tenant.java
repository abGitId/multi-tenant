package com.example.multitenant.config;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Tenant {

    private String id;
    private String url;
    private String username;
    private String password;

    public static Map<String, Tenant> tenantMap;

    static {
        Tenant tenant1 = new Tenant();
        tenant1.setUrl("jdbc:mysql://localhost:3306/tenant1");
        tenant1.setUsername("root");
        tenant1.setPassword("admin@123");
        tenant1.setId("tenant1");

        Tenant tenant2 = new Tenant();
        tenant2.setUrl("jdbc:mysql://localhost:3306/tenant2");
        tenant2.setUsername("root");
        tenant2.setPassword("admin@123");
        tenant2.setId("tenant2");

        Tenant tenant3 = new Tenant();
        tenant3.setUrl("jdbc:mysql://localhost:3306/tenant3");
        tenant3.setUsername("root");
        tenant3.setPassword("admin@123");
        tenant3.setId("tenant3");
        tenantMap = new HashMap<>();
        tenantMap.put("tenant1", tenant1);
        tenantMap.put("tenant2", tenant2);
        tenantMap.put("tenant3", tenant3);

    }

    public static Tenant getTenantById(String tenantId) {
        return tenantMap.get(tenantId);
    }

    public static List<Tenant> getAllTenant() {
        List<Tenant> tenants = new ArrayList<>();
        tenantMap.entrySet().forEach(tenantEntry -> tenants.add(tenantEntry.getValue()));
        return tenants;
    }
}
