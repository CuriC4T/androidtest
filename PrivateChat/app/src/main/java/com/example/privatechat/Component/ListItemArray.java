package com.example.privatechat.Component;

import java.util.Vector;

public class ListItemArray extends Vector<ListItem> {

    @Override
    public synchronized boolean add(ListItem listItem) {
        return super.add(listItem);
    }

    @Override
    public synchronized ListItem get(int index) {
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
