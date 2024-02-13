package com.gc.security;

public enum ApplicationUserPermission 
{
	GC_READ("gc:read"),
	GC_WRITE("gc:write"),
	USER_READ("user:read"),
	USER_WRITE("user:write");

    private final String permission;
    
    //ApplicationUserPermission() {}

    ApplicationUserPermission(String permission) 
    {
    	//super(permission);
        this.permission = permission;
    }

    public String getPermission() 
    {
        return permission;
    }
}