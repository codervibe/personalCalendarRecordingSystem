package com.codervibe.rizhi.service;

import com.codervibe.rizhi.model.Message;

import java.util.List;

public interface MessageService {

    public List<Message> findMessages(Integer owner_id,Integer sendto);

    public void addMessage(Message message);
}
