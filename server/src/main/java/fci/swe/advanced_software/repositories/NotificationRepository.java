package fci.swe.advanced_software.repositories;

import fci.swe.advanced_software.models.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface NotificationRepository extends AbstractEntityRepository<Notification> {
    Set<Notification> findAllByRecipientId(String recipientId);
    Set<Notification> findAllByRecipientIdAndIsReadFalse(String recipientId);

    Page<Notification> findByRecipientId(String userId, Pageable pageable);

    Page<Notification> findByRecipientIdAndIsReadFalse(String userId, Pageable pageable);
}
