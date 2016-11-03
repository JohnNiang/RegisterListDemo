package cn.edu.cqjtu.registerlistdemo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.util.DatabaseUtil;

public class MySqliteOpenHelper extends SQLiteOpenHelper {

	private static final String CREAT_TABLE_STUDENT = "create table Student (        "
			+ "sno                	char(12) primary key not null,                   "
			+ "sname                varchar(20)          null,                       "
			+ "sclass               varchar(40)          null,         				 "
			+ "image				blob				 null,                       "
			+ "score                tinyint              null                       )";

	private static final String CREAT_TABLE_ATTENDENCE = "create table Attendence (             "
			+ "atteId  				integer primary key autoincrement  not null,                "
			+ "atteSno              char(12)             null,                                  "
			+ "atteType             char(1)              null,                                  "
			+ "atteNum              int                  null                                  )";

	private Context mContext;

	public MySqliteOpenHelper(Context context) {
		super(context, "register.db", null, 1);
		this.mContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		System.out.println("开始创建register.db数据库");
		db.execSQL(CREAT_TABLE_STUDENT);
		db.execSQL(CREAT_TABLE_ATTENDENCE);
		System.out.println("创建register.db成功");

		// ��ʼ������
		System.out.println("初始化数据...");
		List<Student> list = DatabaseUtil.getStudentsFromFile(mContext, "data.csv", ',');
		if (list != null) {
			for (Student s : list) {
				db.execSQL("INSERT INTO Student(sno,sname,sclass,score) VALUES(?,?,?,?)",
						new Object[] { s.getSno(), s.getSname(), s.getSclass(), s.getScore() });
			}
		}
		System.out.println("初始化数据成功");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
	}

}
