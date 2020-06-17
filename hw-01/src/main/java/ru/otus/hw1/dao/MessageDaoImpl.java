package ru.otus.hw1.dao;

import ru.otus.hw1.model.Question;
import ru.otus.hw1.resourcemanager.ResourceData;

public class MessageDaoImpl implements MessageDao{

    private final ResourceData resource;

    public MessageDaoImpl(ResourceData resource) {
        this.resource = resource;
    }

    @Override
    public Question getMessage() {
        return resource.getMessage();
    }
}
