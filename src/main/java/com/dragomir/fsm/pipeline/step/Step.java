package com.dragomir.fsm.pipeline.step;

import com.dragomir.fsm.state.TransactionState;

public abstract class Step<I, O> {
    public abstract O compute(I input);

    public abstract TransactionState getCurrentState();

    public abstract TransactionState getNextState();
}
