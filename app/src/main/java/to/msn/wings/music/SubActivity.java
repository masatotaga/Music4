package to.msn.wings.music;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.media.MediaPlayer;
import android.os.Handler;

import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import androidx.appcompat.app.AppCompatActivity;

import static to.msn.wings.music.ListDataSource.getAllIcons;
import static to.msn.wings.music.ListDataSource.getAllId;
import static to.msn.wings.music.ListDataSource.getAllNames;
import static to.msn.wings.music.ListDataSource.getAllSong_id;

public class SubActivity extends AppCompatActivity {

    private Button b1,b2,b3,b4,btnNext,btnBack;
    private ImageView iv;
    private static MediaPlayer mediaPlayer; //変更

    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();;
    private int forwardTime = 5000;
    private int backwardTime = 5000;
    private SeekBar seekbar;
    private TextView tx1,tx2,tx3;

    private int id;
    private int icon;
    private int song_id;
    private String song;
    private int[] song_ids;
    private int[] icons;
    private ArrayList<String> names;
    private boolean check = false;
    private RotateAnimation rotate;

    //public static int oneTimeOnly = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        //インテントからデータを受け取る
        Intent intent = getIntent();
        //song = intent.getStringExtra("song");
        id = intent.getIntExtra("id2",0);
        //int song_id = intent.getIntExtra("id", 0);
        //int icon = intent.getIntExtra("icon", 0);

        //ListDataSourceからデータを取得する
        song_ids = getAllSong_id();
        icons = getAllIcons();
        names = getAllNames();

        //idを利用して取得
        icon = icons[id];
        song_id = song_ids[id];
        song = names.get(id);

        /*Map<String, Integer> map = ListDataSource.getInfoByName(song);
         icon = map.get("icon");
         song_id = map.get("song_id");*/

        //RETURNボタンを押したとき
        Button returnButton = findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);

                //メインアクティビティ開始
                startActivity(intent);
                //サブアクティビティ終了
                finish();

            }
       });

        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button)findViewById(R.id.button3);
        b4 = (Button)findViewById(R.id.button4);
        btnNext = (Button)findViewById(R.id.button5);
        btnBack = (Button)findViewById(R.id.button6);
        iv = (ImageView)findViewById(R.id.imageView);

        tx1 = (TextView)findViewById(R.id.textView2);
        tx2 = (TextView)findViewById(R.id.textView3);
        tx3 = (TextView)findViewById(R.id.textView4);

        //選択された項目の曲名とアイコンセット
        tx3.setText(song);
        iv.setImageResource(icon);

        //SeekBarの設定
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        seekbar.setClickable(true);
        b2.setEnabled(false);

        //アニメーションの設定
        rotate = new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());
        rotate.setRepeatCount(Animation.INFINITE);

        //再生ボタンを押したとき
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //MediaPlayerの設定
                if(mediaPlayer != null && check == false){
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = MediaPlayer.create(getApplicationContext(), song_id);
                    check = true;
                }else {
                    if(check == false){
                        mediaPlayer = MediaPlayer.create(getApplicationContext(), song_id);
                        check =true;
                    }

                }

                mediaPlayer.start();

                finalTime = mediaPlayer.getDuration();
                startTime = mediaPlayer.getCurrentPosition();

                seekbar.setMax((int) finalTime);

                tx2.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        finalTime)))
                );

                tx1.setText(String.format("%d min, %d sec",
                        TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                        TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                        startTime)))
                );


                seekbar.setProgress((int)startTime);
                myHandler.postDelayed(UpdateSongTime,100);
                //一時停止ボタンを使用可能にする
                b2.setEnabled(true);
                //再生ボタンを使用不可にする
                b3.setEnabled(false);

                //アニメーション開始
                iv.startAnimation(rotate);

                //曲が終わったら
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        Log.d("debug", "end of audio");
                        //ボタンなし再生イベント
                        sai();
                    }
                });
            }
        });

        //一時停止ボタンを押したとき
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.pause();

                //一時停止ボタンを使用不可
                b2.setEnabled(false);
                //再生ボタンを使用可能にする
                b3.setEnabled(true);
                //アニメーションストップ
                iv.clearAnimation();

            }
        });

        //5秒進むボタンを押したとき
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp+forwardTime)<=finalTime){
                    startTime = startTime + forwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump forward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //5秒戻るボタンを押したとき
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int temp = (int)startTime;

                if((temp-backwardTime)>0){
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo((int) startTime);
                }else{
                    Toast.makeText(getApplicationContext(),"Cannot jump backward 5 seconds",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //次へボタンを押したとき
        btnNext.setOnClickListener
                (
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                sai();
                            }
                        }
                );

        //戻るボタンを押したとき
        btnBack.setOnClickListener
                (
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                                back();
                            }
                        }
                );

    //SeekBarを移動した位置から音楽を再生する処理
    //GUIを操作するスレッド？
    SubActivity.this.runOnUiThread
            (
                    new Runnable() {
                        @Override
                        public void run() {
                            if(finalTime != 0){
                                int CurrentPosition = mediaPlayer.getCurrentPosition();
                                seekbar.setProgress(CurrentPosition);
                            }
                             myHandler.postDelayed(this, 1000);
                         }
                    }
                );

    //SeekBarを移動したとき
        seekbar.setOnSeekBarChangeListener
                (
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if(mediaPlayer != null && fromUser){
                              mediaPlayer.seekTo(progress);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
                );
    }


    public void sai(){
        if(id == song_ids.length-1){
            id = 0;
            song_id = song_ids[0];
            icon = icons[0];
            song = names.get(0);
            mediaPlayer = MediaPlayer.create(getApplicationContext(),song_id);
            iv.setImageResource(icon);
            tx3.setText(song);
        }else{
            id++;
            song_id = song_ids[id];
            icon = icons[id];
            song = names.get(id);

            mediaPlayer = MediaPlayer.create(getApplicationContext(),song_id);
            iv.setImageResource(icon);
            tx3.setText(song);
        }
        b3.performClick();
    }

    public void back(){
        if(id == 0){
            id = song_ids.length-1;
            song_id = song_ids[song_ids.length-1];
            icon = icons[song_ids.length-1];
            song = names.get(song_ids.length-1);
            mediaPlayer = MediaPlayer.create(getApplicationContext(),song_id);
            iv.setImageResource(icon);
            tx3.setText(song);
        }else{
            id--;
            song_id = song_ids[id];
            icon = icons[id];
            song = names.get(id);

            mediaPlayer = MediaPlayer.create(getApplicationContext(),song_id);
            iv.setImageResource(icon);
            tx3.setText(song);
        }
        b3.performClick();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaPlayer.getCurrentPosition();
            tx1.setText(String.format("%d min, %d sec",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };


}
