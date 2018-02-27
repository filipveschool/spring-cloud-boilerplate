package com.filip.licensingservice.controllers;

import com.filip.licensingservice.services.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/tools")
public class ToolsController {

    @Autowired
    private DiscoveryService discoveryService;

    @GetMapping("/eureka/services")
    public List<String> getEurekaServices(){
        return discoveryService.getEurekaServices();
    }

}
