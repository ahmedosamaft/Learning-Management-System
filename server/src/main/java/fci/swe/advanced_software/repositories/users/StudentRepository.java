package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.users.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends AbstractUserRepository<Student> {

    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId")
    List<Enrollment> findEnrollmentsByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT c FROM Course c " +
            "WHERE c.id IN (SELECT e.course.id FROM Enrollment e WHERE e.student.id = :studentId)")
    List<Course> findCoursesByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId")
    List<Attendance> findAttendanceByStudentId(@Param("studentId") Long studentId);

    @Query("SELECT a FROM Attendance a WHERE a.student.id = :studentId AND a.lesson.id = :lessonId")
    Optional<Attendance> findAttendanceForLesson(@Param("studentId") Long studentId, @Param("lessonId") Long lessonId);

    @Query("SELECT e.grade FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    Optional<String> findGradeForCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);

    @Query("SELECT e FROM Enrollment e WHERE e.student.id = :studentId AND e.course.id = :courseId")
    Optional<Enrollment> findEnrollmentForCourse(@Param("studentId") Long studentId, @Param("courseId") Long courseId);
}
