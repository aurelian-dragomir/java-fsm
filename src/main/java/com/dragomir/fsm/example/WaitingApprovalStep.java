package com.dragomir.fsm.example;

import com.dragomir.fsm.example.service.TransactionService;
import com.dragomir.fsm.v.with.pipeline.Step;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class WaitingApprovalStep implements Step<Transaction, Transaction> {
    private final TransactionService transactionService;

    @Override
    public Transaction compute(Transaction input) {

        //update in db
        Transaction t = transactionService.changeState(input, TransactionState.WAITING_APPROVAL);
        log.info("Executed step WaitingApprovalStep");
        return t;
    }

}
