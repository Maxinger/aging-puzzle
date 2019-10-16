package org.agingpuzzle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Message;
import org.agingpuzzle.repo.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DictionaryService {

    @Autowired
    private MessageRepository messageRepository;

    // lang -> type -> key -> value
    private Map<String, Map<String, Map<String, String>>> dictionary;

    private String keyForMessage(Message message) {
        return String.format("%s_%s_%s", message.getType(), message.getKey(), message.getLanguage());
    }

    private void loadDictionary() {
        dictionary = new TreeMap<>();
        messageRepository.findAllOrderByType().forEach(msg -> {
            dictionary
                    .putIfAbsent(msg.getLanguage(), new TreeMap<>())
                    .putIfAbsent(msg.getType(), new TreeMap<>())
                    .putIfAbsent(msg.getKey(), msg.getValue());
        });
    }

    public String getValue(String type, String lang, String keys) {
        return Arrays.stream(keys.split(","))
                .map(key -> dictionary.get(lang).get(type).getOrDefault(key, key))
                .collect(Collectors.joining(", "));
    }

    public Map<String, String> getDictionaryForType(String type, String lang) {
        return dictionary.get(lang).get(type);
    }

    public void updateFromCSV(String csv) {

    }

    public String getCSV() {
        return null;
    }
}
