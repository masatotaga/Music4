package to.msn.wings.music;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Objects;

public class MyListAdapter extends BaseAdapter {

    /*フィールド*/
    private Context context;           //コンテキスト
    private ArrayList<ListItem> data;  //リスト用データ
    private int resource;              //リスト用レイアウト

    /*コンストラクタ*/
    public MyListAdapter(Context context, ArrayList<ListItem> data, int resource) {
        this.context = context;
        this.data = data;
        this.resource = resource;
    }

    /*メソッド*/

    //データ項目の個数を取得
    @Override
    public int getCount() {
        return data.size();
    }

    //指定された項目を取得
    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    //指定された項目を識別するためのID値を取得
    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    //リスト項目を表示するためのViewを取得
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // アクティビティを取得
        Activity activity = (Activity)context;

        // 再利用可能なビューの確認
        if(convertView == null) {
            // リスト用レイアウトのオブジェクトを取得
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(resource, null);
        }

        // リスト用データを取得
        ListItem item = (ListItem)getItem(position);
        String name = item.getName();
        int icon = item.getIcon();

        // ウィジェットに値を設定（名前）
        TextView tvName = convertView.findViewById(R.id.name);
        tvName.setText(name);

        // リソースIDから画像オブジェクト(Drawable)に変換(P.355参照)
        Drawable drawable = Objects.requireNonNull(ContextCompat.getDrawable(activity, icon));

        // ウィジェットに値を設定（アイコン画像）
        ImageView ivIcon = convertView.findViewById(R.id.icon);
        ivIcon.setImageDrawable(drawable);

        // １件分のデータを返す
        return convertView;
    }
}
