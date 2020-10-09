package com.luorifeiche.dao;

import com.google.gson.Gson;
import com.luorifeiche.entity.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Repository
public class ChatDao {

    @Autowired
    RedisTemplate<String,String> redisTemplate;

    private Logger logger = LoggerFactory.getLogger(ChatDao.class);

    private Gson gson = new Gson();


    /**
     * 用户发送信息
     * @param content
     * @param name
     * @param time
     * @return
     * @throws Exception
     */
    public String sendMessage(String content,
                              String name,
                              String time,
                              int uid) throws Exception{
        Long messageSize = redisTemplate.opsForList().size("message");
        logger.info(messageSize+"");
        int index = 0;
        if(messageSize == 0){
            //这条信息作为第一条index为0；
            index = 0;
            //插入值
            Message message = new Message();
            message.setId(index);
            message.setContent(content);
            message.setName(name);
            message.setTime(time);
            message.setUid(uid);
            String json = gson.toJson(message);
            redisTemplate.opsForList().rightPush("message", json);
            return json;
        } else {
            //否则的话说明这个list不为空，所以需要根据最后一个元素获取当前元素的index值。
            String messageString = redisTemplate.opsForList().index("message",messageSize - 1);
            Message message = gson.fromJson(messageString, Message.class);
            int id = message.getId() + 1;
            //开始插入
            Message insertMessage = new Message();
            insertMessage.setId(id);
            insertMessage.setName(name);
            insertMessage.setContent(content);
            insertMessage.setTime(time);
            insertMessage.setUid(uid);
            String json = gson.toJson(insertMessage);
            redisTemplate.opsForList().rightPush("message",json);
            return json;
        }
    }


    /**
     * 用户获取list中的信息；index为上次已读的信息索引。防止重复读取；0即为用户刚进聊天室。
     * @param index
     * @return
     */
    public String getMessage(int index) throws Exception{
        long size = redisTemplate.opsForList().size("message");
        if(size == 0){
            return "";
        }

        if((index+1) == size){
            return "";
        }

        List<String> list = redisTemplate.opsForList().range("message", index + 1, -1);

        String s = gson.toJson(list);
        //RedisConnectionUtils.unbindConnection(redisTemplate.getConnectionFactory());
        return s;
    }

    public void  del() throws Exception{
        redisTemplate.delete("message");
    }
}
