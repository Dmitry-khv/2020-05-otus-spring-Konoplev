package ru.otus.hw2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.hw2.model.Question;
import ru.otus.hw2.resourcemanager.ResourceData;

@Repository
public class MessageDaoImpl implements MessageDao{

    private final ResourceData resource;

    @Autowired
    public MessageDaoImpl(ResourceData resource) {
        this.resource = resource;
    }

    @Override
    public Question getQuestions() {
        return resource.getMessage();
    }
}