package com.dragomir.fsm.pipeline.step;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import com.dragomir.fsm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
@RequiredArgsConstructor
public class WaitingApprovalStep extends Step<Transaction, Transaction> {
    private final TransactionService transactionService;

    @Override
    public Transaction compute(Transaction input) {

        //update in db
        Transaction t = transactionService.changeState(input, TransactionState.WAITING_APPROVAL);
        log.info("Executed step WaitingApprovalStep");
        return t;
    }

    @Override
    public TransactionState getCurrentState() {
        return TransactionState.NEW;
    }

    @Override
    public TransactionState getNextState() {
        return TransactionState.WAITING_APPROVAL;
    }

}
