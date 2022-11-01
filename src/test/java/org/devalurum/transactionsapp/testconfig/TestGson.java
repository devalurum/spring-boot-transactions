package org.devalurum.transactionsapp.testconfig;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestGson {

    @Bean
    public Gson getGson() {
        return new GsonBuilder().serializeNulls().create();
    }
}
