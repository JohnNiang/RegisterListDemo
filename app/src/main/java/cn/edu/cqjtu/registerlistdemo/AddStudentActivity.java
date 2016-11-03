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
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.content.StudentContent;
import cn.edu.cqjtu.registerlistdemo.dao.StudentDao;
import cn.edu.cqjtu.registerlistdemo.dto.Dto;
import cn.edu.cqjtu.registerlistdemo.util.BitmapUtil;

public class AddStudentActivity extends AppCompatActivity {

    public static final String ACTION_NAME = "cn.edu.cqjtu.registerlistdemo.intent.action.AddStudentActivity";
    private static final int RESULT = 1;
    private String className = "";
    private List<String> classList;

    private Bitmap mBitmap;

    private EditText etSno, etSname;

    private Spinner mSpinnerClass;

    private Button btnAdd, btnCancel;

    private ImageView imgHeader;

    private boolean imgChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        ActionBar bar = getSupportActionBar();
        bar.setDisplayHomeAsUpEnabled(true);
        init();
        clearState();
    }

    private void init() {
        classList = StudentContent.CLASSES;
        if (!classList.contains("请选择班级")) {
            classList.add(0, "请选择班级");
        }
        // TODO 对其进行排序
        etSno = (EditText) findViewById(R.id.etSno);
        etSname = (EditText) findViewById(R.id.etSname);
        mSpinnerClass = (Spinner) findViewById(R.id.spinnerClass);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, classList);
        mSpinnerClass.setAdapter(adapter);
        mSpinnerClass.setOnItemSelectedListener(mSpinnerOnItemSelectedlistener);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        imgHeader = (ImageView) findViewById(R.id.imgHeader);
        imgHeader.setOnClickListener(imageListener);
        // mBitmap = BitmapUtil.drawableToBitmap(imgHeader.getDrawable());
        btnAdd.setOnClickListener(mButtonAddListener);
        btnCancel.setOnClickListener(mButtonCancelListener);
    }

    private AdapterView.OnItemSelectedListener mSpinnerOnItemSelectedlistener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            className = classList.get(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    private void clearState() {
        imgChanged = false;
        etSno.setText("");
        etSname.setText("");
        className = classList.get(0);
        imgHeader.setImageResource(R.mipmap.ic_account_circle_black_48dp);
        mSpinnerClass.setSelection(0);
    }


    private View.OnClickListener mButtonAddListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final String sno = etSno.getText().toString().trim();
            if (sno.length() != 12) {
                Toast.makeText(AddStudentActivity.this, "学号不能为12位", Toast.LENGTH_SHORT).show();
                return;
            }
            final String sname = etSname.getText().toString().trim();
            if(sname.equals(""))
            {
                Toast.makeText(AddStudentActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!sno.equals("") && !sname.equals("") &&   !className.equals(classList.get(0))) {
                if (!imgChanged) {
                    new AlertDialog.Builder(AddStudentActivity.this).setTitle("温馨提示").setMessage("您未修改图片，是否将此默认图片作为头像").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Student newStu = null;
                            newStu = new Student(sno, sname, className, null, (short) 100);
                            StudentDao dao = Dto.getDto().getStuDao();
                            if (dao.add(newStu)) {
                                Toast.makeText(AddStudentActivity.this, "添加" + sname + "同学成功", Toast.LENGTH_SHORT).show();
                                clearState();
                            } else {
                                Toast.makeText(AddStudentActivity.this, "添加" + sname + "同学失败,可能已存在相同的学号", Toast.LENGTH_LONG).show();
                            }
                        }
                    }).setNegativeButton("返回", null).show();
                } else {
                    Student newStu = new Student(sno,sname, className, mBitmap, (short) 100);
                    StudentDao dao = Dto.getDto().getStuDao();
                    if (dao.add(newStu)) {
                        Toast.makeText(AddStudentActivity.this, "添加" + sname + "同学成功", Toast.LENGTH_SHORT).show();
                        clearState();
                    } else {
                        Toast.makeText(AddStudentActivity.this, "添加" + sname + "同学失败,可能已存在相同的学号", Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(AddStudentActivity.this, "请完善相关学生信息", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener mButtonCancelListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            onBackPressed();
        }
    };

    private boolean checkTextChanged() {
        String sno = etSno.getText().toString().trim();
        String sname = etSname.getText().toString().trim();
        return (sno != null && !sno.equals("")) || (sname != null && !sname.equals(""))|| !className.equals(classList.get(0));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (checkTextChanged() || imgChanged) {
            new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("您修改的内容还未提交，是否确认退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("返回", null).show();
        }else{
            finish();
        }
    }

    /**
     * 图片的点击事件
     */
    private View.OnClickListener imageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Dialog dialog = new AlertDialog.Builder(AddStudentActivity.this).setTitle("从图库里选择照片").setMessage("确定要更换照片？")
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
            Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            System.out.println("Image path: " + picturePath);
            mBitmap = BitmapUtil.getSmallBitmap(picturePath);
            imgChanged = true;
            imgHeader.setImageBitmap(mBitmap);
        }
    }
}
