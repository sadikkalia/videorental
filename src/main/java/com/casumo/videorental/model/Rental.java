package com.casumo.videorental.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Data
@Getter
@Setter
public class Rental {

    private String customerId;
    private Long timestamp;
    private Map<String, Integer> filmsDaysOfRenting;
}
