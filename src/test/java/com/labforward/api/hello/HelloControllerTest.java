package com.labforward.api.hello;

import com.labforward.api.common.MVCIntegrationTest;
import com.labforward.api.core.GlobalControllerAdvice;
import com.labforward.api.hello.domain.Greeting;
import com.labforward.api.hello.service.HelloWorldService;
import java.util.UUID;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloControllerTest extends MVCIntegrationTest {

    private static final String HELLO_LUKE = "Hello Luke";
    private static final String HELLO_MARY = "Hello Mary";
    
    static final Logger LOG = Logger.getLogger(HelloControllerTest.class.getName());

    /* ***** Tests for Get method ***** */
    @Test
    public void getHelloIsOKAndReturnsValidJSON() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(HelloWorldService.DEFAULT_ID)))
                .andExpect(jsonPath("$.message", is(HelloWorldService.DEFAULT_MESSAGE)));
    }

    /* ***** Tests for Create method ***** */
    @Test
    public void createReturnsBadRequestWhenMessageMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(post("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void createReturnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
        final String body = "{ \"tacos\":\"value\" }}";
        mockMvc.perform(post("/hello").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", containsString(GlobalControllerAdvice.BAD_REQUEST)));
    }

    @Test
    public void createReturnsBadRequestWhenMessageEmptyString() throws Exception {
        Greeting emptyMessage = new Greeting("");
        final String body = getGreetingBody(emptyMessage);

        mockMvc.perform(post("/hello").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void createOKWhenRequiredGreetingProvided() throws Exception {
        Greeting hello = new Greeting(HELLO_LUKE);
        final String body = getGreetingBody(hello);

        mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(hello.getMessage())));
    }

    @Test
    public void updateReturnsBadRequestWhenMessageMissing() throws Exception {
        String body = "{}";
        mockMvc.perform(put("/hello/update").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void updateReturnsBadRequestWhenUnexpectedAttributeProvided() throws Exception {
        final String body = "{ \"tacos\":\"value\" }}";
        mockMvc.perform(put("/hello/update").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.message", containsString(GlobalControllerAdvice.BAD_REQUEST)));
    }

    @Test
    public void updateReturnsBadRequestWhenMessageEmptyString() throws Exception {
        Greeting emptyMessage = new Greeting("");
        final String body = getGreetingBody(emptyMessage);

        mockMvc.perform(put("/hello/update").content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.validationErrors", hasSize(1)))
                .andExpect(jsonPath("$.validationErrors[*].field", contains("message")));
    }

    @Test
    public void updateOKWhenRequiredGreetingProvided() throws Exception {
        String id = UUID.randomUUID().toString();
        Greeting oldGreeting = new Greeting(id, HELLO_LUKE);
        Greeting newGreeting = new Greeting(id, HELLO_MARY);

        final String oldBody = getGreetingBody(oldGreeting);
        final String newBody = getGreetingBody(newGreeting);
        
        mockMvc.perform(post("/hello").contentType(MediaType.APPLICATION_JSON)
                .content(oldBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(oldGreeting.getMessage())));

        mockMvc.perform(put("/hello/update").contentType(MediaType.APPLICATION_JSON)
                .content(newBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is(newGreeting.getMessage())));
    }

    private String getGreetingBody(Greeting greeting) throws JSONException {
        JSONObject json = new JSONObject().put("message", greeting.getMessage());

        if (greeting.getId() != null) {
            json.put("id", greeting.getId());
        }

        return json.toString();
    }
}
