package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.AnnouncementResponseDto;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.AnnouncementComment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.AdminRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AnnouncementMapper {

    protected CourseRepository courseRepository;
    protected AdminRepository adminRepository;
    protected InstructorRepository instructorRepository;

    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseDtoToCourse")
    @Mapping(target = "postedBy", source = "postedByUserId", qualifiedByName = "userDtoToUser")
    public abstract Announcement toEntity(AnnouncementRequestDto requestDto);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "postedByUserId", source = "postedBy.id")
    @Mapping(target="commentIds",source="comments")
    public abstract AnnouncementResponseDto toResponseDto(Announcement announcement);

    @Named("courseDtoToCourse")
    public Course courseDtoToCourse(String courseId) {
        if (courseId == null) {
            return null;
        }
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + courseId));
    }
    public List<String> mapComments(List<AnnouncementComment> comments) {
        if (comments == null) {
            return null;
        }
        return comments.stream().map(comment -> comment.getId()).toList();
    }

    @Named("userDtoToUser")
    public AbstractUser userDtoToUser(String userId) {
        if (userId == null) {
            return null;
        }
        if (userId.startsWith("admin")) {
            return adminRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid admin ID: " + userId));
        } else if (userId.startsWith("instructor")) {
            return instructorRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid instructor ID: " + userId));
        } else {
            throw new IllegalArgumentException("Invalid user type for ID: " + userId);
        }
    }

    @Autowired
    protected void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    @Qualifier("adminRepository")
    protected void setAdminRepository(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Autowired
    @Qualifier("instructorRepository")
    protected void setInstructorRepository(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }
}
