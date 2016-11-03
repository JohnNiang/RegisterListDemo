package cn.edu.cqjtu.registerlistdemo.content;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.dao.StudentDao;

import static cn.edu.cqjtu.registerlistdemo.dto.Dto.getDto;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class StudentContent {

    /**
     * An array of sample (dummy) items.
     */
    public final static List<Student> ITEMS = new ArrayList<Student>();
    public final static List<Student> ITEMS_CLASS_ONE = new ArrayList<Student>();
    public final static List<Student> ITEMS_CLASS_TWO = new ArrayList<Student>();
    public final static List<Student> ITEMS_CLASS_THREE = new ArrayList<Student>();
    public final static List<Student> ITEMS_CLASS_FOUR = new ArrayList<Student>();

    public static final List<String> CLASSES = new ArrayList<>();


    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, Student> ITEM_MAP = new HashMap<String, Student>();

    static {
        initItems();
    }

    private static void initItems() {
        refreshData();
    }


    public static void refreshData() {
        // 清空操作
        if (!ITEMS.isEmpty()) {
            ITEMS.clear();
        }
        if (!ITEMS_CLASS_ONE.isEmpty()) {
            ITEMS_CLASS_ONE.clear();
        }
        if (!ITEMS_CLASS_TWO.isEmpty()) {
            ITEMS_CLASS_TWO.clear();
        }
        if (!ITEMS_CLASS_THREE.isEmpty()) {
            ITEMS_CLASS_THREE.clear();
        }
        if (!ITEMS_CLASS_FOUR.isEmpty()) {
            ITEMS_CLASS_FOUR.clear();
        }
        if (!CLASSES.isEmpty()) {
            CLASSES.clear();
        }
        if (!ITEM_MAP.isEmpty()) {
            ITEM_MAP.clear();
        }
        if (getDto().getmContext() != null) {
            StudentDao dao = new StudentDao(getDto().getmContext());
            getDto().setStuDao(dao);
            List<Student> resultList = dao.queryAll();
            // 初始化StudentMap，及各种班级分类List
            for (Student s : resultList) {
                ITEMS.add(s);
                ITEM_MAP.put(s.getSno(), s);
                switch (s.getSclass()) {
                    case StudentDao.CLASS_ONE:
                        ITEMS_CLASS_ONE.add(s);
                        break;
                    case StudentDao.CLASS_TWO:
                        ITEMS_CLASS_TWO.add(s);
                        break;
                    case StudentDao.CLASS_THREE:
                        ITEMS_CLASS_THREE.add(s);
                        break;
                    case StudentDao.CLASS_FOUR:
                        ITEMS_CLASS_FOUR.add(s);
                        break;
                }
                if (!CLASSES.contains(s.getSclass())) {
                    CLASSES.add(s.getSclass());
                }
            }
            // 对classlist进行排序
            Collections.sort(CLASSES, new ListComparator());
            StudentListComparator comparator = new StudentListComparator();
            Collections.sort(ITEMS,comparator);
            Collections.sort(ITEMS_CLASS_ONE,comparator);
            Collections.sort(ITEMS_CLASS_TWO,comparator);
            Collections.sort(ITEMS_CLASS_THREE,comparator);
            Collections.sort(ITEMS_CLASS_FOUR,comparator);
        }
    }

    private static class StudentListComparator implements Comparator<Student> {

        @Override
        public int compare(Student student, Student t1) {
            return student.getSno().compareTo(t1.getSno());
        }
    }

    private static class ListComparator implements Comparator<String> {

        @Override
        public int compare(String s, String t1) {
            return s.compareTo(t1);
        }
    }
}
