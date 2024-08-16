package com.dragomir.fsm.example;

import com.dragomir.fsm.state.TransactionState;
import com.dragomir.fsm.v.with.pipeline.Pipeline;
import com.dragomir.fsm.v.with.pipeline.Step;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PipelineTest {

    @Autowired
    private List<Step> steps;

    @Test
    public void runFullPipeline() throws Exception {
        var t = new Transaction(1L, TransactionState.NEW);
        var p = Pipeline.<Transaction, Transaction>of(steps);
        var tran = p.execute(t);
        assertTrue(tran.getState() == TransactionState.APPLIED);
    }

    @Test
    public void retryPipelineFromStep() throws Exception {
        var t = new Transaction(1L, TransactionState.WAITING_APPROVAL);
        int i = t.getState().ordinal();
        var p = Pipeline.<Transaction, Transaction>of(steps, i);
        var tran = p.execute(t);
        assertTrue(tran.getState() == TransactionState.APPLIED);
    }
}
