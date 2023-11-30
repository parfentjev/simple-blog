package ee.fakeplastictrees.blog.user.model;

import lombok.Getter;

import java.util.List;

public enum UserRole {
    ADMIN(UserPrivilege.USER_MANAGEMENT, UserPrivilege.POST_MANAGEMENT),
    EDITOR(UserPrivilege.POST_MANAGEMENT);

    @Getter
    private final List<UserPrivilege> privileges;

    UserRole(UserPrivilege... privileges) {
        this.privileges = List.of(privileges);
    }
}
