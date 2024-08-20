package com.dragomir.fsm.pipeline;

import com.dragomir.fsm.pipeline.step.Step;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.dragomir.fsm.state.TransactionState.NEW;

@RequiredArgsConstructor
public class Pipeline<I, O> {
    private final Step<I, O> currentStep;

    public <NewO> Pipeline<I, NewO> andThen(Step<O, NewO> nextStep) {
        return new Pipeline<>(new Step<I, NewO>() {
            @Override
            public NewO compute(I input) {
                return nextStep.compute(currentStep.compute(input));
            }

            @Override
            public TransactionState getCurrentState() {
                return nextStep.getCurrentState();
            }

            @Override
            public TransactionState getNextState() {
                return nextStep.getNextState();
            }
        });
    }

    public O execute(I input) {
        return currentStep.compute(input);
    }

    public static <I, O> Pipeline<I, O> of(List<Step> steps) {
        return of(steps, NEW);
    }

    public static <I, O> Pipeline<I, O> of(List<Step> steps, TransactionState state) {
        int fromIndex = -1;
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getCurrentState() == state) {
                fromIndex = i;
                break;
            }
        }

        if (fromIndex == -1) {
            throw new RuntimeException(String.format("No step found for state %s", state));
        }

        var p = new Pipeline<I, O>(steps.get(fromIndex));
        for (int i = fromIndex + 1; i < steps.size(); i++) {
            p = p.andThen(steps.get(i));
        }
        return p;
    }
}
