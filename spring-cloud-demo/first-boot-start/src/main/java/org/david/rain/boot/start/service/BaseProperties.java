package org.david.rain.boot.start.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BaseProperties {

    @Value("${org.david.rain.properties.test.value}")
    private String randomValue;


    public String getRandomValue() {
        return randomValue;
    }

    public void setRandomValue(String randomValue) {
        this.randomValue = randomValue;
    }


}
