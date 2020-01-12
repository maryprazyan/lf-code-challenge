package com.labforward.api.hello;

import com.labforward.api.core.exception.EntityValidationException;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloWorldServiceTest {

    private static final String HELLO_LUKE = "Hello Luke";
    private static final String HELLO_MARY = "Hello Mary";

    @Autowired
    private HelloWorldService helloService;

    public HelloWorldServiceTest() {
    }

    /* ***** Tests for Get method ***** */
    @Test
    public void getDefaultGreetingIsOK() {
        Optional<Greeting> greeting = helloService.getDefaultGreeting();
        Assert.assertTrue(greeting.isPresent());
        Assert.assertEquals(HelloWorldService.DEFAULT_ID, greeting.get().getId());
        Assert.assertEquals(HelloWorldService.DEFAULT_MESSAGE, greeting.get().getMessage());
    }

    /* ***** Tests for Create method ***** */
    @Test(expected = EntityValidationException.class)
    public void createGreetingWithEmptyMessageThrowsException() {
        final String EMPTY_MESSAGE = "";
        helloService.createGreeting(new Greeting(EMPTY_MESSAGE));
    }

    @Test(expected = EntityValidationException.class)
    public void createGreetingWithNullMessageThrowsException() {
        helloService.createGreeting(new Greeting(null));
    }

    @Test
    public void createGreetingOKWhenValidRequest() {
        Greeting request = new Greeting(HELLO_LUKE);

        Greeting created = helloService.createGreeting(request);
        Assert.assertEquals(HELLO_LUKE, created.getMessage());
    }

    /* ***** Tests for Update method ***** */
    @Test(expected = EntityValidationException.class)
    public void updateGreetingWithEmptyMessageThrowsException() {
        final String EMPTY_MESSAGE = "";
        helloService.updateGreeting(new Greeting(EMPTY_MESSAGE));
    }

    @Test(expected = EntityValidationException.class)
    public void updateGreetingWithNullMessageThrowsException() {
        helloService.updateGreeting(new Greeting(null));
    }

    @Test
    public void updateGreetingOKWhenValidRequest() {
        Greeting request = new Greeting(HELLO_LUKE);

        Greeting created = helloService.createGreeting(request);
        Assert.assertEquals(HELLO_LUKE, created.getMessage());

        String id = created.getId();
        Greeting newRequest = new Greeting(id, HELLO_MARY);
        Optional<Greeting> updated = helloService.updateGreeting(newRequest);
        
        Assert.assertTrue(updated.isPresent());
        Assert.assertEquals(HELLO_MARY, updated.get().getMessage());
        
    }
}
