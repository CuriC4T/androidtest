package com.example.privatechat.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.privatechat.Adapter.ChatBubbleListAdapter;
import com.example.privatechat.Fragment.ChatBubbleListFragment;
import com.example.privatechat.QwertyLayout.QwertyLayout;
import com.example.privatechat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChatActivity extends AppCompatActivity implements QwertyLayout.OnFragmentInterectionListener, View.OnClickListener {
    private Activity activity;
    private QwertyLayout qwertyLayout;
    private View qwertyVew;
    private EditText editText;
    private Fragment chatListFragment;
    private ChatBubbleListFragment chatBubbleListFragment;
    private FrameLayout frameLayout;


    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;


    private Button backButton, searchButton, addButton, testButton;

    private LinearLayout keyboardLayout;

    private boolean isPopuped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init();

    }

    public void init() {
        activity = this;
        isPopuped = false;

        chatBubbleListFragment = ChatBubbleListFragment.newInstance(new ChatBubbleListAdapter());

        editText = findViewById(R.id.chat_edit_text);
        frameLayout = findViewById(R.id.frameLayout2);
        keyboardLayout = findViewById(R.id.keyboardLayout);

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frameLayout2, chatBubbleListFragment).commitAllowingStateLoss();

        settingButton();
        settingEditText();
        settingQwertyLayout();
    }

    @Override
    public void setString(String msg, int type) {
        chatBubbleListFragment.addItem(msg, type);
        chatBubbleListFragment.updateList();
    }

    public void settingButton() {

        backButton = findViewById(R.id.back_button);
        searchButton = findViewById(R.id.search_button);
        addButton = findViewById(R.id.add_button);
        testButton = findViewById(R.id.test_button);

        backButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        testButton.setOnClickListener(this);

    }

    public void settingQwertyLayout() {
        qwertyLayout = activity.findViewById(R.id.qwertylayout);
        qwertyVew = qwertyLayout.getView();

        InputConnection ic = editText.onCreateInputConnection(new EditorInfo());
        qwertyLayout.setLineText(editText);
        qwertyLayout.setInputConnection(ic);
        qwertyLayout.addView(qwertyVew);
        qwertyVew.setVisibility(View.GONE);

    }

    @SuppressLint("ClickableViewAccessibility")
    public void settingEditText() {

        if (Build.VERSION.SDK_INT >= 21) {
            editText.setShowSoftInputOnFocus(false);
            editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
            editText.setTextIsSelectable(true);

        } else {
            editText.setRawInputType(InputType.TYPE_NULL);
            editText.setFocusable(true);

        }
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewGroup.LayoutParams layoutParams = frameLayout.getLayoutParams();
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (isPopuped) {
                        qwertyVew.setVisibility(View.GONE);
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 610, getResources().getDisplayMetrics());
                        layoutParams.height = height;
                        isPopuped = false;

                    } else {
                        qwertyVew.setVisibility(View.VISIBLE);
                        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 360, getResources().getDisplayMetrics());
                        layoutParams.height = height;
                        isPopuped = true;
                    }
                }

                return false;

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.back_button:
                finish();
                break;
            case R.id.add_button:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.createChooser(intent, "Picture");
                startActivityForResult(intent, 1);

                break;
            case R.id.search_button:
                break;
            default:

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 1:
                try {
                    String path;
                    int resizedwidth = 200;

                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap bitmap = BitmapFactory.decodeStream(in);

                    File storage = getCacheDir();
                    String fileName = bitmap.toString();

                    File tempFile = new File(storage, fileName);
                    path =storage.getAbsolutePath().concat("/").concat(fileName);

                    tempFile.createNewFile();

                    // 파일을 쓸 수 있는 스트림을 준비합니다.
                    FileOutputStream out = new FileOutputStream(tempFile);

                    // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                    // 스트림 사용후 닫아줍니다.
                    out.close();


                    double height = bitmap.getHeight();
                    double width = bitmap.getWidth();

                    double ratio = height / width;
                    int resizeheight = (int) (resizedwidth * ratio);
                    //bitmap.compress(Bitmap.CompressFormat.JPEG,40,out);
                    Bitmap icon = Bitmap.createScaledBitmap(bitmap, resizedwidth, resizeheight, true);
                    bitmap.recycle();

                    chatBubbleListFragment.addBitmap(icon,path, 4);
                    chatBubbleListFragment.updateList();
                    in.close();
                } catch (IOException e) {

                }
                break;

        }
    }


}
