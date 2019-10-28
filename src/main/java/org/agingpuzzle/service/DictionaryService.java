package org.agingpuzzle.service;

import lombok.extern.slf4j.Slf4j;
import org.agingpuzzle.model.Message;
import org.agingpuzzle.repo.MessageRepository;
import org.agingpuzzle.web.LanguageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DictionaryService {

    public static final String ROLE_TYPE = "role";
    public static final String STATUS_TYPE = "status";

    @Autowired
    private MessageRepository messageRepository;

    private List<Message> messages;

    // type -> key -> lang -> message
    private Map<String, Map<String, Map<String, Message>>> dictionaries;

    @PostConstruct
    private void loadDictionary() {
        messages = messageRepository.findAllByOrderByTypeAscKeyAscLanguageAsc();
        dictionaries = new TreeMap<>();
        messages.forEach(msg -> {
            dictionaries.putIfAbsent(msg.getType(), new TreeMap<>());
            var byType = dictionaries.get(msg.getType());

            byType.putIfAbsent(msg.getKey(), new TreeMap<>());
            var byKey = byType.get(msg.getKey());

            byKey.put(msg.getLanguage(), msg);
        });
    }

    public String getText(String type, String lang, String keys) {
        if (keys == null) {
            return "";
        }
        return Arrays.stream(keys.split(","))
                .map(key -> find(type, key, lang))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(Message::getText)
                .collect(Collectors.joining(", "));
    }

    public Map<String, String> getDictionaryForType(String type, String lang) {
        return dictionaries.get(type).entrySet().stream()
                .filter(e -> e.getValue().containsKey(lang))
                .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().get(lang).getText()));
    }

    public String getCSV() {
        List<String> langs = new ArrayList<>(LanguageUtils.getSupportedLanguages());

        StringBuilder csv = new StringBuilder("Type,Key");
        for (String lang : langs) {
            csv.append(',').append(lang);
        }
        csv.append('\n');

        dictionaries.forEach((type, map) -> {
            map.forEach((key, values) -> {
                csv.append(type).append(',').append(key);
                for (String lang : langs) {
                    csv.append(',');
                    if (values.containsKey(lang)) {
                        csv.append(values.get(lang).getText());
                    }
                }
                csv.append('\n');
            });
        });

        return csv.toString();
    }

    public void updateFromCSV(String csv) {
        var rows = csv.trim().split("\n");
        var langs = Arrays.stream(rows[0].split(",")).skip(2).map(String::trim).collect(Collectors.toList());

        var toSave = new ArrayList<Message>();
        var toDelete = new ArrayList<>(messages);

        Arrays.stream(rows).skip(1).map(row -> row.split(",")).forEach(row -> {
            String type = row[0];
            String key = row[1];
            for (int i = 2; i < row.length; i++) {
                String lang = langs.get(i - 2);
                String text = row[i].trim();

                if (!text.isBlank()) {
                    var found = find(type, key, lang);
                    found.ifPresentOrElse(msg -> {
                        toDelete.remove(msg);
                        if (!text.equals(msg.getText())) {
                            msg.setText(text);
                            toSave.add(msg);
                        }
                    }, () -> {
                        toSave.add(new Message(type, key, lang, text));
                    });
                }
            }
        });

        log.info("Saving {} messages, deleting {} messages", toSave.size(), toDelete.size());
        messageRepository.batchUpdate(toSave, toDelete);

        loadDictionary();
    }

    private Optional<Message> find(String type, String key, String lang) {
        return Optional.ofNullable(dictionaries.get(type))
                .map(m -> m.get(key))
                .map(m -> m.get(lang));
    }
}
