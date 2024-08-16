package com.dragomir.fsm.example;

import com.dragomir.fsm.state.TransactionState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long id;
    private TransactionState state;
}
