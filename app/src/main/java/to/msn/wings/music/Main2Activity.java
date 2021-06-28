package to.msn.wings.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Main2Activity extends AppCompatActivity {

    private ImageButton b7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        b7 = (ImageButton) findViewById(R.id.button7);


        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                //mediaPlayer.stop();
                //oneTimeOnly = 0;

                //メインアクティビティ開始
                startActivity(intent);
                //メイン２アクティビティ終了
                finish();

            }
        });

    }
}
