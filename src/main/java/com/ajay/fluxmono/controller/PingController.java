package com.ajay.fluxmono.controller;


import com.ajay.fluxmono.model.Activity;
import com.ajay.fluxmono.service.PingBuilderService;
import com.ajay.fluxmono.service.PingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//@Controller
@RestController
public class PingController {

    @Autowired
    PingService service ;
    //alternative service impl using webclinet builder
//    @Autowired
//    PingBuilderService service2;

    @QueryMapping
    String getActivity()
    {
        return service.getActivity();
    }

    @QueryMapping
    Activity getActivityMonoGraph()
    {
        return service.getActivityObj();
    }





}
