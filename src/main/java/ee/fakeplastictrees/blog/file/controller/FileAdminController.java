package ee.fakeplastictrees.blog.file.controller;

import ee.fakeplastictrees.blog.core.annotation.ProtectedRoute;
import ee.fakeplastictrees.blog.file.model.FileEditorDto;
import ee.fakeplastictrees.blog.file.service.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
