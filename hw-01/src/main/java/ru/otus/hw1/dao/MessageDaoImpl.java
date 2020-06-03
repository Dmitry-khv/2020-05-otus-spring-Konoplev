package ru.otus.hw1.dao;

import ru.otus.hw1.model.Message;
import ru.otus.hw1.resourcemanager.ResourceData;

public class MessageDaoImpl implements MessageDao{

    private final ResourceData resource;

    public MessageDaoImpl(ResourceData resource) {
        this.resource = resource;
    }

    @Override
    public Message getMessage() {
        return resource.getMessage();
    }
}
