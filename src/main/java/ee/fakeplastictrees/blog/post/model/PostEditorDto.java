package ee.fakeplastictrees.blog.post.model;

public record PostEditorDto(
    String id, String title, String summary, String text, Boolean visible, Boolean updateDate) {}
