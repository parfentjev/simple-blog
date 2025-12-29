package ee.fakeplastictrees.blog.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoreController {
  @GetMapping("/search")
  public String search() {
    return "search";
  }

  @GetMapping("/license")
  public String license() {
    return "license";
  }

  @GetMapping("/banner")
  public String banner() {
    return "banner";
  }

  @GetMapping("/contact")
  public String contact() {
    return "contact";
  }

  @GetMapping("/err")
  public String err() {
    throw new RuntimeException();
  }
}
