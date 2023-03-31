package com.ajay.fluxmono.service;

import com.ajay.fluxmono.model.Activity;
import com.ajay.fluxmono.util.CustomMinimalForTestResponseSpec;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PingBuilderServiceTest {
PingBuilderService pingBuilderService;

@Mock
private WebClient webClientmock;
    @Mock
    private WebClient.Builder webClientBuilderMock;
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersMock;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodyMock;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriMock;
    @Mock
    private CustomMinimalForTestResponseSpec responseMock;


    String baseUrl="/api/activity";
    public static MockWebServer mockBackEnd;
    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @BeforeEach
    void init() {
        String baseUrl = String.format("http://localhost:%s",
                mockBackEnd.getPort());
        pingBuilderService = new PingBuilderService(webClientBuilderMock, baseUrl);
    }

    @AfterEach
    void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }
    @Test
    void getBuilderService() throws JsonProcessingException {
        Activity mockActivity = Activity.builder().activity("Create a cookbook with your favorite recipes")
                .type("Adam")
                .participants("cooking")
                .price(1)
                .link("")
                .key("1947449")
                .accessibility(0.05)
                .build();

        when(webClientBuilderMock.clone()).thenReturn(webClientBuilderMock);
        //  when(requestHeadersUriMock.uri("/api/activity")).thenReturn(requestHeadersMock);
        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientmock);
        when(webClientmock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToMono(Activity.class)).thenReturn(Mono.just(mockActivity));
        when(responseMock.onStatus(any(Predicate.class),any(Function.class))).thenReturn(responseMock);
        ObjectMapper objectMapper=new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockActivity))
                .addHeader("Content-Type", "application/json"));

        Mono<Activity> activityMono = pingBuilderService.getMono();

        StepVerifier.create(activityMono)
                .expectNextMatches(activity -> activity.getKey()
                        .equals("1947449"))
                .verifyComplete();

    }
    @Test
    void getBuilderService2() throws JsonProcessingException {
        Activity mockActivity = Activity.builder().activity("Create a cookbook with your favorite recipes")
                .type("Adam")
                .participants("cooking")
                .price(1)
                .link("")
                .key("1947449")
                .accessibility(0.05)
                .build();
List<Activity> activities=new ArrayList<>();
activities.add(mockActivity);
        activities.add(mockActivity);
        when(webClientBuilderMock.clone()).thenReturn(webClientBuilderMock);
//        when(requestHeadersUriMock.uri("/api/activity")).thenReturn(requestHeadersMock);
        when(webClientBuilderMock.baseUrl(anyString())).thenReturn(webClientBuilderMock);
        when(webClientBuilderMock.build()).thenReturn(webClientmock);
        when(webClientmock.get()).thenReturn(requestHeadersUriMock);
        when(requestHeadersUriMock.retrieve()).thenReturn(responseMock);
        when(responseMock.bodyToFlux(Activity.class)).thenReturn(Flux.just(mockActivity));
        when(responseMock.onStatus(any(Predicate.class),any(Function.class))).thenReturn(responseMock);


        ObjectMapper objectMapper=new ObjectMapper();
        mockBackEnd.enqueue(new MockResponse()
                .setBody(objectMapper.writeValueAsString(mockActivity))
                .addHeader("Content-Type", "application/json"));

        Mono<List<Activity> >activityMono = pingBuilderService.getFlux();

        StepVerifier.create(activityMono)
                .expectNextMatches(activity -> activity.get(0).getKey()
                        .equals("1947449"))
                .verifyComplete();

    }

}