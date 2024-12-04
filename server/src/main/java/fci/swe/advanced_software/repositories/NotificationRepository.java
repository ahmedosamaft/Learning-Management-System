package fci.swe.advanced_software.repositories;

import fci.swe.advanced_software.models.Notification;

import java.util.Set;

public interface NotificationRepository extends AbstractEntityRepository<Notification> {
    Set<Notification> findAllByRecipientId(String recipientId);
    Set<Notification> findAllByRecipientIdAndIsReadFalse(String recipientId);
}
