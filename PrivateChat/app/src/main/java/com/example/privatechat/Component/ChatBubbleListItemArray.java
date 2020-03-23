package com.example.privatechat.Component;

import java.util.Vector;

public class ChatBubbleListItemArray extends Vector<Chatbubbleitem> {

    @Override
    public synchronized boolean add(Chatbubbleitem chatbubbleitem) {
        return super.add(chatbubbleitem);
    }

    @Override
    public synchronized Chatbubbleitem get(int index) {
        return super.get(index);
    }

    @Override
    public synchronized boolean isEmpty() {
        return super.isEmpty();
    }

    @Override
    public synchronized int size() {
        return super.size();
    }
}
