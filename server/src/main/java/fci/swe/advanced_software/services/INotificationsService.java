package fci.swe.advanced_software.services;

import fci.swe.advanced_software.dtos.NotificationRequestDto;
import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.models.users.Role;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface INotificationsService {
    ResponseEntity<Response> getNotifications(String filter, Integer page, Integer size);
    void broadcastNotification(String title, String message, String courseId, Role role);
    void sendNotification(String title, String message, String courseId, String userId);

    ResponseEntity<Response> markAsRead(List<NotificationRequestDto> notifications);
}
