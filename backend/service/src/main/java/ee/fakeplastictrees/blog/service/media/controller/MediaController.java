package ee.fakeplastictrees.blog.service.media.controller;

import ee.fakeplastictrees.blog.codegen.api.MediaApi;
import ee.fakeplastictrees.blog.codegen.model.MediaPost200Response;
import ee.fakeplastictrees.blog.service.core.annotation.PostEditor;
import ee.fakeplastictrees.blog.service.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class MediaController implements MediaApi {
  @Autowired
  private MediaService mediaService;

  @Override
  @PostEditor
  public ResponseEntity<MediaPost200Response> mediaPost(MultipartFile file) {
    return ResponseEntity.ok(new MediaPost200Response().id(mediaService.save(file)));
  }

  @Override
  public ResponseEntity<Resource> mediaIdGet(String id) {
    var media = mediaService.get(id);

    return ResponseEntity.ok().header("Content-Type", media.getContentType()).body(media.getResource());
  }
}
