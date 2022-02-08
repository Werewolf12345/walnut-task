package com.alexeiboriskin.walnuttask.controllers;

import com.alexeiboriskin.walnuttask.domain.PingResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/api")
public class PingController {

    @GetMapping("/ping")
    public PingResponse getPing() {
        return new PingResponse();
    }
}
