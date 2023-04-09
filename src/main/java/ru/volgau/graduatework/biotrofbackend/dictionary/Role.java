package ru.volgau.graduatework.biotrofbackend.dictionary;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum Role {

    USER(Set.of(Permission.EMPLOYER_PERMISSIONS)),

    WAREHOUSE_MANAGER(Set.of(Permission.EMPLOYER_PERMISSIONS, Permission.WAREHOUSE_MANAGER)),

    ADMIN(Set.of(Permission.EMPLOYER_PERMISSIONS, Permission.WAREHOUSE_MANAGER, Permission.ADMIN_PERMISSIONS));

    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}
