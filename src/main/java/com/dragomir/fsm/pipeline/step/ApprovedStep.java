package com.dragomir.fsm.pipeline.step;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import com.dragomir.fsm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
@Slf4j
@RequiredArgsConstructor
public class ApprovedStep extends Step<Transaction, Transaction> {
    private final TransactionService transactionService;

    @Override
    public Transaction compute(Transaction input) {

        //update in db
        Transaction t = transactionService.changeState(input, TransactionState.APPROVED);
        log.info("Executed step ApprovedStep");
        return t;
    }

    @Override
    public TransactionState getCurrentState() {
        return TransactionState.WAITING_APPROVAL;
    }

    @Override
    public TransactionState getNextState() {
        return TransactionState.APPROVED;
    }
}