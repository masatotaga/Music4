package to.msn.wings.music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ListDataSource {

    public static final int[] ids =
            {
              0,1,2,3,4,5,6,7,8,9
            };



    public static final int[] icons =
            {
                    R.drawable.jazz_in_paris, R.drawable.the_coldest_shoulder,
                    R.drawable.croladuex, R.drawable.deep_night,
                    R.drawable.feeling_blue, R.drawable.house_party,
                    R.drawable.million_try, R.drawable.monday8am,
                    R.drawable.moonlit, R.drawable.somebody_else
            };

    public static final int[] song_id =
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

            };


    public static final ArrayList<String> names = new ArrayList<String>() {
        {
            add("jazz_in_paris");
            add("the_coldest_shoulder");
            add("croladuex");
            add("deep_night");
            add("feeling_blue");
            add("house_party");
            add("million_try");
            add("monday_8am");
            add("moonlit");
            add("somebody_else");
        }
    };
    /*private static final ArrayList<String> alias = new ArrayList<String>() {
        {
            add("pepper");
            add("turmeric");
            add("coriander");
            add("ginger");
            add("garlic");
            add("saffron");
        }
    };
    private static final ArrayList<String> info = new ArrayList<String>() {
        {
            add("コショウ科コショウ属でつる性植物です。黒胡椒、白胡椒、青胡椒、赤胡椒などがあります。");
            add("ショウガ科ウコン属で多年草です。「ウコン」とも呼ばれます。");
            add("セリ科の一年草です。「パクチー」「香菜（シャンツァイ）」「中国パセリ」とも呼ばれます。");
            add("ショウガ科の多年草です。野菜として料理に使ったり、薬としても使用されます。");
            add("ヒガンバナ科ネギ属の多年草です。日本では、2月29日がニンニクの日です。");
            add("アヤメ科の多年草です。めしべを乾燥させた香辛料を、パエリアなどの料理で使います。");
            add
        }
    };*/

    public static int[] getAllId() {
        return ids;
    }

    public static ArrayList<String> getAllNames() {
        return names;
    }

    public static int[] getAllIcons() {
        return icons;
    }

    public static int[] getAllSong_id() {
        return song_id;
    }


    /*public static List<Map<String, String>> getAll() {
        List<Map<String, String>> result = new ArrayList<>();
        for(int i = 0; i < name.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("name", name.get(i));
            map.put("alias", );
            map.put("info", info.get(i));
            result.add(map);
        }
        return result;
    }

    static Map<String, String> getInfoByName(String key) {
        int index = name.indexOf(key);
        Map<String, String> map = new HashMap<>();
        map.put("name", name.get(index));
        map.put("alias", alias.get(index));
        map.put("info", info.get(index));
        return map;
    }*/



        static Map<String, Integer> getInfoByName(String key) {
            int index = names.indexOf(key);
            Map<String, Integer> map = new HashMap<>();
            map.put("id", ids[index]);
            map.put("icon", icons[index]);
            map.put("song_id", song_id[index]);

            return map;
        }

    static Map<String, Integer> getNextInfoByName(String key) {
        int index = names.indexOf(key) +1;
        Map<String, Integer> map = new HashMap<>();
        map.put("id", ids[index]);
        map.put("icon", icons[index]);
        map.put("song_id", song_id[index]);

        return map;
    }

    static int index(String key){
        int index = names.indexOf(key) +1;
        return index;
    }
}
