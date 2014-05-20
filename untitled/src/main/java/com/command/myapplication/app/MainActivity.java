package com.command.myapplication.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends Activity {
    Button showAlert,startOver,showInfo;
    Dialog dialog;
    TextView roundtext,scoretext,targettext;
    SeekBar seekBar;
    int targetValue,score,round;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
//全屏        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar)findViewById(R.id.seekBar);
        showAlert = (Button)findViewById(R.id.showAlert);
        startOver = (Button)findViewById(R.id.startOver);
        showInfo = (Button)findViewById(R.id.showInfo);
        roundtext = (TextView)findViewById(R.id.roundtext);
        scoretext = (TextView)findViewById(R.id.scoretext);
        targettext = (TextView)findViewById(R.id.targettext);
        startNewGame();
        showAlert.setOnClickListener(new OnClickListenerYes());
        startOver.setOnClickListener(new StartNewGame());
        showInfo.setOnClickListener(new showInfo());
    }

    private class OnClickListenerYes implements OnClickListener {
        @Override
        public void onClick(View view) {
            int sliderMoved = seekBar.getProgress();
            sliderMoved +=1;//设置为0-99，要＋1
            round ++;
            String str;
            int difference = Math.abs(targetValue-sliderMoved);//取绝对值
            int points = 100-difference;
            if (difference == 0) {
                points = 200;
                str = "完美";
            }else if (difference < 5) {
                points = 95;
                str = "差一点";
            }else {
                str = "再来一次吧";
            }
            dialog = new AlertDialog.Builder(MainActivity.this)
                    .setTitle(str)
                    .setMessage("你的数值是："+sliderMoved+"你的成绩是："+points)
//                .setPositiveButton("确定", (android.content.DialogInterface.OnClickListener) ocl)
                    .create();
            dialog.show();
            score += points;
            startNewround();
            updatetextView();
        }
    }

    private class StartNewGame implements OnClickListener {
        @Override
        public void onClick(View view) {
            startNewGame();
        }
    }

    private class showInfo implements OnClickListener {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this,InfoActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }

    private void startNewround() {
        targetValue = (int)Math.round(Math.random()*99+1);//取得1-100的随机数
        String s = String.valueOf(targetValue);
        targettext.setText(s);
    }

    private void updatetextView() {
        roundtext.setText(String.valueOf(round));
        scoretext.setText(String.valueOf(score));
    }

    private void startNewGame() {
        round = 0;
        score = 0;
        updatetextView();
        startNewround();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
