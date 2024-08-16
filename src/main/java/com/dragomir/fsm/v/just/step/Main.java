package com.dragomir.fsm.v.just.step;

public class Main {
    public static void main(String[] args) {
        System.out.println(new DoubleStep()
                .andThen(new ConvertToStringStep())
                .andThen(new PadWithStarsStep())
                .compute(10));
    }
}
