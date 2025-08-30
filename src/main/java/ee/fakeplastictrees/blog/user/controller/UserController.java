package ee.fakeplastictrees.blog.user.controller;

import ee.fakeplastictrees.blog.user.model.CreateUserException;
import ee.fakeplastictrees.blog.user.model.UserRegistrationRequestDto;
import ee.fakeplastictrees.blog.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {
  private static final String TEXT_INVALID_CREDENTIALS = "Invalid username or password.";
  private static final String TEXT_USER_CREATED = "User was created.";
  private static final String TEXT_SOMETHING_WRONG = "Something went wrong.";

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/login")
  public String getLoginForm() {
    return "login";
  }

  @GetMapping("/register")
  public String getRegistrationForm() {
    if (userService.isRegistrationDisabled()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    return "register";
  }

  @PostMapping("/register")
  public String postRegistrationForm(@Valid @ModelAttribute UserRegistrationRequestDto request,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {
    if (userService.isRegistrationDisabled()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    if (bindingResult.hasErrors()) {
      redirectAttributes.addFlashAttribute("error", TEXT_INVALID_CREDENTIALS);
      return "redirect:/register";
    }

    try {
      userService.createUser(request.username(), request.password());
      redirectAttributes.addFlashAttribute("message", TEXT_USER_CREATED);
    } catch (CreateUserException e) {
      redirectAttributes.addFlashAttribute("error", e.getMessage());
      return "redirect:/register";
    } catch (Exception e) {
      redirectAttributes.addFlashAttribute("error", TEXT_SOMETHING_WRONG);
      return "redirect:/register";
    }

    return "redirect:/login";
  }
}
