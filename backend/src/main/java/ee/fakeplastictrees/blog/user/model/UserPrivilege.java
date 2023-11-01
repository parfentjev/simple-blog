package ee.fakeplastictrees.blog.user.model;

import org.springframework.security.core.Authentication;

public enum UserPrivilege {
    USER_MANAGEMENT,
    POST_MANAGEMENT;

    public boolean granted(Authentication authentication) {
        if (authentication == null) {
            return false;
        }

        return authentication.getAuthorities().stream().anyMatch(i -> i.getAuthority().equals("ROLE_" + name()));
    }
}
