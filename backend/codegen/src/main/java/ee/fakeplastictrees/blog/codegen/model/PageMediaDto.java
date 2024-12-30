package ee.fakeplastictrees.blog.codegen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import ee.fakeplastictrees.blog.codegen.model.MediaDto;
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
 * PageMediaDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0-SNAPSHOT")
public class PageMediaDto {

  private Integer page;

  private Integer totalPages;

  @Valid
  private List<@Valid MediaDto> items = new ArrayList<>();

  public PageMediaDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public PageMediaDto(Integer page, Integer totalPages, List<@Valid MediaDto> items) {
    this.page = page;
    this.totalPages = totalPages;
    this.items = items;
  }

  public PageMediaDto page(Integer page) {
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

  public PageMediaDto totalPages(Integer totalPages) {
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

  public PageMediaDto items(List<@Valid MediaDto> items) {
    this.items = items;
    return this;
  }

  public PageMediaDto addItemsItem(MediaDto itemsItem) {
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
  public List<@Valid MediaDto> getItems() {
    return items;
  }

  public void setItems(List<@Valid MediaDto> items) {
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
    PageMediaDto pageMediaDto = (PageMediaDto) o;
    return Objects.equals(this.page, pageMediaDto.page) &&
        Objects.equals(this.totalPages, pageMediaDto.totalPages) &&
        Objects.equals(this.items, pageMediaDto.items);
  }

  @Override
  public int hashCode() {
    return Objects.hash(page, totalPages, items);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PageMediaDto {\n");
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

