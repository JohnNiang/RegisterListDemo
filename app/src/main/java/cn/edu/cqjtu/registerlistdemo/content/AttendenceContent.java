package cn.edu.cqjtu.registerlistdemo.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cqjtu.registerlistdemo.bean.Attendence;
import cn.edu.cqjtu.registerlistdemo.dao.AttendenceDao;
import cn.edu.cqjtu.registerlistdemo.dto.Dto;

/**
 * Created by JohnNiang on 2016/11/2.
 */

public class AttendenceContent {

    public static final List<Attendence> ITEMS = new ArrayList<>();
    public static final Map<Integer, Map<String, Integer>> ITEM_MAP = new HashMap<>();



    static {
        initItems();
    }

    private AttendenceContent() {
    }

    private static void initItems() {
        if (Dto.getDto().getmContext() != null) {
            AttendenceDao dao = new AttendenceDao(Dto.getDto().getmContext());
            Dto.getDto().setAtteDao(dao);
            refreshData();
        }
    }

    public static void refreshData() {
        // 清空操作
        if (!ITEMS.isEmpty()) {
            ITEMS.clear();
        }
        if (!ITEM_MAP.isEmpty()) {
            ITEM_MAP.clear();
        }
        List<Attendence> results = Dto.getDto().getAtteDao().queryAll();
        if (results == null) {
            // 如果为查询到则退出
            System.err.println("查询Attendence失败或者没有数据");
            return;
        }
        Map<String, Integer>[] tmpMapArray = new HashMap[Dto.MAX_ROOL_CALLNUMER];
        for (Attendence a : results) {
            ITEMS.add(a);
            int num = a.getAtteNum();
            String sno = a.getAtteSno();
            int type = Integer.parseInt(a.getAtteType()+"");
            if (ITEM_MAP.containsKey(num)) {
                // 如果该map中没有包含这次课程的就添加
                tmpMapArray[num - 1].put(sno, type);
            } else {
                tmpMapArray[num - 1] = new HashMap<>();
                ITEM_MAP.put(num, tmpMapArray[num - 1]);
            }
        }
    }

    /**
     * 获得数据库中最大的课程次
     * @return
     */
    public static int getMaxRoolCallNumberInDB() {
        int max = 0;
        for (int key :
                AttendenceContent.ITEM_MAP.keySet()) {
            if (key > max) {
                max = key;
            }
        }
        return max;
    }
}
