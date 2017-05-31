package com.example.baijunfeng.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * MainActivity for this Application.
 * 本应用主要测试使用部分java和android M和N的新特性和新组件，包括但不限于：
 * 1. Lambda Expression, java 1.8 的一种新的表达式，主要用来简化function interface的实现
 * 2. ConstraintLayout, Android 7.0 的一种新的关联性布局，可以是的各个组件相互依赖，方便组件的布局以及同步移动
 * 3. TabLayout, 实现tab布局
 * 4. AppBarLayout, LinearLayout子类，主要用来布局ToolBar，可以包含CollapsingToolbarLayout，Toolbar，TabLayout等
 * 5. CoordinatorLayout, 是一个增强型的FrameLayout，可以作为一个布局的根布局，可以作为一个为子视图之间相互协调手势效果的协调布局
 * 6. CollapsingToolbarLayout, 包裹 Toolbar 的时候提供一个可折叠的 Toolbar，一般作为AppBarLayout的子布局
 * 7. FloatingActionButton,
 * 8. Snackbar, 介于Toast和AlertDialog之间轻量级控件
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File baseDir = getExternalCacheDir();
        Log.d("baijf1", "File path = " + baseDir.toString());
//        File file = new File();

        findViewById(R.id.button).setOnClickListener(
                (v) -> {
                    Intent intent = new Intent(this, AppBarLayoutActivity.class);
                    startActivity(intent);
                }
        );

        //Snackbar Test
        findViewById(R.id.button2).setOnClickListener(
//                (v) -> SnackBarTest(v)
                (v) -> {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                getJson(new URL("https://yaoguang8816.github.io/json/json_test.xml"));
                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }
        );

        findViewById(R.id.button3).setOnClickListener(
                v -> {
                    Intent intent = new Intent(this, TabActivity.class);
                    startActivity(intent);
                }
        );

        findViewById(R.id.button4).setOnClickListener(
                v -> {
                    Intent intent = new Intent(this, CollapsingToolbarAndScrollingActivity.class);
                    startActivity(intent);
                }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        File baseDir = getExternalCacheDir();
        Log.d("baijf1", "File path = " + baseDir.toString());
        File file = getFilesDir();
        Log.d("baijf1", "File path = " + file.toString());

        TextInputLayoutTest();
    }

    /*
     * TextInputLayout 不仅能让EditView的提示语上弹显示在EditView之上(不消失)，而且还能把错误提示信息显示在EditView之下。
     * TextInputLayout不能单独使用，需要包裹EditView组件。
     */
    private void TextInputLayoutTest() {
        final TextInputLayout inputLayout = (TextInputLayout)findViewById(R.id.textInput);
        inputLayout.setHint("请输入姓名:");

        EditText editText = inputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>4){
                    inputLayout.setErrorEnabled(true);
                    inputLayout.setError("姓名长度不能超过4个");
                }else{
                    inputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    /*
     * Snackbar提供了一个介于Toast和AlertDialog之间轻量级控件，它可以很方便的提供消息的提示和动作反馈
     * 第一个参数View 可以是当前父布局中的任何一个view对象都可以
     * Snackbar的使用和Toast很类似
     * Snackbar是从整个界面的底部弹出
     */
    private void SnackBarTest(View v) {
        Snackbar snackBar = Snackbar.make(v, "Snackbar 测试", Snackbar.LENGTH_LONG);
        snackBar.show();
        snackBar.setAction("cancle", view -> {
            snackBar.setText("Snackbar 点击事件发生");
        });
        snackBar.setActionTextColor(0xaa0f7766);
    }

    void getJson(URL url) {
        try {
            HttpURLConnection httpconn = (HttpURLConnection) url
                    .openConnection();
            InputStreamReader inputReader = new InputStreamReader(httpconn
                    .getInputStream());

            BufferedReader buffReader = new BufferedReader(inputReader);

            String line = "";
            String JsonStr = "";

            while ((line = buffReader.readLine()) != null) {
//                lineIndex++;
                JsonStr += line;

            }
            Log.d("baijf1", JsonStr);
//            resolveJson(JsonStr);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
