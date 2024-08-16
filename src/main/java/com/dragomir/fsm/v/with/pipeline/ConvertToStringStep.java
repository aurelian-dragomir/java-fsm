package com.dragomir.fsm.v.with.pipeline;

public class ConvertToStringStep implements Step<Integer, String> {
    @Override
    public String compute(Integer input) {
        return Integer.toString(input);
    }
}
