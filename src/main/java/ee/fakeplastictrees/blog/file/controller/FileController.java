package ee.fakeplastictrees.blog.file.controller;

import ee.fakeplastictrees.blog.file.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/media")
public class FileController {
  private final FileService fileService;

  public FileController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping("/{fileId}")
  public ResponseEntity<Resource> getFileById(@PathVariable String fileId) {
    var resource = fileService.getFile(fileId);

    return ResponseEntity.ok().header("Content-Type", resource.contentType()).body(resource.resource());
  }
}
