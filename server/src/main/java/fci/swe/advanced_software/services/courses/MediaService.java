package fci.swe.advanced_software.services.courses;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.courses.Media;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.QuestionRepository;
import fci.swe.advanced_software.repositories.course.AnnouncementRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.repositories.course.MediaRepository;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.MediaMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MediaService implements IMediaService {
    private final LessonRepository lessonRepository;
    private final AssessmentRepository assessmentRepository;
    private final AnnouncementRepository announcementRepository;
    private final QuestionRepository questionRepository;
    private final MediaRepository mediaRepository;
    private final MediaMapper mediaMapper;
    @Value("${media.upload-dir}")
    private String LOCATION;
    private Path rootLocation;
    @Value("${media.max-file-size:5242880}")
    int MAX_FILE_SIZE;
    @Value("#{'${media.supported-types}'.split(',')}")
    private List<String> SUPPORTED_TYPES;

    @Override
    public ResponseEntity<Resource> getMedia(String id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

        Path filePath = Path.of(media.getUrl());
        if (!Files.exists(filePath)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found on server");
        }

        try {
            Resource fileResource = new UrlResource(filePath.toUri());

            if (!fileResource.exists() || !fileResource.isReadable()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File is not readable");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + media.getRealName() + "\"")
                    .body(fileResource);

        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error loading file", e);
        }
    }

    @Override
    public ResponseEntity<Response> uploadFile(String resourceId, ResourceType resourceType, MultipartFile file) {
        try {
            if (file.getSize() > MAX_FILE_SIZE) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File size exceeds the allowed limit");
            }

            String contentType = file.getContentType();
            if (contentType == null || !SUPPORTED_TYPES.contains(contentType.toLowerCase())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid file type. Supported types are: " + SUPPORTED_TYPES);
            }

            String filename = file.getOriginalFilename();
            if (filename == null || filename.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File name is invalid");
            }

            String uniqueFilename = System.currentTimeMillis() + "_" + filename;
            Path destinationFile = rootLocation.resolve(uniqueFilename).normalize();
            Files.copy(file.getInputStream(), destinationFile);

            var mediaBuilder = Media.builder()
                    .realName(filename)
                    .url(destinationFile.toString());

            switch (resourceType) {
                case LESSON -> {
                    Lesson lesson = lessonRepository.findById(resourceId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
                    mediaBuilder.lesson(lesson);
                }
                case ASSESSMENT -> {
                    Assessment assessment = assessmentRepository.findById(resourceId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));
                    mediaBuilder.assessment(assessment);
                }
                case ANNOUNCEMENT -> {
                    Announcement announcement = announcementRepository.findById(resourceId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Announcement not found"));
                    mediaBuilder.announcement(announcement);
                }
                case QUESTION -> {
                    Question question = questionRepository.findById(resourceId)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
                    mediaBuilder.question(question);
                }
            }

            Media media = mediaBuilder.build();

            media = mediaRepository.save(media);

            return ResponseEntityBuilder.create()
                    .withMessage("Ok")
                    .withData("media", mediaMapper.toDto(media))
                    .build();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed!");
        }
    }

    @Override
    public ResponseEntity<Response> deleteFile(String id) {
        Media media = mediaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Media not found"));

        Path filePath = Path.of(media.getUrl());
        try {
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete file", e);
        }

        mediaRepository.delete(media);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .build();
    }

    @PostConstruct
    private void init() {
        rootLocation = Path.of(LOCATION);
        try {
            if (!rootLocation.toFile().exists()) {
                Files.createDirectories(rootLocation);
            }
        } catch (Exception e) {
            log.error("Failed to create directory at {}", LOCATION, e);
        }
    }
}
