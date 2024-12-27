package fci.swe.advanced_software.controllers.course;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.MediaService;
import fci.swe.advanced_software.services.courses.ResourceType;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Constants.API_VERSION + "/media")
@AllArgsConstructor

public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        return mediaService.uploadFile("ef2fbdad-1b7a-4c34-8a19-a937563ce697", ResourceType.LESSON, file);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) {
        return mediaService.getMedia(id);
    }

}
