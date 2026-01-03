package ee.fakeplastictrees.blog.file.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.file.model.FileEditorDto;
import ee.fakeplastictrees.blog.file.model.FilePageDto;
import ee.fakeplastictrees.blog.file.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
  public String getFiles(
      @PathVariable(required = false) Integer pageNumber,
      @RequestParam(required = false) String q,
      Model model) {
    pageNumber = pageNumber == null ? 1 : pageNumber;

    FilePageDto page;
    if (q == null || q.isBlank()) {
      page = fileService.getEditorFiles(pageNumber);
    } else {
      page = fileService.getEditorFilesByName(q.trim(), pageNumber);
    }

    model.addAttribute("page", page);
    model.addAttribute("q", q);

    return "admin/file/file_list";
  }

  @PostMapping("/files")
  @ProtectedRoute
  public String uploadFile(MultipartFile file) {
    var fileId = fileService.saveFile(file);

    return "redirect:/admin/files?newFileId=" + fileId;
  }

  @GetMapping("/file/{fileId}")
  @ProtectedRoute
  public String getFileById(@PathVariable String fileId, Model model) {
    var file = fileService.getFile(fileId);
    model.addAttribute("file", file);

    return "admin/file/file_edit";
  }

  @PostMapping("/file/{fileId}")
  @ProtectedRoute
  public String updateFileById(
      @ModelAttribute FileEditorDto fileEditorDto, @PathVariable String fileId, Model model) {
    fileService.updateFile(fileEditorDto);

    return "redirect:/admin/files";
  }

  @PostMapping("/file/{fileId}/delete")
  @ProtectedRoute
  public String deleteFileById(@PathVariable String fileId) {
    fileService.deleteFile(fileId);

    return "redirect:/admin/files";
  }
}
