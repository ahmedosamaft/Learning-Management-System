package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends AbstractEntityRepository<Attendance> {
    // Find attendance records by student
    List<Attendance> findAllByStudent(Student student);

    List<Attendance> findByLesson(Lesson lesson);

    List<Attendance> findByStudentAndLesson(Student student, Lesson lesson);

}
