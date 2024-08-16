package com.dragomir.fsm.v.just.step;

public class DoubleStep implements Step<Integer, Integer> {
    @Override
    public Integer compute(Integer input) {
        return input * 2;
    }
}
