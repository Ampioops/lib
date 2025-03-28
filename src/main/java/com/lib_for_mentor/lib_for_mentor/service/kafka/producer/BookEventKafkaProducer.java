package com.lib_for_mentor.lib_for_mentor.service.kafka.producer;

import org.common.common_utils.event.BookEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookEventKafkaProducer {
    private final KafkaTemplate<String, BookEvent> kafkaTemplate;

    public void sendBookEvent(BookEvent bookEvent) {
        kafkaTemplate.send("book-events", bookEvent);
    }
}
