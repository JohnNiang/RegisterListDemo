package cn.edu.cqjtu.registerlistdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.edu.cqjtu.registerlistdemo.R;
import cn.edu.cqjtu.registerlistdemo.StudentFragment.OnListFragmentInteractionListener;
import cn.edu.cqjtu.registerlistdemo.bean.Student;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Student} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyStudentRecyclerViewAdapter extends RecyclerView.Adapter<MyStudentRecyclerViewAdapter.ViewHolder> {

    private List<Student> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyStudentRecyclerViewAdapter(List<Student> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void refresh(List<Student> items){
        this.mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Student item = mValues.get(position);

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

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    v.startAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.alpha_animation));
                    mListener.onListFragmentInteraction(holder.mItem);
                }
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

        public Student mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mImageViewHeader = (ImageView) view.findViewById(R.id.imgHeader);
            mTextViewSname = (TextView) view.findViewById(R.id.tvSname);
            mTextViewSno = (TextView) view.findViewById(R.id.tvSno);
        }
    }


}
