package ee.fakeplastictrees.core.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorDto {
  String message;
  MessageDefinition messageDefinition;
}
