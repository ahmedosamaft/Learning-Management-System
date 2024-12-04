package fci.swe.advanced_software.controllers;

import fci.swe.advanced_software.dtos.StudentDto;
import fci.swe.advanced_software.models.Notification;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.NotificationRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentController {
    private StudentRepository studentRepository;
    private NotificationRepository notificationRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable String id) {
        return studentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/notifications/unread")
    public ResponseEntity<Set<Notification>> getStudentNotifications(@PathVariable String id) {
        return ResponseEntity.ok(notificationRepository.findAllByRecipientIdAndIsReadFalse(id));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody StudentDto dto) {
        Student student = Student.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        Student savedStudent = studentRepository.save(student);
        return ResponseEntity.ok(savedStudent);
    }
}
