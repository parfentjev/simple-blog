package ee.fakeplastictrees.blog.user.model;

import lombok.Getter;

import java.util.Set;

public enum UserRole {
    ADMIN(UserPrivilege.USER_MANAGEMENT, UserPrivilege.POST_MANAGEMENT),
    EDITOR(UserPrivilege.POST_MANAGEMENT);

    @Getter
    private final Set<UserPrivilege> privileges;

    UserRole(UserPrivilege... privileges) {
        this.privileges = Set.of(privileges);
    }
}
