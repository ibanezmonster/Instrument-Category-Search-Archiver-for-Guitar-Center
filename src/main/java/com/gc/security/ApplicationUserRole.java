package com.gc.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static com.gc.security.ApplicationUserPermission.*;

import java.util.Set;
import java.util.stream.Collectors;

public enum ApplicationUserRole 
{	
    USER(Sets.newHashSet(USER_READ, USER_WRITE)),
    ADMIN(Sets.newHashSet(GC_READ, GC_WRITE, USER_READ, USER_WRITE));
    
    private final Set<ApplicationUserPermission> permissions;

    ApplicationUserRole(Set<ApplicationUserPermission> permissions) 
    {
        this.permissions = permissions;
    }

    public Set<ApplicationUserPermission> getPermissions() 
    {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthorities() 
    {
        Set<SimpleGrantedAuthority> permissions = 
        		getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }
}