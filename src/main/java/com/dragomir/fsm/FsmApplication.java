package com.dragomir.fsm;

import com.dragomir.fsm.v.with.pipeline.Pipeline;
import com.dragomir.fsm.v.with.pipeline.Step;
import com.dragomir.fsm.example.Transaction;
import com.dragomir.fsm.state.TransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
//@EnableScheduling
public class FsmApplication {

    public static void main(String[] args) {
        SpringApplication.run(FsmApplication.class, args);
    }

    @Autowired
    private List<Step> steps;

    @Bean
    public CommandLineRunner runAtStartup() {
        return args -> {
            var t = new Transaction(2L, TransactionState.WAITING_APPROVAL);
            var subSteps = steps.subList(t.getState().ordinal(), steps.size());
            var p = Pipeline.of(subSteps);
            System.out.println(p.execute(t));
        };
    }
}
