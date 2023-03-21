package com.example.multitenant.config;

public class TenantContext {

    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    public static String getCurrentTenant() {
        return CURRENT_TENANT.get();
    }

    public static void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}
//
//public class TenantContext {
//    private static final ThreadLocal<String> currentTenantId = new ThreadLocal<>();
//
//    public static String getCurrentTenantId() {
//        return currentTenantId.get();
//    }
//
//    public static void setCurrentTenantId(String tenantId) {
//        currentTenantId.set(tenantId);
//    }
//
//    public static void clear() {
//        currentTenantId.remove();
//    }
//}

