package com.example.student.entity;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permissions.STUDENT_READ, Permissions.STUDENT_DELETE, Permissions.STUDENT_WRITE)),
    USER(Set.of(Permissions.STUDENT_READ));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
