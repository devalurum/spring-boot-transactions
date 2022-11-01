package org.devalurum.transactionsapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
public class TransactionsApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(TransactionsApplication.class).run(args);
    }
}
