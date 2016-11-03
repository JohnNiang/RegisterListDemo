package cn.edu.cqjtu.registerlistdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import cn.edu.cqjtu.registerlistdemo.CheckActivity;
import cn.edu.cqjtu.registerlistdemo.R;
import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.dao.OnCheckListInteractionListener;

/**
 * Created by JohnNiang on 2016/11/2.
 */

public class MyCheckRecyclerViewAdapter extends RecyclerView.Adapter<MyCheckRecyclerViewAdapter.ViewHolder> {

    private final List<Student> mValues;
    private final OnCheckListInteractionListener listener;

    public MyCheckRecyclerViewAdapter(List<Student> item, OnCheckListInteractionListener listener) {
        mValues = item;
        for (Student s:item){
            CheckActivity.ATTENDENCE_MAP.put(s.getSno(),1);
        }
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.check_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Student item = mValues.get(position);
        holder.mView.setAnimation(AnimationUtils.loadAnimation(holder.mView.getContext(),R.anim.scale_animation));
        holder.mItem = item;
        if (item.getImage() != null) {
            holder.mImageViewHeader.setImageBitmap(item.getImage());
        } else {
            holder.mImageViewHeader.setImageResource(R.mipmap.ic_account_circle_black_48dp);
        }
        if (item.getSno() != null) {
            holder.mTextViewSno.setText(item.getSno());
        } else {
            holder.mTextViewSno.setText("Error No!");
        }
        if (item.getSname() != null) {
            holder.mTextViewSname.setText(item.getSname());
        } else {
            holder.mTextViewSname.setText("Error Name");
        }
        int type = CheckActivity.ATTENDENCE_MAP.get(holder.mItem.getSno());
        holder.rbs[type-1].setChecked(true);
        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                int type = 0;
                switch (checkedId){
                    case R.id.rbPresent:
                        type =1;
                        break;
                    case R.id.rbAskForLeave:
                        type =2;
                        break;
                    case R.id.rbLeaveEarlier:
                        type = 3;
                        break;
                    case R.id.rbLate:
                        type = 4;
                        break;
                    case R.id.rbAbsent:
                        type = 5;
                        break;
                }
                listener.onCheckListInteraction(holder.mItem.getSno(), type);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mImageViewHeader;
        public final TextView mTextViewSno;
        public final TextView mTextViewSname;
        public final RadioGroup radioGroup;
        public final RadioButton[] rbs;
        public Student mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageViewHeader = (ImageView) view.findViewById(R.id.imgHeader);
            mTextViewSname = (TextView) view.findViewById(R.id.tvSname);
            mTextViewSno = (TextView) view.findViewById(R.id.tvSno);
            rbs = new RadioButton[5];
            rbs[0] = (RadioButton) view.findViewById(R.id.rbPresent);
            rbs[1] = (RadioButton) view.findViewById(R.id.rbAskForLeave);
            rbs[2] = (RadioButton) view.findViewById(R.id.rbLeaveEarlier);
            rbs[3] = (RadioButton) view.findViewById(R.id.rbLate);
            rbs[4] = (RadioButton) view.findViewById(R.id.rbAbsent);
            radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        }
    }
}
