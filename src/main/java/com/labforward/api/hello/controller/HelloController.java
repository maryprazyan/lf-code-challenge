package com.labforward.api.hello.controller;

import com.labforward.api.core.exception.ResourceNotFoundException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    public static final String GREETING_NOT_FOUND = "Greeting Not Found";

    private final HelloWorldService helloWorldService;

    public HelloController(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @GetMapping(path = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Greeting helloWorld() {
        return getHello(HelloWorldService.DEFAULT_ID);
    }

    @GetMapping(value = "/hello/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Greeting getHello(@PathVariable String id) {
        return helloWorldService.getGreeting(id)
                .orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
    }

    @PostMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    public Greeting createGreeting(@RequestBody Greeting request) {
        return helloWorldService.createGreeting(request);
    }

    @PutMapping(value = "/hello/update", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Greeting updatGreeting(@RequestBody Greeting request) {
        return helloWorldService.updateGreeting(request)
                .orElseThrow(() -> new ResourceNotFoundException(GREETING_NOT_FOUND));
    }
}
