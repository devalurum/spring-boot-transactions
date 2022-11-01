package org.devalurum.transactionsapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureTestDatabase
public class TransactionsApplicationTests {

    @Autowired
    private TransactionsApplication application;

    @Test
    public void contextLoads() {
        assertThat(application).isNotNull();
    }

}
