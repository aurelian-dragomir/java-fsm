package com.dragomir.fsm.v.with.pipeline;

public interface Step<I, O> {
    O compute(I input);
}
