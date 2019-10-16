package org.agingpuzzle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Message;
import org.agingpuzzle.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {

    @Autowired
    private MessageRepository messageRepository;

    private Map<String, Message> dictionary;

    private String keyForMessage(Message message) {
        return String.format("%s_%s_%s", message.getType(), message.getKey(), message.getLanguage());
    }

    private void loadDictionary() {
        dictionary = messageRepository.findAllOrderByType()
                .stream().collect(Collectors.toMap(this::keyForMessage, identity()));
    }

    public String getValue(String type, String keys) {
        return null;
    }

    public Map<String, String> getDictionaryForType(String type) {
        return null;
    }

    public void updateFromCSV(String csv) {

    }

    public String getCSV() {
        return null;
    }
}
