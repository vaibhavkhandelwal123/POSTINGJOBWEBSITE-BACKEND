package service;

import dto.NotificationDTO;
import dto.ResponseDTO;
import entity.Notification;
import exception.JobPortalException;

import java.util.List;

public interface NotificationService {
    public void sendNotification(NotificationDTO notificationDTO) throws Exception;
    public List<Notification> getUnreadNotifications(Long userId);

    public void readNotifications(Long id) throws JobPortalException;
}
