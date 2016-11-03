package cn.edu.cqjtu.registerlistdemo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqjtu.registerlistdemo.bean.Attendence;

/**
 * 考勤类
 *
 * @author JohnNiang
 */
public class AttendenceDao extends BaseDao<Attendence> {

    private static final String TABLE_NAME = "Attendence";

    public AttendenceDao(Context context) {
        super(context);
    }

    @Override
    public boolean add(Attendence bean) {
        ContentValues values = new ContentValues();
        values.put("atteSno", bean.getAtteSno());
        values.put("atteType", "" + bean.getAtteType());
        values.put("atteNum", bean.getAtteNum());
        long result = mySqliteOpenHelper.getReadableDatabase().insert(TABLE_NAME, null, values);
        mySqliteOpenHelper.getReadableDatabase().close();
        return result != -1;
    }

    /**
     * 插入批量数据，利用事务提高插入效率
     *
     * @param list 需要插入的考勤的集合
     * @return ture，插入成功，否则插入失败
     */
    public boolean addAll(List<Attendence> list) {
        boolean flag = false;
        if (list != null) {
            SQLiteDatabase db = mySqliteOpenHelper.getReadableDatabase();
            try {
                db.beginTransaction();
                // 这里进行循环操作
                for (Attendence atte : list) {
                    ContentValues values = new ContentValues();
                    values.put("atteSno", atte.getAtteSno());
                    values.put("atteType", "" + atte.getAtteType());
                    values.put("atteNum", atte.getAtteNum());
                    db.insert(TABLE_NAME, null, values);
                }
                db.setTransactionSuccessful();
                flag=true;
            } finally {
                db.endTransaction();
                db.close();
            }
        }
        return flag;
    }

    @Override
    public int delete(String id) {
        int result = mySqliteOpenHelper.getReadableDatabase().delete(TABLE_NAME, "atteId = ?", new String[]{id});
        mySqliteOpenHelper.getReadableDatabase().close();
        return result;
    }

    @Override
    public int update(Attendence bean) {
        ContentValues values = new ContentValues();
        // values.put("atteId", bean.getAtteId());
        values.put("atteSno", bean.getAtteSno());
        values.put("atteType", "" + bean.getAtteType());
        values.put("atteNum", bean.getAtteNum());
        int result = mySqliteOpenHelper.getReadableDatabase().update(TABLE_NAME, values, "atteId = ?", new String[]{});
        mySqliteOpenHelper.getReadableDatabase().close();
        return result;
    }

    @Override
    public List<Attendence> queryAll() {
        List<Attendence> list = null;
        Cursor cursor = mySqliteOpenHelper.getReadableDatabase().query(TABLE_NAME,
                new String[]{"atteId", "atteSno", "atteType", "atteNum"}, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (list == null) {
                    list = new ArrayList<Attendence>();
                }
                Integer atteId = cursor.getInt(cursor.getColumnIndex("atteId"));
                String atteSno = cursor.getString(cursor.getColumnIndex("atteSno"));
                Character atteType = cursor.getString(cursor.getColumnIndex("atteType")).charAt(0);
                Integer atteNum = cursor.getInt(cursor.getColumnIndex("atteNum"));
                list.add(new Attendence(atteId, atteSno, atteType, atteNum));
            }
        }
        cursor.close();
        mySqliteOpenHelper.getReadableDatabase().close();
        return list;
    }

    /**
     * 根据学号查询学生的所有的考勤
     *
     * @param sno 学号
     * @return 该学生的所有考勤
     */
    public List<Attendence> queryBySno(String sno) {
        if (sno == null) {
            return null;
        }
        List<Attendence> list = null;
        Cursor cursor = mySqliteOpenHelper.getReadableDatabase().query(TABLE_NAME,
                new String[]{"atteId", "atteSno", "atteType", "atteNum"}, "atteSno = ? ", new String[]{sno}, null,
                null, "atteNum", null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                if (list == null) {
                    list = new ArrayList<Attendence>();
                }
                Integer atteId = cursor.getInt(cursor.getColumnIndex("atteId"));
                String atteSno = cursor.getString(cursor.getColumnIndex("atteSno"));
                Character atteType = cursor.getString(cursor.getColumnIndex("atteType")).charAt(0);
                Integer atteNum = cursor.getInt(cursor.getColumnIndex("atteNum"));
                list.add(new Attendence(atteId, atteSno, atteType, atteNum));
            }
        }
        return list;
    }

}
