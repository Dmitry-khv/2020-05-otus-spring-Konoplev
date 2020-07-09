package ru.otus.hw2.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.hw2.config.YamlProps;

@Service
public class MessageSourceServiceImpl implements MessageSourceService {
    private final MessageSource messageSource;
    private final YamlProps yamlProps;

    public MessageSourceServiceImpl(MessageSource messageSource, YamlProps yamlProps) {
        this.messageSource = messageSource;
        this.yamlProps = yamlProps;
    }

    @Override
    public String getMessage(String message) {
        return messageSource.getMessage(message, null, yamlProps.getLocale());
    }

    @Override
    public String getMessage(String message, String obj) {
        return messageSource.getMessage(message, new Object[]{obj}, yamlProps.getLocale());
    }
}
