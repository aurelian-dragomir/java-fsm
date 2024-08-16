package com.dragomir.fsm.example;

import com.dragomir.fsm.example.service.TransactionService;
import com.dragomir.fsm.state.TransactionState;
import com.dragomir.fsm.v.with.pipeline.Pipeline;
import com.dragomir.fsm.v.with.pipeline.Step;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
public class PipelineMockTest {

    @SpyBean
    private TransactionService transactionService;

    @Autowired
    private List<Step> steps;

    @Test
    public void testWithBadStateTransition() {
        var t = new Transaction(1L, TransactionState.NEW);

        doReturn(new Transaction(1L,
                TransactionState.WAITING_APPROVAL))
                .when(transactionService).changeState(any(), eq(TransactionState.APPROVED));

        Pipeline.<Transaction, Transaction>of(steps).execute(t);
    }
}
