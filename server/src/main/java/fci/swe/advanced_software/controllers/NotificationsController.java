package fci.swe.advanced_software.controllers;

import fci.swe.advanced_software.dtos.NotificationRequestDto;
import fci.swe.advanced_software.services.INotificationsService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_VERSION + "/notifications")
@RequiredArgsConstructor
public class NotificationsController {
    private final INotificationsService notificationsService;

    @GetMapping
    public ResponseEntity<?> getNotifications(@RequestParam(required = false, defaultValue = "all") @Pattern(regexp = "all|unread") String filterBy,
                                             @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                             @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return notificationsService.getNotifications(filterBy, page, size);
    }

    @PostMapping
    public ResponseEntity<?> markAsRead(@Valid @RequestBody List<NotificationRequestDto> notifications) {
        return notificationsService.markAsRead(notifications);
    }
}
