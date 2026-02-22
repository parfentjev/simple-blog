package ee.fakeplastictrees.blog.feed.controller;

import ee.fakeplastictrees.blog.feed.service.FeedService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {
  private final FeedService feedService;

  public FeedController(FeedService feedService) {
    this.feedService = feedService;
  }

  @GetMapping("/feed")
  public String getSocialFeed(Model model) {
    var posts = feedService.getMastodonPosts();
    model.addAttribute("posts", posts);

    return "feed/index";
  }
}
