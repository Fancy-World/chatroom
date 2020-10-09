package com.luorifeiche.controller;

import com.luorifeiche.dao.ChatDao;
import com.luorifeiche.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatDao chatDao;

    @RequestMapping(value="/send",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String send(@RequestParam("name") String name,
                       @RequestParam("content") String content,
                       @RequestParam("uid") int uid){
        String s= "";
        Date date = new Date();
        String currentTime = new SimpleDateFormat("hh:mm:ss").format(new Date()).toString();


        try{
            s = chatService.sendMessage(content, name, currentTime,uid);
        } catch(Exception e){
            e.printStackTrace();
        }
        return s;
    }

    @RequestMapping(value = "/get",produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String get(@RequestParam("index") int index){
        String message = "";
        try{
            message = chatService.getMessage(index);
        } catch(Exception e){
            e.printStackTrace();
        }
        if(message.equals("")){
            return "";
        }
        return message;
    }

    @RequestMapping(value="/del")
    @ResponseBody
    public String del(){
        try{
            chatDao.del();
        } catch(Exception e){
            e.printStackTrace();
        }
        return "success";
    }
}
