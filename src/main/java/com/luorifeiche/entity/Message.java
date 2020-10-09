package com.luorifeiche.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Message {
    private int id;
    private String name;
    private String time;
    private String content;
    private int uid;
}
