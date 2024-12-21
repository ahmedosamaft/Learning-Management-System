package fci.swe.advanced_software.utils.mappers.courses;

import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementResponseDto;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class AnnouncementMapper {

    protected CourseRepository courseRepository;
    protected AbstractUserRepository<AbstractUser> userRepository;

    @Mapping(target = "course", source = "courseId", qualifiedByName = "courseDtoToCourse")
    @Mapping(target = "postedBy", source = "userId", qualifiedByName = "userDtoToUser")
    public abstract Announcement toEntity(AnnouncementRequestDto requestDto);

    @Mapping(target = "courseId", source = "course.id")
    @Mapping(target = "author", source = "postedBy")
    @Mapping(target = "comments", source = "comments")
    public abstract AnnouncementResponseDto toResponseDto(Announcement announcement);

    @Named("courseDtoToCourse")
    public Course courseDtoToCourse(String courseId) {
        if (courseId == null) {
            return null;
        }
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid course id" + courseId));
    }

    @Named("userDtoToUser")
    public AbstractUser userDtoToUser(String userId) {
        if (userId == null) {
            return null;
        }
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user id"));
    }

    @Autowired
    protected void setCourseRepository(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Autowired
    protected void setUserRepository(AbstractUserRepository<AbstractUser> userRepository) {
        this.userRepository = userRepository;
    }
}
