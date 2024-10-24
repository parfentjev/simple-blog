package ee.fakeplastictrees.blog.codegen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import ee.fakeplastictrees.blog.codegen.model.PostPreviewDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * PagePostDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0-SNAPSHOT")
public class PagePostDto {

  private Integer page;

  private Integer totalPages;

  @Valid
  private List<@Valid PostPreviewDto> items = new ArrayList<>();

  public PagePostDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PagePostDto(Integer page, Integer totalPages, List<@Valid PostPreviewDto> items) {
    this.page = page;
    this.totalPages = totalPages;
    this.items = items;
  }

  public PagePostDto page(Integer page) {
    this.page = page;
    return this;
  }

  /**
   * Current page
   * @return page
   */
  @NotNull 
  @Schema(name = "page", description = "Current page", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public PagePostDto totalPages(Integer totalPages) {
    this.totalPages = totalPages;
    return this;
  }

  /**
   * Total pages
   * @return totalPages
   */
  @NotNull 
  @Schema(name = "totalPages", description = "Total pages", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("totalPages")
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  public PagePostDto items(List<@Valid PostPreviewDto> items) {
    this.items = items;
    return this;
  }

  public PagePostDto addItemsItem(PostPreviewDto itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Get items
   * @return items
   */
  @NotNull @Valid 
  @Schema(name = "items", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<@Valid PostPreviewDto> getItems() {
    return items;
  }

  public void setItems(List<@Valid PostPreviewDto> items) {
    this.items = items;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PagePostDto pagePostDto = (PagePostDto) o;
    return Objects.equals(this.page, pagePostDto.page) &&
        Objects.equals(this.totalPages, pagePostDto.totalPages) &&
        Objects.equals(this.items, pagePostDto.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, totalPages, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagePostDto {\n");
    sb.append("    page: ").append(toIndentedString(page)).append("\n");
    sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
    sb.append("    items: ").append(toIndentedString(items)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

