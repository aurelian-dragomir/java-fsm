package com.dragomir.fsm.v.with.pipeline;

public class PadWithStarsStep implements Step<String, String> {
    @Override
    public String compute(String input) {
        return "* " + input + " *";
    }
}
