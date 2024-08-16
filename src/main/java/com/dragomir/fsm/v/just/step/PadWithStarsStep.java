package com.dragomir.fsm.v.just.step;

public class PadWithStarsStep implements Step<String, String> {
    @Override
    public String compute(String input) {
        return "* " + input + " *";
    }
}
