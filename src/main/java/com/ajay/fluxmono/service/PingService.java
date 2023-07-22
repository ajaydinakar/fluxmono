package com.ajay.fluxmono.service;

import com.ajay.fluxmono.model.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PingService {
    WebClient webClient;
    @Autowired
    public PingService(@Value("${baseurl}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }
    public PingService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<Activity> getMono()
    {
        Mono<Activity> activityMono=webClient
                .get()
                .retrieve()
                .bodyToMono(Activity.class);
        return activityMono;
    }

    public Activity getActivityObj()
    {
        Mono<Activity> activityMono=webClient
                .get()
                .retrieve()
                .bodyToMono(Activity.class);
        Activity activity= activityMono.block();
        return activity;
    }
    public String getActivity()
    {
       // WebClient webClient=WebClient.create("https://www.boredapi.com/api/activity");
        Mono<Activity> activityMono=webClient
                .get()
                .retrieve()
                .bodyToMono(Activity.class);
        Activity activity= activityMono.block();

        return activity.getActivity();
    }
}
