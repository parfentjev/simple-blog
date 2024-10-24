package ee.fakeplastictrees.blog.codegen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
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
 * PageDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0-SNAPSHOT")
public class PageDto {

  private Integer page;

  private Integer totalPages;

  @Valid
  private List<Object> items = new ArrayList<>();

  public PageDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PageDto(Integer page, Integer totalPages, List<Object> items) {
    this.page = page;
    this.totalPages = totalPages;
    this.items = items;
  }

  public PageDto page(Integer page) {
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

  public PageDto totalPages(Integer totalPages) {
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

  public PageDto items(List<Object> items) {
    this.items = items;
    return this;
  }

  public PageDto addItemsItem(Object itemsItem) {
    if (this.items == null) {
      this.items = new ArrayList<>();
    }
    this.items.add(itemsItem);
    return this;
  }

  /**
   * Page items
   * @return items
   */
  @NotNull 
  @Schema(name = "items", description = "Page items", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("items")
  public List<Object> getItems() {
    return items;
  }

  public void setItems(List<Object> items) {
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
    PageDto pageDto = (PageDto) o;
    return Objects.equals(this.page, pageDto.page) &&
        Objects.equals(this.totalPages, pageDto.totalPages) &&
        Objects.equals(this.items, pageDto.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, totalPages, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageDto {\n");
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

