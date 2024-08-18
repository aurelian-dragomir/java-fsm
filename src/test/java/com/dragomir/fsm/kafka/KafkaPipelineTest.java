package com.dragomir.fsm.kafka;

import com.dragomir.fsm.entity.Transaction;
import com.dragomir.fsm.state.TransactionState;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.annotation.Rollback;

import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Import(KafkaConsumer.class)
@Slf4j
public class KafkaPipelineTest {

    @Autowired
    private KafkaConsumer kafkaConsumer;

    @Autowired
    private KafkaTemplate<Void, Transaction> prod;

    @Test
    @Rollback(value = false)
    public void test1() {
        var tx = new Transaction(1L, TransactionState.NEW);
        prod.executeInTransaction(kt -> kt.sendDefault(tx));

        await()
                .atMost(10, TimeUnit.SECONDS)
                .pollInterval(1, TimeUnit.SECONDS)
                .until(() -> kafkaConsumer.getTx() != null);
        assertTrue(kafkaConsumer.getTx().getState() == TransactionState.APPLIED);
    }
}
