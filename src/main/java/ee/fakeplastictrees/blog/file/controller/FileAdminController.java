package ee.fakeplastictrees.blog.file.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.file.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin")
public class FileAdminController {
  private final FileService fileService;

  public FileAdminController(FileService fileService) {
    this.fileService = fileService;
  }

  @GetMapping({"/files", "/files/{pageNumber}"})
  @ProtectedRoute
  public String getFiles(@PathVariable(required = false) Integer pageNumber, Model model) {
    var page = fileService.getEditorFiles(pageNumber == null ? 1 : pageNumber);
    model.addAttribute("page", page);

    return "admin/file/file_list";
  }

  @PostMapping("/files")
  @ProtectedRoute
  public String uploadFile(MultipartFile file) {
    var fileId = fileService.saveFile(file);

    return "redirect:/admin/files?newFileId=" + fileId;
  }

  @PostMapping("/files/delete/{fileId}")
  @ProtectedRoute
  public String deleteFileById(@PathVariable String fileId) {
    fileService.deleteFromDisk(fileId);

    return "redirect:/admin/files";
  }
}
