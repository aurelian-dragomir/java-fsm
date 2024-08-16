package com.dragomir.fsm.step;

public interface Step<I, O> {
    O compute(I input);
}
