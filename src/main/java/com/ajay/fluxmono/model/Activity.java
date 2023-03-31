package com.ajay.fluxmono.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    String activity ;
    String type;
    String participants;
    int price;
    String link;
    String key;
    Double accessibility;


}
