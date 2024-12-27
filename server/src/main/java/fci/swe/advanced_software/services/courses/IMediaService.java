package fci.swe.advanced_software.services.courses;

import fci.swe.advanced_software.dtos.Response;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IMediaService {
    ResponseEntity<Resource> getMedia(String id);
    ResponseEntity<Response> uploadFile(String resourceId, ResourceType resourceType, MultipartFile file);
    ResponseEntity<Response> deleteFile(String id);
}
