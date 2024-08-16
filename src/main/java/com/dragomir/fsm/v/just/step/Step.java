package com.dragomir.fsm.v.just.step;

public interface Step<I, O> {
    O compute(I input);

    default <NewO> Step<I, NewO> andThen(Step<O, NewO> source) {
        return i -> source.compute(compute(i));
    }

    static <I, O> Step<I, O> of(Step<I, O> source) {
        return source;
    }
}
