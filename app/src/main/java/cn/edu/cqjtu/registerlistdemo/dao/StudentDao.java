package cn.edu.cqjtu.registerlistdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.util.BitmapUtil;

/**
 * 学生操作类
 *
 * @author JohnNiang
 */
public class StudentDao extends BaseDao<Student> {

    private static final String TABLE_NAME = "Student";

    public static final String CLASS_ONE = "计科1401班";

    public static final String CLASS_TWO = "计科1402班";

    public static final String CLASS_THREE = "计科1403班";

    public static final String CLASS_FOUR = "计科1404班";

    public StudentDao(Context context) {
        super(context);
    }

    @Override
    public boolean add(Student bean) {
        ContentValues values = new ContentValues();
        values.put("sno", bean.getSno());
        values.put("sname", bean.getSname());
        values.put("sclass", bean.getSclass());
        byte[] buf = BitmapUtil.getByteArray(bean.getImage());
        if (buf != null) {
            values.put("image", buf);
        }
        values.put("score", bean.getScore());
        long result = mySqliteOpenHelper.getReadableDatabase().insert(TABLE_NAME, null, values);
        mySqliteOpenHelper.getReadableDatabase().close();
        return result != -1;
    }

    @Override
    public int delete(String sno) {
        int result = mySqliteOpenHelper.getReadableDatabase().delete(TABLE_NAME, "sno = ?", new String[]{sno});
        mySqliteOpenHelper.getReadableDatabase().close();
        return result;
    }

    @Override
    public int update(Student bean) {
        ContentValues values = new ContentValues();
        values.put("sname", bean.getSname());
        values.put("sclass", bean.getSclass());
        byte[] buf = BitmapUtil.getByteArray(bean.getImage());
        if (buf != null) {
            values.put("image", buf);
        }
        values.put("score", bean.getScore());
        int result = mySqliteOpenHelper.getReadableDatabase().update(TABLE_NAME, values, "sno = ?",
                new String[]{bean.getSno()});
        mySqliteOpenHelper.getReadableDatabase().close();
        return result;
    }

    /**
     * 更新所有学生的成绩
     *
     * @param list
     * @return
     */
    public boolean updateScore(List<Student> list) {
        boolean flag = false;

        if (list != null) {
            SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
            try {
                db.beginTransaction();
                for (Student s : list) {
                    ContentValues values = new ContentValues();
                    values.put("score", s.getScore());
                    db.update(TABLE_NAME, values, "sno = ?", new String[]{s.getSno()});
                }
                flag = true;
                db.setTransactionSuccessful();
            } finally {
                db.endTransaction();
            }
        }
        return flag;
    }

    @Override
    public List<Student> queryAll() {
        List<Student> list = null;
        Cursor cursor = mySqliteOpenHelper.getReadableDatabase().query(TABLE_NAME,
                new String[]{"sno", "sname", "sclass", "image", "score"}, null, null, null, null, "sno");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (list == null) {
                    list = new ArrayList<Student>();
                }
                byte[] buf = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                String sno = cursor.getString(cursor.getColumnIndexOrThrow("sno"));
                String sname = cursor.getString(cursor.getColumnIndexOrThrow("sname"));
                String sclass = cursor.getString(cursor.getColumnIndexOrThrow("sclass"));
                Short score = cursor.getShort(cursor.getColumnIndexOrThrow("score"));
                Bitmap bitmap = null;
                if (buf != null) {
                    bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
                }
                list.add(new Student(sno, sname, sclass, bitmap, score));
            }
        }
        cursor.close();
        mySqliteOpenHelper.getReadableDatabase().close();
        return list;
    }

    /**
     * 根据学号进行查询
     *
     * @param no
     * @return
     */
    public Student queryBySno(String no) {
        Student student = null;
        //        Cursor cursor1 = mySqliteOpenHelper.getReadableDatabase().query(TABLE_NAME,
        //                new String[]{"sno", "sname", "sclass", "image", "score"}, "sno = ?", new String[]{no}, null, null, null);

        Cursor cursor = mySqliteOpenHelper.getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE sno = ?", new String[]{no});
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToNext();
            System.out.println("Sno: " + cursor.getColumnIndexOrThrow("image"));
            System.out.println("Sno: " + cursor.getColumnIndexOrThrow("sno"));
            System.out.println("Sno: " + cursor.getColumnIndexOrThrow("sname"));
            System.out.println("Sno: " + cursor.getColumnIndexOrThrow("sclass"));
            System.out.println("Sno: " + cursor.getColumnIndexOrThrow("score"));
            byte[] buf = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
            String sno = cursor.getString(cursor.getColumnIndexOrThrow("sno"));
            String sname = cursor.getString(cursor.getColumnIndexOrThrow("sname"));
            String sclass = cursor.getString(cursor.getColumnIndexOrThrow("sclass"));
            Short score = cursor.getShort(cursor.getColumnIndexOrThrow("score"));

            Bitmap bitmap = null;
            if (buf != null) {
                bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
            }
            if (student == null) {
                student = new Student(sno, sname, sclass, bitmap, score);
            }
        }
        cursor.close();
        mySqliteOpenHelper.getReadableDatabase().close();
        return student;
    }

    public List<Student> queryByClassName(String className) {
        List<Student> list = null;
        Cursor cursor = mySqliteOpenHelper.getReadableDatabase().query(TABLE_NAME,
                new String[]{"sno", "sname", "sclass", "image", "score"}, "sclass = ?", new String[]{className}, null, null, "sno");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (list == null) {
                    list = new ArrayList<Student>();
                }
                byte[] buf = cursor.getBlob(cursor.getColumnIndexOrThrow("image"));
                String sno = cursor.getString(cursor.getColumnIndexOrThrow("sno"));
                String sname = cursor.getString(cursor.getColumnIndexOrThrow("sname"));
                String sclass = cursor.getString(cursor.getColumnIndexOrThrow("sclass"));
                Short score = cursor.getShort(cursor.getColumnIndexOrThrow("score"));
                Bitmap bitmap = null;
                if (buf != null) {
                    bitmap = BitmapFactory.decodeByteArray(buf, 0, buf.length);
                }
                list.add(new Student(sno, sname, sclass, bitmap, score));
            }
        }
        cursor.close();
        mySqliteOpenHelper.getReadableDatabase().close();
        return list;
    }
}
