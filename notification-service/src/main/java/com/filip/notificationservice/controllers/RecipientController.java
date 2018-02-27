package com.filip.notificationservice.controllers;

import com.filip.notificationservice.model.Recipient;
import com.filip.notificationservice.service.RecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping(value = "/recipients")
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    @GetMapping(value = "/current")
    public Object getCurrentNotificationSettings(Principal principal) {
        return recipientService.findByAccountName(principal.getName());
    }

    @PutMapping(value = "/current")
    public Object saveCurrentNotificationSettings(Principal principal, @Valid @RequestBody Recipient recipient) {
        return recipientService.save(principal.getName(), recipient);
    }
}
