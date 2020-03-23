package com.example.privatechat.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.privatechat.Component.Chatbubbleitem;
import com.example.privatechat.R;

import java.util.ArrayList;

public class ChatBubbleListAdapter extends BaseAdapter implements Parcelable {

    private ArrayList<Chatbubbleitem> chatbubbleitemArrayList = new ArrayList<Chatbubbleitem>();


    public ChatBubbleListAdapter() {
    }

    public ChatBubbleListAdapter(Parcel in) {
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        //        public ListAdapter createFromParcel(Parcel in){
//            return new ListAdapter(in);
//        }
        public ChatBubbleListAdapter createFromParcel(Parcel in) {
            return new ChatBubbleListAdapter(in);
        }

        //배열 객체 만들어 주기
        public ChatBubbleListAdapter[] newArray(int size) {
            return new ChatBubbleListAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }


    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String msg, int type) {
        Chatbubbleitem item = new Chatbubbleitem(msg, type);
        if (item != null) {
            chatbubbleitemArrayList.add(item);
        }
    }

    public void addBitmap(Bitmap icon,String path, int type) {
        Chatbubbleitem item = new Chatbubbleitem(icon,path, type);
        if (item != null) {
            chatbubbleitemArrayList.add(item);
        }
    }

    @Override
    public int getCount() {
        return chatbubbleitemArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatbubbleitemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        LinearLayout layout = null;
        View viewRight = null;
        View viewLeft = null;
        TextView chatTextView = null;
        ImageView imageView = null;

        Chatbubbleitem chatbubbleitem = chatbubbleitemArrayList.get(position);

        int type = chatbubbleitem.getType();
        if (type > 2) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatbubbleitem_image, parent, false);

            layout = (LinearLayout) convertView.findViewById(R.id.chatbubbleitemlayout_image);
            viewRight = (View) convertView.findViewById(R.id.imageViewright_image);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft_image);
            imageView = (ImageView) convertView.findViewById(R.id.chatimage_image);
            imageView.setImageBitmap(chatbubbleitem.getIcon());

        } else {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatbubbleitem, parent, false);

            layout = (LinearLayout) convertView.findViewById(R.id.chatbubbleitemlayout);
            viewRight = (View) convertView.findViewById(R.id.imageViewright);
            viewLeft = (View) convertView.findViewById(R.id.imageViewleft);
            chatTextView = (TextView) convertView.findViewById(R.id.chatText);
            chatTextView.setText(chatbubbleitem.getMsg());


        }


        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득

        switch (type) {
            case 0:
                chatTextView.setBackgroundResource(R.drawable.inbox2);
                layout.setGravity(Gravity.LEFT);
                viewRight.setVisibility(View.GONE);
                viewLeft.setVisibility(View.GONE);
                break;
            case 1:
                chatTextView.setBackgroundResource(R.drawable.outbox2);
                layout.setGravity(Gravity.RIGHT);
                viewRight.setVisibility(View.GONE);
                viewLeft.setVisibility(View.GONE);
                break;
            case 2:
                chatTextView.setBackgroundResource(R.drawable.datebg);
                layout.setGravity(Gravity.CENTER);
                viewRight.setVisibility(View.VISIBLE);
                viewLeft.setVisibility(View.VISIBLE);
                break;
            case 3:
                imageView.setBackgroundResource(R.drawable.inbox2);
                layout.setGravity(Gravity.LEFT);
                viewRight.setVisibility(View.GONE);
                viewLeft.setVisibility(View.GONE);
                break;
            case 4:
                imageView.setBackgroundResource(R.drawable.outbox2);
                layout.setGravity(Gravity.RIGHT);
                viewRight.setVisibility(View.GONE);
                viewLeft.setVisibility(View.GONE);
                break;
        }


        return convertView;
    }

    public void clearList() {
        chatbubbleitemArrayList.clear();
    }
}
