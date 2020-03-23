package com.example.privatechat.QwertyLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.privatechat.Cryption.AES256;
import com.example.privatechat.R;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class QwertyLayout extends LinearLayout implements View.OnClickListener {

    private static final int NOMAL_MODE = 0;
    private static final int SPECIAL_MODE = 1;

    private OnFragmentInterectionListener onFragmentInterectionListener;

    private static final String[] N_ARRAY = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"};
    private static final String[] Q_ARRAY = {"q", "w", "e", "r", "t", "y", "u", "i", "o", "p"};
    private static final String[] A_ARRAY = {"a", "s", "d", "f", "g", "h", "j", "k", "l"};
    private static final String[] Z_ARRAY = {"z", "x", "c", "v", "b", "n", "m"};

    private static final String[] SN_ARRAY = {"!", "@", "#", "$", "%", "^", "&", "*", "(", ")"};
    private static final String[] SQ_ARRAY = {"-", "=", "+", "{", "}", "[", "]", "\\", ":", ";"};
    private static final String[] SA_ARRAY = {"\"", "\'", "<", ">", ".", ".", "/", "?", "|"};
    private static final String[] SZ_ARRAY = {"~", "`", "_", "￦", "#", "@", "!"};

    private Context context;
    private View view;
    private Activity activity;
    private InputConnection ic;
    private EditText line_text;

    private Button[] number_Button_Array;
    private Button[] q_Button_Array;
    private Button[] a_Button_Array;
    private Button[] z_Button_Array;
    private Button[] s_Button_Array;

    private int[] blank_narray;
    private int[] blank_qarray;
    private int[] blank_aarray;
    private int[] blank_zarray;

    private SecureRandom random;

    private AES256 aes256;
    private int mode = NOMAL_MODE;

//    private TextView encrypted_textview;
//    private TextView decrypted_textview;
//    private TextView ECBencrypted_textview;
//    private TextView ECBdecrypted_textview;

    private boolean iscap;
    private boolean isdown;

    //Constructor//
    public QwertyLayout(Context context) {
        this(context, null, 0);
    }

    public QwertyLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QwertyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        if (context != null) {
            this.context = context;
            activity = (Activity) context;
            onFragmentInterectionListener= (OnFragmentInterectionListener)activity;
            view = LayoutInflater.from(context).inflate(R.layout.qwertylayout, this, false);
            String key;
            iscap = false;
            isdown = false;

            try {
                random = SecureRandom.getInstance("SHA1PRNG");
                key = "hyunjaewook12345";
                aes256 = new AES256(key);
            } catch (NoSuchAlgorithmException e) {
                Log.e("CRYPTION_ERROR", "error");

            } catch (UnsupportedEncodingException e) {
                Log.e("CRYPTION_ERROR", "error");
            }
            settingLayout();
        }

    }


    /*Layout setting*/
    //기본적인 버튼 초기화
    public void settingLayout() {
        int resource_id;

        number_Button_Array = new Button[12];
        q_Button_Array = new Button[12];
        a_Button_Array = new Button[13];
        z_Button_Array = new Button[11];
        s_Button_Array = new Button[4];

        for (int i = 0; i < 12; i++) {
            resource_id = view.getResources().getIdentifier("button_".concat(String.valueOf(i)), "id", context.getPackageName());
            number_Button_Array[i] = view.findViewById(resource_id);
            number_Button_Array[i].setOnClickListener(this);
        }
        for (int i = 0; i < 12; i++) {
            resource_id = view.getResources().getIdentifier("qbutton_".concat(String.valueOf(i)), "id", context.getPackageName());
            q_Button_Array[i] = view.findViewById(resource_id);
            q_Button_Array[i].setOnClickListener(this);
        }
        for (int i = 0; i < 13; i++) {
            resource_id = view.getResources().getIdentifier("abutton_".concat(String.valueOf(i)), "id", context.getPackageName());
            a_Button_Array[i] = view.findViewById(resource_id);
            a_Button_Array[i].setOnClickListener(this);
        }
        for (int i = 0; i < 11; i++) {
            resource_id = view.getResources().getIdentifier("zbutton_".concat(String.valueOf(i)), "id", context.getPackageName());
            z_Button_Array[i] = view.findViewById(resource_id);
            z_Button_Array[i].setOnClickListener(this);
        }
        for (int i = 0; i < 4; i++) {
            resource_id = view.getResources().getIdentifier("sbutton_".concat(String.valueOf(i)), "id", context.getPackageName());
            s_Button_Array[i] = view.findViewById(resource_id);
            s_Button_Array[i].setOnClickListener(this);
        }

        blankSetting();
        basicKeySetting();
    }
    //빈칸 세팅

    public void blankSetting() {
        int random_position1;
        int random_position2;
        int random_position3;
        int random_position4;

        blank_narray = new int[2];//2  10
        blank_qarray = new int[2];//2  10
        blank_aarray = new int[4];//4  9
        blank_zarray = new int[2];//2  7 특수키 2


        // 각각의 열의 빈칸 갯수 2 2 4 2 만큼 안 겹칠 때까지 반복
        random_position1 = random.nextInt(12);
        random_position2 = random.nextInt(12);
        while (random_position1 == random_position2) {
            random_position2 = random.nextInt(12);
        }
        blank_narray[0] = random_position1;
        blank_narray[1] = random_position2;

        random_position1 = random.nextInt(12);
        random_position2 = random.nextInt(12);
        while (random_position1 == random_position2) {
            random_position2 = random.nextInt(12);
        }
        blank_qarray[0] = random_position1;
        blank_qarray[1] = random_position2;

        random_position1 = random.nextInt(13);
        random_position2 = random.nextInt(13);
        random_position3 = random.nextInt(13);
        random_position4 = random.nextInt(13);
        while (random_position1 == random_position2 ||
                random_position2 == random_position3 ||
                random_position3 == random_position4 ||
                random_position4 == random_position1 ||
                random_position2 == random_position4 ||
                random_position1 == random_position3
        ) {

            random_position1 = random.nextInt(13);
            random_position2 = random.nextInt(13);
            random_position3 = random.nextInt(13);
            random_position4 = random.nextInt(13);
        }
        blank_aarray[0] = random_position1;
        blank_aarray[1] = random_position2;
        blank_aarray[2] = random_position3;
        blank_aarray[3] = random_position4;

        random_position1 = random.nextInt(9) + 1;
        random_position2 = random.nextInt(9) + 1;
        while (random_position1 == random_position2) {
            random_position2 = random.nextInt(9) + 1;
        }
        blank_zarray[0] = random_position1;
        blank_zarray[1] = random_position2;


    }

    //리스너 및 세팅 적용
    public void basicKeySetting() {
        commonKeySetting();

        int i = 0;

        for (Button button : number_Button_Array) {
            //빈칸이면 모든 설정 삭제
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);
            } else {
                //아니면 설정
                button.setText(N_ARRAY[i]); // 순차적으로 배열 입력 qwertyui....
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;

            }

        }
        i = 0;

        for (Button button : q_Button_Array) {
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);

            } else {
                button.setText(Q_ARRAY[i]);
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;
            }
        }
        i = 0;

        for (Button button : a_Button_Array) {
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);

            } else {
                button.setText(A_ARRAY[i]);
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;
            }
        }
        i = 0;

        for (int j = 1; j < 11 - 1; j++) {
            Button button = z_Button_Array[j];
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);

            } else {
                button.setText(Z_ARRAY[i]);
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;
            }
        }


    }

    /*key setting*/
    // 빈칸은 빈칸으로 키가 들어갈 자리는 1로 세팅
    public void commonKeySetting() {

        for (Button button : number_Button_Array) {
            button.setText("1");
        }
        for (Button button : q_Button_Array) {
            button.setText("1");
        }
        for (Button button : a_Button_Array) {
            button.setText("1");
        }
        for (Button button : z_Button_Array) {
            button.setText("1");
        }

        Button special = z_Button_Array[10];
        special.setText("←");
        special = z_Button_Array[0];
        special.setText("↑");


        number_Button_Array[blank_narray[0]].setText("");
        number_Button_Array[blank_narray[1]].setText("");

        q_Button_Array[blank_qarray[0]].setText("");
        q_Button_Array[blank_qarray[1]].setText("");

        a_Button_Array[blank_aarray[0]].setText("");
        a_Button_Array[blank_aarray[1]].setText("");
        a_Button_Array[blank_aarray[2]].setText("");
        a_Button_Array[blank_aarray[3]].setText("");

        z_Button_Array[blank_zarray[0]].setText("");
        z_Button_Array[blank_zarray[1]].setText("");

        longKeySetting();
    }


    //특수키 배열 설정
    public void specialKeySetting() {
        int i = 0;

        commonKeySetting();

        for (Button button : number_Button_Array) {

        }
        i = 0;

        for (Button button : q_Button_Array) {
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);

            } else {
                button.setText(SQ_ARRAY[i]);
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;
            }
        }
        i = 0;

        for (Button button : a_Button_Array) {
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);

            } else {
                button.setText(SA_ARRAY[i]);
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;
            }
        }
        i = 0;

        for (int j = 1; j < 11 - 1; j++) {
            Button button = z_Button_Array[j];
            if (button.getText().toString().compareTo("") == 0) {
                button.setOnClickListener(null);
                button.setClickable(false);

            } else {
                button.setText(SZ_ARRAY[i]);
                button.setOnClickListener(this);
                button.setClickable(true);
                i++;
            }
        }
    }

    // 롱키 삭제
    @SuppressLint("ClickableViewAccessibility")
    public void longKeySetting() {
        if (z_Button_Array[10] != null) {
            z_Button_Array[10].setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            isdown = true;
                            buttonDowned();

                            break;

                        case MotionEvent.ACTION_UP:
                            isdown = false;
                            break;

                        default:
                            break;
                    }
                    return false;
                }
            });

        }
    }

    private Handler touchHandler = new Handler() {
        public void handleMessage(Message msg) {
            deleteText();


        }
    };


    public void buttonDowned() {
        TouchThread thread = new TouchThread();
        thread.start();
    }

    private class TouchThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (isdown) {

                try {
                    touchHandler.sendEmptyMessage(9876);
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onClick(View v) {
        Button button = (Button) view.findViewById(v.getId());
        String text = String.valueOf(button.getText());


        //encrypted_textview = activity.findViewById(R.id.encrypted);
        //decrypted_textview = activity.findViewById(R.id.decrypted);
        //ECBencrypted_textview = activity.findViewById(R.id.ECBencrypted);
        //ECBdecrypted_textview = activity.findViewById(R.id.ECBdecrypted);

        String encrypted_text;
        String decrypted_text;
        String ecbencrypted_text;
        String ecbdecrypted_text;

        switch (v.getId()) {
            case R.id.sbutton_0://특수키
                switch (mode) {
                    case NOMAL_MODE:
                        specialKeySetting();
                        mode = SPECIAL_MODE;
                        break;
                    case SPECIAL_MODE:
                        basicKeySetting();
                        mode = NOMAL_MODE;
                        break;
                }
                break;
            case R.id.sbutton_1://재배열
                switch (mode) {
                    case NOMAL_MODE:
                        blankSetting();
                        basicKeySetting();
                        break;
                    case SPECIAL_MODE:
                        blankSetting();
                        specialKeySetting();
                        break;
                }


                break;
            case R.id.sbutton_2://스페이스
                ic.commitText(" ", 1);

                encrypted_text = aes256.aesCBCEncode(" ");
                decrypted_text = aes256.aesCBCDecode(encrypted_text);
                ecbencrypted_text = aes256.aesECBEncode(" ");
                ecbdecrypted_text = aes256.aesECBDecode(ecbencrypted_text);
//
//                encrypted_textview.append(encrypted_text);
//                decrypted_textview.append(decrypted_text);
//
//                ECBencrypted_textview.append(ecbencrypted_text);
//                ECBdecrypted_textview.append(ecbdecrypted_text);


                break;
            case R.id.sbutton_3://완료

//                encrypted_textview.setText("");
//                decrypted_textview.setText("");
//                ECBencrypted_textview.setText("");
//                ECBdecrypted_textview.setText("");
                onFragmentInterectionListener.setString(line_text.getText().toString(),1);
                line_text.setText("");
                break;

            case R.id.zbutton_0://대문자
                if (mode == NOMAL_MODE) {
                    if (iscap) {
                        for (Button temp : q_Button_Array) {
                            String temp_string = temp.getText().toString();
                            temp_string = temp_string.toLowerCase();
                            temp.setText(temp_string);
                            temp.setAllCaps(false);
                        }
                        for (Button temp : a_Button_Array) {
                            String temp_string = temp.getText().toString();
                            temp_string = temp_string.toLowerCase();
                            temp.setText(temp_string);
                            temp.setAllCaps(false);
                        }
                        for (Button temp : z_Button_Array) {
                            String temp_string = temp.getText().toString();
                            temp_string = temp_string.toLowerCase();
                            temp.setText(temp_string);
                            temp.setAllCaps(false);
                        }
                        iscap = false;
                    } else {
                        String temp_string;
                        for (Button temp : q_Button_Array) {
                            temp_string = temp.getText().toString();
                            temp_string = temp_string.toUpperCase();
                            temp.setText(temp_string);
                            temp.setAllCaps(true);
                        }
                        for (Button temp : a_Button_Array) {
                            temp_string = temp.getText().toString();
                            temp_string = temp_string.toUpperCase();
                            temp.setText(temp_string);
                            temp.setAllCaps(true);
                        }
                        for (Button temp : z_Button_Array) {
                            temp_string = temp.getText().toString();
                            temp_string = temp_string.toUpperCase();
                            temp.setText(temp_string);
                            temp.setAllCaps(true);
                        }
                        iscap = true;

                    }

                }


                break;
            case R.id.zbutton_10://삭제

                break;

            default:

                ic.commitText(text, 1);

                encrypted_text = aes256.aesCBCEncode(text);
                decrypted_text = aes256.aesCBCDecode(encrypted_text);
                ecbencrypted_text = aes256.aesECBEncode(text);
                ecbdecrypted_text = aes256.aesECBDecode(ecbencrypted_text);
//
//                encrypted_textview.append(encrypted_text);
//                decrypted_textview.append(decrypted_text);
//
//                ECBencrypted_textview.append(ecbencrypted_text);
//                ECBdecrypted_textview.append(ecbdecrypted_text);
                break;


        }


    }

    public void deleteText() {
        CharSequence selectedText = ic.getSelectedText(0);

        String dec_str;
        String enc_str;

        if (TextUtils.isEmpty(selectedText)) {
            //블럭된게 없을 시
            ic.deleteSurroundingText(1, 0);

            //dec_str = decrypted_textview.getText().toString();
            //enc_str = encrypted_textview.getText().toString();
//            if (dec_str.length() > 0) {
//                dec_str = dec_str.substring(0, dec_str.length() - 1);
//                decrypted_textview.setText(dec_str);
//                enc_str = enc_str.substring(0, enc_str.length() - 24);
//                encrypted_textview.setText(enc_str);
//
//            }
//            dec_str = ECBdecrypted_textview.getText().toString();
//            enc_str = ECBencrypted_textview.getText().toString();
//            if (dec_str.length() > 0) {
//                dec_str = dec_str.substring(0, dec_str.length() - 1);
//                ECBdecrypted_textview.setText(dec_str);
//                enc_str = enc_str.substring(0, enc_str.length() - 24);
//                ECBencrypted_textview.setText(enc_str);

            // }
        } else {
            //블럭된게 있을시
            ic.commitText("", 1);
            int size = selectedText.length();
//
//            dec_str = decrypted_textview.getText().toString();
//            enc_str = encrypted_textview.getText().toString();
//            dec_str = dec_str.substring(0, dec_str.length() - 1 * size);
//            decrypted_textview.setText(dec_str);
//            enc_str = enc_str.substring(0, enc_str.length() - 24 * size);
//            encrypted_textview.setText(enc_str);
//
//            dec_str = ECBdecrypted_textview.getText().toString();
//            enc_str = ECBencrypted_textview.getText().toString();
//            dec_str = dec_str.substring(0, dec_str.length() - 1 * size);
//            ECBdecrypted_textview.setText(dec_str);
//            enc_str = enc_str.substring(0, enc_str.length() - 24 * size);
//            ECBencrypted_textview.setText(enc_str);

        }
    }

    /*getter setter*/
    public View getView() {
        if (view != null) {
            return view;
        }
        return null;
    }

    public void setInputConnection(InputConnection ic) {
        if (ic != null) {
            this.ic = ic;
        }
    }

    public void setLineText(EditText lineText) {
        if (lineText != null) {
            line_text = lineText;
        }
    }

    public interface OnFragmentInterectionListener {
        void setString(String msg, int type);
    }

    public void getActivity(Activity activity) {
        this.activity = activity;
    }

}

