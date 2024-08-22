package codebreakers.demo.kafka;

import codebreakers.demo.domain.ProfileChange;
import codebreakers.demo.service.ProfileChangeService;
import codebreakers.demo.service.ProfileHistoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ProfileListener {

    private final ProfileChangeService profileChangeService;
    private final ProfileHistoryService profileHistoryService;
    private final ObjectMapper objectMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public ProfileListener(ProfileChangeService profileChangeService, ProfileHistoryService profileHistoryService, ObjectMapper objectMapper) {
        this.profileChangeService = profileChangeService;
        this.profileHistoryService = profileHistoryService;
        this.objectMapper = objectMapper;
    }

    @Transactional
    @KafkaListener(
            topics = "spring.kafka.consumer.topic",
            groupId = "spring.kafka.consumer.group-id",
            batch = "false"
    )
    public void listen(ConsumerRecord<String, String> newRecord) {
        try {
            ProfileChange profileChange = objectMapper.readValue(newRecord.value(), ProfileChange.class);
            profileChangeService.save(profileChange);
            profileHistoryService.processProfileEvent(profileChange);
        } catch (Exception ex) {
            logger.error("Error while processing record: " + newRecord.value(), ex);
        }
    }
}
