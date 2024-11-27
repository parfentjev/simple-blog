package ee.fakeplastictrees.blog.codegen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * MediaPost200Response
 */

@JsonTypeName("_media_post_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0-SNAPSHOT")
public class MediaPost200Response {

  private String filename;

  public MediaPost200Response() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public MediaPost200Response(String filename) {
    this.filename = filename;
  }

  public MediaPost200Response filename(String filename) {
    this.filename = filename;
    return this;
  }

  /**
   * Name of the file
   * @return filename
   */
  @NotNull 
  @Schema(name = "filename", description = "Name of the file", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("filename")
  public String getFilename() {
    return filename;
  }

  public void setFilename(String filename) {
    this.filename = filename;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    MediaPost200Response mediaPost200Response = (MediaPost200Response) o;
    return Objects.equals(this.filename, mediaPost200Response.filename);
  }

  @Override
  public int hashCode() {
    return Objects.hash(filename);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class MediaPost200Response {\n");
    sb.append("    filename: ").append(toIndentedString(filename)).append("\n");
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

