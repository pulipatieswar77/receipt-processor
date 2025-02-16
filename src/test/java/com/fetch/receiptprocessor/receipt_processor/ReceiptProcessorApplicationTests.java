package com.fetch.receiptprocessor.receipt_processor;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(classes = ReceiptProcessorApplication.class)
class ReceiptProcessorApplicationTests {

	private static final Logger log =  LoggerFactory.getLogger(ReceiptProcessorApplicationTests.class);

    @Test
    void contextLoads() {
        log.info("Starting context load test...");
    }

}
