package com.example.privatechat.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.ListFragment;

import com.example.privatechat.Activity.ChatActivity;
import com.example.privatechat.Adapter.ListAdapter;
import com.example.privatechat.Component.ListItem;
import com.example.privatechat.Component.ListItemArray;
import com.example.privatechat.R;


public class ChatListFragment extends ListFragment {

    private Context mContext;
    private ListAdapter adapter;
    private ListItemArray listItemArray;
    public ChatListFragment() {

    }

    public static final ChatListFragment newInstance(ListAdapter adapter) {

        ChatListFragment chatListFragment = new ChatListFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("adapter", adapter);
        chatListFragment.setArguments(bundle);
        return chatListFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
//        adapter = new ListAdapter();
//        setListAdapter(adapter);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //일단 디비 없이 생성
        listItemArray = new ListItemArray();
        ListItem item1 = new ListItem();

        item1.setIcon(ContextCompat.getDrawable(mContext, R.drawable.test_icon));
        item1.setTitle("New Box1");
        item1.setDesc("New Account Box Black 36dp");
        listItemArray.add(item1);

        ListItem item2 = new ListItem();

        item2.setIcon(ContextCompat.getDrawable(mContext, R.drawable.test_icon));
        item2.setTitle("aa");
        item2.setDesc("asdasdasd");
        listItemArray.add(item2);

        refreshList();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = container.getContext();
        this.adapter = getArguments().getParcelable("adapter");
        setListAdapter(adapter);


        return inflater.inflate(R.layout.fragment_chatlist, container, false);

    }


    public void addItem(Drawable icon, String title, String desc) {
        if (adapter == null) {

        } else {
            adapter.addItem(icon, title, desc);

        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // get TextView's Text.
        ListItem item = (ListItem) l.getItemAtPosition(position);


        String titleStr = item.getTitle();
        String descStr = item.getDesc();
        Drawable iconDrawable = item.getIcon();

        /*
         * 리스트 눌렀을 때 할일
         * 온라인 확인, 갠톡*/

        Intent intent = new Intent(mContext, ChatActivity.class);
        intent.putExtra("title",titleStr);

        startActivity(intent);

        // TODO : use item data.
    }

    public void refreshList() {
        ListItem listItem = null;
        if(adapter!=null){
            adapter.clearList();
            if (!listItemArray.isEmpty()) {
                for (int i = 0; i < listItemArray.size(); i++) {
                    listItem = listItemArray.get(i);
                    addItem(listItem.getIcon(), listItem.getTitle(), listItem.getDesc());
                }
            }

        }



    }

}
