package service;

import dto.NotificationDTO;
import dto.NotificationStatus;
import dto.ResponseDTO;
import entity.Notification;
import exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.NotificationRepository;
import utility.Utilities;

import java.time.LocalDateTime;
import java.util.List;

@Service("notificationService")
public class NotificationServiceImplementation implements NotificationService{
    @Autowired
    private NotificationRepository notificationRepository;


    @Override
    public void sendNotification(NotificationDTO notificationDTO) throws Exception {
        notificationDTO.setId(Utilities.getNextSequence("notification"));
        notificationDTO.setStatus(NotificationStatus.UNREAD);
        notificationDTO.setTimeStamp(LocalDateTime.now());
        notificationRepository.save(notificationDTO.toEntity());
    }

    @Override
    public List<Notification> getUnreadNotifications(Long userId) {
        return notificationRepository.findByUserIdAndStatus(userId, NotificationStatus.UNREAD);
    }

    @Override
    public void readNotifications(Long id) throws JobPortalException {
        Notification noti = notificationRepository.findById(id).orElseThrow(() -> new JobPortalException("No Notification Found"));
        noti.setStatus(NotificationStatus.READ);
        notificationRepository.save(noti);
    }
}
