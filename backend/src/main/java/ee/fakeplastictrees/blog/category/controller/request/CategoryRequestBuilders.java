package ee.fakeplastictrees.blog.category.controller.request;

public class CategoryRequestBuilders {
    public PostCategoriesRequest.PostCategoriesRequestBuilder postCategories() {
        return PostCategoriesRequest.builder();
    }

    public PutCategoriesRequest.PutCategoriesRequestBuilder putCategories() {
        return PutCategoriesRequest.builder();
    }
}
