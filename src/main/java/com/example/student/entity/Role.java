package com.example.student.entity;

import java.util.Set;

public enum Role {
    ADMIN(Set
            .of(
                    Permissions.STUDENT_READ,
                    Permissions.STUDENT_DELETE,
                    Permissions.STUDENT_WRITE,
                    Permissions.STUDENT_MODIFY)
    ),
    STUDENT(Set
            .of(
                    Permissions.STUDENT_READ,
                    Permissions.STUDENT_WRITE)
    ),
    TEACHER(Set
            .of(
                    Permissions.STUDENT_READ,
                    Permissions.STUDENT_MODIFY)
    );
    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }

    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
