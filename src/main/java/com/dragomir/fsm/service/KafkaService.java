package com.dragomir.fsm.service;

import com.dragomir.fsm.entity.Transaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaService {
    private final KafkaTemplate<Void, Transaction> kafkaTemplate;

    @Transactional("kafkaTransactionManager")
    public void sendToNextServiceStateInTx(Transaction tx) {
        kafkaTemplate.sendDefault(tx);
    }

    public void sendToNextServiceState(Transaction tx) {
        kafkaTemplate.sendDefault(tx);
    }
}
