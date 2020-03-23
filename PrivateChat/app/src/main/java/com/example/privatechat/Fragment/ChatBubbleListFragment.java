package com.example.privatechat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.privatechat.Activity.PictureViewerActivity;
import com.example.privatechat.Adapter.ChatBubbleListAdapter;
import com.example.privatechat.Component.ChatBubbleListItemArray;
import com.example.privatechat.Component.Chatbubbleitem;
import com.example.privatechat.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatBubbleListFragment extends ListFragment {
    private Context mContext;
    private ChatBubbleListAdapter chatBubbleListAdapter;
    private ChatBubbleListItemArray chatBubbleListItemArray;


    public ChatBubbleListFragment() {

    }

    public static ChatBubbleListFragment newInstance(ChatBubbleListAdapter chatBubbleListAdapter) {
        ChatBubbleListFragment chatBubbleListFragment = new ChatBubbleListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("adapter", chatBubbleListAdapter);
        chatBubbleListFragment.setArguments(bundle);
        return chatBubbleListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = container.getContext();
        chatBubbleListAdapter = getArguments().getParcelable("adapter");
        setListAdapter(chatBubbleListAdapter);
        return inflater.inflate(R.layout.fragment_chatbubblelist, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        chatBubbleListItemArray = new ChatBubbleListItemArray();
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdfNow = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String formatDate = sdfNow.format(date);


        Chatbubbleitem item = new Chatbubbleitem(String.valueOf(formatDate), 2);

        chatBubbleListItemArray.add(item);

        refreshList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Chatbubbleitem chatbubbleitem = (Chatbubbleitem) l.getItemAtPosition(position);
        int type = chatbubbleitem.getType();

        switch (type) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:

                //파일로 저장하고 읽어오자
                //상대 꺼면 그전에 서버로부터 저장하던지 디비에 저장하던지 해야될
                break;
            case 4:

                //파일로 저장하고 읽어오자
                //내꺼면 경로로부터 읽어와서 넘겨주기
                Intent intent = new Intent(mContext, PictureViewerActivity.class);
                intent.putExtra("path", chatbubbleitem.getPath());


                startActivityForResult(intent, 1);


                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                break;
        }
    }

    public void addItem(String msg, int type) {
        chatBubbleListAdapter.addItem(msg, type);
    }

    public void addBitmap(Bitmap icon, String path, int type) {
        chatBubbleListAdapter.addBitmap(icon, path, type);
    }

    public void refreshList() {
        Chatbubbleitem listItem = null;
        if (chatBubbleListAdapter != null) {
            chatBubbleListAdapter.clearList();
            if (!chatBubbleListItemArray.isEmpty()) {
                for (int i = 0; i < chatBubbleListItemArray.size(); i++) {
                    listItem = chatBubbleListItemArray.get(i);
                    addItem(listItem.getMsg(), listItem.getType());
                }
            }

        }


    }

    public void updateList() {
        chatBubbleListAdapter.notifyDataSetChanged();           // 리스트 목록 갱신


    }


}
