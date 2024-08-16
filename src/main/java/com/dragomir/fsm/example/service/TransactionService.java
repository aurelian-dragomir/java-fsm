package com.dragomir.fsm.example.service;

import com.dragomir.fsm.example.Transaction;
import com.dragomir.fsm.state.TransactionState;
import org.springframework.stereotype.Service;

import java.util.Map;

import static com.dragomir.fsm.state.TransactionState.*;

@Service
public class TransactionService {
    private static final Map<TransactionState, TransactionState> STATE_MAP =
            Map.of(NEW, WAITING_APPROVAL,
                    WAITING_APPROVAL, APPROVED,
                    APPROVED, APPLIED);

    public Transaction changeState(Transaction tx, TransactionState state) {
        if (STATE_MAP.get(tx.getState()) != state) {
            throw new RuntimeException(String.format("Can't go from state %s to %s!",
                    tx.getState(), state));
        }
        tx.setState(state);
        //call TransactionRepository for actual save in the database
        return tx;
    }
}
