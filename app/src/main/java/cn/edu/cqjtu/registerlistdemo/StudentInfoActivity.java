package cn.edu.cqjtu.registerlistdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.content.AttendenceContent;
import cn.edu.cqjtu.registerlistdemo.content.StudentContent;
import cn.edu.cqjtu.registerlistdemo.dao.StudentDao;
import cn.edu.cqjtu.registerlistdemo.dto.Dto;
import cn.edu.cqjtu.registerlistdemo.util.BitmapUtil;

public class StudentInfoActivity extends AppCompatActivity {

    public static final String ACTION = "cn.edu.cqjtu.registerlistdemo.intent.action.StudentInfoActivity";

    public static final String ARG_STR = "student_sno";

    private static final int RESULT = 1;

    private static String SNO;

    private ImageView imgHeader;

    private EditText etSname;

    private EditText etSno;

    private EditText etClass;

    private TextView tvAttendence;

    private TextView tvScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        initView();
        if(savedInstanceState!=null){
            etSno.setText(savedInstanceState.getString("sno"));
            etSname.setText(savedInstanceState.getString("sname"));
            etClass.setText(savedInstanceState.getString("sclass"));
            tvAttendence.setText(savedInstanceState.getString("attendence"));
            tvScore.setText(savedInstanceState.getString("score"));
            Bitmap bitmap = BitmapUtil.getBitmap(savedInstanceState.getByteArray("image"));
            imgHeader.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info_delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class btnDelClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.action_del) {
                // 删除次学生信息
                StudentDao dao = Dto.getDto().getStuDao();
                int result = dao.delete(SNO);
                Toast.makeText(StudentInfoActivity.this, SNO + "学生删除成功", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        } else if (id == R.id.action_del) {
            // 删除次学生信息
            new AlertDialog.Builder(this).setTitle("警告").setMessage("是否确认删除" + SNO + "学生?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    StudentDao dao = Dto.getDto().getStuDao();
                    int result = dao.delete(SNO);
                    Toast.makeText(StudentInfoActivity.this, SNO + "学生删除成功", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }
            }).setNegativeButton("返回", null).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        SNO = getIntent().getStringExtra(ARG_STR);
        System.out.println("=================" + SNO);
        StudentDao dao = Dto.getDto().getStuDao();
        Student s = dao.queryBySno(SNO);
        imgHeader = (ImageView) findViewById(R.id.imgHeader);
        etSno = (EditText) findViewById(R.id.etSno);
        etSname = (EditText) findViewById(R.id.etSname);
        etClass = (EditText) findViewById(R.id.etClass);
        tvAttendence = (TextView) findViewById(R.id.tvAttendence);
        tvScore = (TextView) findViewById(R.id.tvScore);

        if (null == s) {
            return;
        }
        imgHeader.setOnClickListener(imageListener);
        if (s.getImage() != null) {
            imgHeader.setImageBitmap(s.getImage());
        } else {
            imgHeader.setImageResource(R.mipmap.ic_account_circle_black_48dp);
        }
        if (s.getSno() != null) {
            etSno.setText(s.getSno());
        }
        if (s.getSname() != null) {
            etSname.setText(s.getSname());
        }
        if (s.getSclass() != null) {
            etClass.setText(s.getSclass());
        }
        initAttendence(s.getScore());
        if (s.getScore() != null) {
            tvScore.setText(s.getScore() + " 分");
        }
    }

    private void initAttendence(int score) {
        StringBuilder sb = new StringBuilder();
        Map<Integer, Map<String, Integer>> item_map = AttendenceContent.ITEM_MAP;
        List<List<Integer>> typeList = new ArrayList<>();
        // 初始化List
        for (int i = 0; i < 5; i++) {
            typeList.add(new ArrayList<Integer>());
        }
        for (int key : item_map.keySet()) {
            Map<String, Integer> map = item_map.get(key);
            if (map.containsKey(SNO)) {
                int type = map.get(SNO);
                typeList.get(type - 1).add(key);
            }
        }
        int type1 = typeList.get(1).size();
        if (type1 > 0) {
            sb.append("\n△  |请假: ").append(type1).append("次\n");
            sb.append("\t\t课程次:").append(typeList.get(1));
        }
        int type2 = typeList.get(2).size();
        if (type2 > 0) {
            sb.append("\n☆|早退: ").append(type2).append("次\n");
            sb.append("\t\t课程次:").append(typeList.get(2));
        }
        int type3 = typeList.get(3).size();
        if (type3 > 0) {
            sb.append("\n○ |迟到: ").append(type3).append("次\n");
            sb.append("\t\t课程次:").append(typeList.get(3));
        }
        int type4 = typeList.get(4).size();
        if (type4 > 0) {
            sb.append("\n× |旷课: ").append(type4).append("次\n");
            sb.append("\t\t课程次:").append(typeList.get(4));
        }
        if (sb.toString().isEmpty()) {
            tvAttendence.setText("\n\n状态良好，无任何记录");
        } else {
            tvAttendence.setText(sb.toString());
        }
    }

    /**
     * 图片的点击事件
     */
    private View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Dialog dialog = new AlertDialog.Builder(StudentInfoActivity.this).setTitle("从图库里选择照片").setMessage("确定要更换照片？")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Intent.ACTION_PICK,
                                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, RESULT);
                        }
                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).create();
            dialog.show();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = StudentInfoActivity.this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            System.out.println("Image path: " + picturePath);
            Bitmap mBitmap = BitmapUtil.getSmallBitmap(picturePath);
            Student newStudent = StudentContent.ITEM_MAP.get(SNO);
            newStudent.setImage(mBitmap);
            // 更新数据库
            StudentDao dao = Dto.getDto().getStuDao();
            dao.update(newStudent);
            imgHeader.setImageBitmap(mBitmap);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("sno",etSno.getText().toString());
        outState.putString("sname",etSname.getText().toString());
        outState.putString("sclass",etClass.getText().toString());
        outState.putString("attendence",tvAttendence.getText().toString());
        outState.putString("score",tvScore.getText().toString());
        imgHeader.setDrawingCacheEnabled(true);
        outState.putByteArray("image",BitmapUtil.getByteArray(imgHeader.getDrawingCache()));
        imgHeader.setDrawingCacheEnabled(false);
        super.onSaveInstanceState(outState);
    }
}
