package com.dragomir.fsm.v.with.pipeline;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class Pipeline<I, O> {
    private final Step<I, O> currentStep;

    public <NewO> Pipeline<I, NewO> andThen(Step<O, NewO> nextStep) {
        return new Pipeline<>(input -> nextStep.compute(currentStep.compute(input)));
    }

    public O execute(I input) {
        return currentStep.compute(input);
    }

    public static <I, O> Pipeline<I, O> of(List<Step> steps) {
        return of(steps, 0);
    }

    public static <I, O> Pipeline<I, O> of(List<Step> steps, int fromStepIndex) {
        var p = new Pipeline<I, O>(steps.get(fromStepIndex));
        for (int i = fromStepIndex + 1; i < steps.size(); i++) {
            p = p.andThen(steps.get(i));
        }
        return p;
    }
}
