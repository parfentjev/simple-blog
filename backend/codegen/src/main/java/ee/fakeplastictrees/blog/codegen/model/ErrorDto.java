package ee.fakeplastictrees.blog.codegen.model;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ErrorDto
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", comments = "Generator version: 7.10.0-SNAPSHOT")
public class ErrorDto {

  private String message;

  private String messageDefinition;

  public ErrorDto() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ErrorDto(String message, String messageDefinition) {
    this.message = message;
    this.messageDefinition = messageDefinition;
  }

  public ErrorDto message(String message) {
    this.message = message;
    return this;
  }

  /**
   * Error message
   * @return message
   */
  @NotNull 
  @Schema(name = "message", example = "User already exists", description = "Error message", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("message")
  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public ErrorDto messageDefinition(String messageDefinition) {
    this.messageDefinition = messageDefinition;
    return this;
  }

  /**
   * Error message definition
   * @return messageDefinition
   */
  @NotNull 
  @Schema(name = "messageDefinition", example = "USER_ALREADY_EXISTS", description = "Error message definition", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("messageDefinition")
  public String getMessageDefinition() {
    return messageDefinition;
  }

  public void setMessageDefinition(String messageDefinition) {
    this.messageDefinition = messageDefinition;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorDto errorDto = (ErrorDto) o;
    return Objects.equals(this.message, errorDto.message) &&
        Objects.equals(this.messageDefinition, errorDto.messageDefinition);
  }

  @Override
  public int hashCode() {
    return Objects.hash(message, messageDefinition);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorDto {\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    messageDefinition: ").append(toIndentedString(messageDefinition)).append("\n");
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

