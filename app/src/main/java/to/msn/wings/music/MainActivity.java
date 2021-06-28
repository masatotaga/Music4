package to.msn.wings.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Button sendButton = findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), SubActivity.class);
                startActivity(intent);
            }
        });*/


        //リスト用データを配列で準備
        /*String[] names =
                {
                        "jazz_in_paris", "the_coldest_shoulder", "おおさそり", "がいこつ", "メーダー",
                        "リカント", "キメラ", "よろいのきし", "ドラゴン", "ゴーレム"
                };*/
        /*String[] names =
                {
                        "jazz_in_paris", "the_coldest_shoulder", "croladuex", "deep_night", "feeling_blue",
                        "house_party", "million_try", "monday_8am", "moonlit", "somebody_else"
                };

        int[] icons =
                {
                        R.drawable.jazz_in_paris, R.drawable.the_coldest_shoulder,
                        R.drawable.croladuex, R.drawable.deep_night,
                        R.drawable.feeling_blue, R.drawable.house_party,
                        R.drawable.million_try, R.drawable.monday8am,
                        R.drawable.moonlit, R.drawable.somebody_else
                };

       int[] song_id =
               {
                       R.raw.jazz_in_paris,
                       R.raw.the_coldest_shoulder,
                       R.raw.croladuex,
                       R.raw.deep_night,
                       R.raw.feeling_blue,
                       R.raw.house_party,
                       R.raw.million_try,
                       R.raw.monday_8am,
                       R.raw.moonlit,
                       R.raw.somebody_else

               };*/

        int[]ids = ListDataSource.getAllId();
        ArrayList<String> names = ListDataSource.getAllNames();
        int[]icons = ListDataSource.getAllIcons();
        int[]song_id = ListDataSource.getAllSong_id();


        //ArrayListにリスト用データを詰め替える
        ArrayList<ListItem> data = new ArrayList<>();
        for(int i = 0; i < names.size(); i++){

            /*//乱数を使ってIDを取得
            Random rnd = new Random();
            long id = rnd.nextLong();*/


            //１件分のデータを設定
            ListItem item = new ListItem();
            item.setId(ids[i]);          //ID
            item.setName(names.get(i));  //名前
            item.setIcon(icons[i]);  //アイコン画像
            item.setSong_id(song_id[i]);

            //ArrayListに追加
            data.add(item);
        }

        //リスト用データとリスト用レイアウトをアダプターに設定
        MyListAdapter adapter = new MyListAdapter(this, data, R.layout.list_item);

        //ListViewにアダプターを登録
        ListView list = findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener
                (
                        new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(getApplication(), SubActivity.class);
                                ListView list = findViewById(R.id.list);
                                ListItem s = (ListItem) list.getItemAtPosition(position);
                                int id2 = s.getId();
                                int pos = position;
                                //int song_id = s.getSong_id();
                                //int icon = s.getIcon();

                                intent.putExtra("id2", id2);
                                //intent.putExtra("id", song_id);
                                //intent.putExtra("icon", icon);
                                //サブアクティビティ開始
                                startActivity(intent);
                                //メインアクティビティ終了
                                finish();
                            }
                        }
                );
    }
}
