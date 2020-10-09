package com.luorifeiche.service;

import com.luorifeiche.dao.ChatDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatDao chatDao;

    public String sendMessage(String content,
                              String name,
                              String time,
                              int uid) throws Exception{
        String s = chatDao.sendMessage(content, name, time,uid);
        return s;
    }

    public String getMessage(int index) throws Exception{
        String message = chatDao.getMessage(index);
        return message;
    }
}
