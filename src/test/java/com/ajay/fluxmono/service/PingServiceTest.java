package com.ajay.fluxmono.service;

import com.ajay.fluxmono.model.Activity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PingServiceTest {

    PingService service;
    @Mock
    private WebClient webClientMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private WebClient.ResponseSpec responseMock;

    public static MockWebServer mockBackEnd;
    @BeforeEach
    void setUp()  {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        service = new PingService(webClientMock);

    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeAll
    static void initialize() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();


    }
    @Test
    void getService() throws JsonProcessingException {

        Activity mockActivity = Activity.builder().activity("Create a cookbook with your favorite recipes")
                .type("Adam")
                .participants("cooking")
                .price(1)
                .link("")
                .key("1947449")
                .accessibility(0.05)
                .build();



        when(webClientMock.get()).thenReturn(requestHeadersUriMock);
      //  when(requestHeadersUriMock.uri("/api/activity")).thenReturn(requestHeadersMock);
        when(requestHeadersUriMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Activity.class)).thenReturn(Mono.just(mockActivity));
        ObjectMapper objectMapper=new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockActivity))
                .addHeader("Content-Type", "application/json"));

        Mono<Activity> activityMono = service.getMono();

        StepVerifier.create(activityMono)
                .expectNextMatches(activity -> activity.getKey()
                        .equals("1947449"))
                .verifyComplete();
    }

    @Test
    void getService1() {
        Flux<String> stringFlux = Flux.just("Hello", "Baeldung");
        StepVerifier.create(stringFlux)
                .expectNext("Hello")
                .expectNext("Baeldung")
                .expectComplete()
                .verify();
    }
    @Test
    public void getService2() {

        Mono<String> helloMono = Mono.just("Hello");
        StepVerifier.create(helloMono)
                .expectNext("Hello")
                .expectComplete()
                .verify();
    }
}