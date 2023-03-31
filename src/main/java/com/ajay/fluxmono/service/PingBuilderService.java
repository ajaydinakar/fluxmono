package com.ajay.fluxmono.service;

import com.ajay.fluxmono.model.Activity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.naming.CommunicationException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@Slf4j
public class PingBuilderService {

    private final WebClient.Builder webclientBuilder;
    private final String baseUrl;
    private ExchangeFilterFunction exchangeFilterFunction;
     @Autowired
    public PingBuilderService(WebClient.Builder webclientBuilder,@Value("${baseurl}") String baseUrl) {

        this.webclientBuilder = webclientBuilder;
        this.baseUrl = baseUrl;
    }


    public Mono<Activity> getMono()
    {
      return webclientBuilder
              .clone()
              .baseUrl(baseUrl)
              .build()
              .get()
              .retrieve()
              .onStatus(HttpStatus::is4xxClientError,clientResponse -> Mono.empty())
              .bodyToMono(Activity.class);

    }
    public Mono<List<Activity>>getFlux()
    {
        return webclientBuilder
                .clone()
                .baseUrl(baseUrl)
                .build()
                .get()
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,clientResponse -> Mono.empty())
                .onStatus(HttpStatus::is5xxServerError,clientResponse -> {
                    String errorM="get address"+clientResponse.statusCode().toString();
                    log.error(errorM);
                    return Mono.error(new CommunicationException(errorM));
                }
                )
                .bodyToFlux(Activity.class)
                .collectList()
                .retry(3)
                .doOnNext(activities -> {log.info("hai");});


    }



}
