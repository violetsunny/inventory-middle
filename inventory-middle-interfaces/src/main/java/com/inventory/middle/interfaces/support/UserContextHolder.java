package com.inventory.middle.interfaces.support;

public class UserContextHolder {
    private static final ThreadLocal<UserContext> HOLDER = new ThreadLocal<>();

    public static void set(UserContext ctx) { HOLDER.set(ctx); }
    public static UserContext get() { return HOLDER.get(); }
    public static void clear() { HOLDER.remove(); }

    public static String getTenantId() {
        UserContext ctx = get();
        return ctx != null ? ctx.getTenantId() : null;
    }
    public static String getUserId() {
        UserContext ctx = get();
        return ctx != null ? ctx.getUserId() : null;
    }
    public static String getUsername() {
        UserContext ctx = get();
        return ctx != null ? ctx.getUsername() : null;
    }
}
