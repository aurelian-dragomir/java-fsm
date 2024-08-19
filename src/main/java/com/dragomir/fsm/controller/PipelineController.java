package com.dragomir.fsm.controller;

import com.dragomir.fsm.pipeline.Pipeline;
import com.dragomir.fsm.pipeline.step.Step;
import com.dragomir.fsm.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pipeline")
@RequiredArgsConstructor
public class PipelineController {

    private final TransactionService transactionService;
    private final List<Step> steps;

    @PostMapping
    public String send() {
        var tx = transactionService.createSampleTransaction();
        Pipeline.of(steps).execute(tx);
        return "OK";
    }
}
