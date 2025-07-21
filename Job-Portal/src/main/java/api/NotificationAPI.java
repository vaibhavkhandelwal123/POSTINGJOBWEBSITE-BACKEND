package api;

import dto.ResponseDTO;
import entity.Notification;
import exception.JobPortalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import service.NotificationService;

import java.util.List;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/notification")
public class NotificationAPI {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/get/{userId}")
    public ResponseEntity<List<Notification>>getNotification(@PathVariable Long userId) throws JobPortalException {
        return new ResponseEntity<>(notificationService.getUnreadNotifications(userId), HttpStatus.OK);
    }

    @PutMapping("/read/{id}")
    public ResponseEntity<ResponseDTO>readNotification(@PathVariable Long id){
        notificationService.readNotifications(id);
        return new ResponseEntity<>(new ResponseDTO("Notification Read Successfully"), HttpStatus.OK);
    }

}
