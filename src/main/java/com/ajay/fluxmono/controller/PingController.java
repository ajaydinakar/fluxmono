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
    @Autowired
    PingBuilderService service2;
    @GetMapping("/ping")
    String pingme()
    {
     //  System.out.println(service.getService().toString());
        Mono<Activity> activityMono=service.getMono();
        Activity activity= activityMono.block();
        return activity.getActivity();
    }

    @GetMapping("/ping2")
    String pingme2()
    {
        //  System.out.println(service.getService().toString());
        Mono<Activity> activityMono=service2.getMono();
        Activity activity= activityMono.block();
        return activity.getActivity();
    }

    @GetMapping("/activity")
    String getActivity2()
    {
        return service.getActivity();
    }

    @GetMapping("/activityObject")
    Activity getActivityMono()
    {
       return  service.getActivityObj();
    }

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
