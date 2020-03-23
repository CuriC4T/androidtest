package com.example.privatechat.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.privatechat.Adapter.ListAdapter;
import com.example.privatechat.Fragment.ChatListFragment;
import com.example.privatechat.Fragment.FriendFragment;
import com.example.privatechat.R;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;

    private FrameLayout frameLayout;
    private ChatListFragment chatListFragment;
    private FriendFragment friendFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = findViewById(R.id.frameLayout);

        friendFragment = FriendFragment.newInstance(new ListAdapter());
        chatListFragment = ChatListFragment.newInstance(new ListAdapter());


        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frameLayout, friendFragment).commitAllowingStateLoss();

    }

    public void mainButtonOnclick(View view) {
        transaction = fragmentManager.beginTransaction();

        switch (view.getId()) {
            case R.id.friendButton:
                transaction.replace(R.id.frameLayout, friendFragment).commitAllowingStateLoss();

                friendFragment.refreshList();
                break;
            case R.id.chatListButton:
                transaction.replace(R.id.frameLayout, chatListFragment).commitAllowingStateLoss();
                chatListFragment.refreshList();
                break;
//            case R.id.settingButton:
//                transaction.replace(R.id.frameLayout, fragmentB).commitAllowingStateLoss();
//                break;
        }
    }
}
