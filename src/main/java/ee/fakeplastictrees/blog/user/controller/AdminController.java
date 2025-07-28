package ee.fakeplastictrees.blog.user.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
  @GetMapping
  @ProtectedRoute
  public String index() {
    return "admin/index";
  }
}
