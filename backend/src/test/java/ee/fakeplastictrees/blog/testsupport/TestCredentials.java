package ee.fakeplastictrees.blog.testsupport;

public enum TestCredentials {
    EDITOR("editor", "test"),
    ADMIN("admin", "test");

    private String username;
    private String password;

    TestCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
