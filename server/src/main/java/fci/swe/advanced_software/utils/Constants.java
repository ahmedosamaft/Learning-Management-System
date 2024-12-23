package fci.swe.advanced_software.utils;

public abstract class Constants {
    public static final String API_VERSION = "/api/v1";
    public static final Integer ID_LENGTH = 32;
    public static final String INSTRUCTOR_CONTROLLER = API_VERSION + "/instructors/courses";
    public static final String STUDENT_CONTROLLER = API_VERSION + "/student-courses/{courseId}";
}
