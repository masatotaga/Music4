package to.msn.wings.music;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int[]ids = ListDataSource.getAllId();
        ArrayList<String> names = ListDataSource.getAllNames();
        int[]icons = ListDataSource.getAllIcons();
        int[]song_id = ListDataSource.getAllSong_id();


        //ArrayListにリスト用データを詰め替える
        ArrayList<ListItem> data = new ArrayList<>();
        for(int i = 0; i < names.size(); i++){


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
                                
                                //データを追加
                                intent.putExtra("id2", id2);
                                
                                //サブアクティビティ開始
                                startActivity(intent);
                                //メインアクティビティ終了
                                finish();
                            }
                        }
                );
    }
}
