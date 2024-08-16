package com.dragomir.fsm.v.with.pipeline;

public class Main {
    public static void main(String[] args) throws Exception {
        var p = new Pipeline<>(new DoubleStep())
                .andThen(new ConvertToStringStep())
                .andThen(new PadWithStarsStep());

        String value = p.execute(10);
        System.out.println(value);

        var p2 = new Pipeline<>(new ConvertToStringStep())
                .andThen(new PadWithStarsStep());
        System.out.println(p2.execute(20));
    }
}
