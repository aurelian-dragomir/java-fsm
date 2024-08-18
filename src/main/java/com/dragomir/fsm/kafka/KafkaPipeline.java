package com.dragomir.fsm.kafka;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.service.TransactionService;
import com.dragomir.fsm.state.TransactionState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "${service1-topic}", groupId = "${service1-group-id}",
        concurrency = "${parallelism}", containerFactory = "containerFactory")
@RequiredArgsConstructor
@Slf4j
public class KafkaPipeline {

    @Value("${service2-topic}")
    private String service2Topic;

    private final KafkaTemplate<Void, Transaction> producer;
    private final TransactionService transactionService;

    public void processWaitingApprovalState(Transaction tx) {

        //update db state
        transactionService.changeState(tx, TransactionState.WAITING_APPROVAL);
        log.info("executed processWaitingApprovalState");

        //advanced to next state
        producer.sendDefault(tx);
    }

    public void processApprovedState(Transaction tx) {

        //update db state
        transactionService.changeState(tx, TransactionState.APPROVED);
        log.info("executed processApprovedState");

        //advanced to next state
        producer.sendDefault(tx);
    }

    public void processAppliedState(Transaction tx) {

        //update db state
        transactionService.changeState(tx, TransactionState.APPLIED);
        log.info("executed processAppliedState");
        //advanced to next state
        producer.send(service2Topic, tx);
    }

    @KafkaHandler
    public void dispatchState(Transaction tx) {
        try {
            TransactionState nextState = transactionService.getNextState(tx.getState());
            switch (nextState) {
                case WAITING_APPROVAL -> processWaitingApprovalState(tx);
                case APPROVED -> processApprovedState(tx);
                case APPLIED -> processAppliedState(tx);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}