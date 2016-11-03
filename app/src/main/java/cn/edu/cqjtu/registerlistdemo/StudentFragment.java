package cn.edu.cqjtu.registerlistdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

import cn.edu.cqjtu.registerlistdemo.adapter.MyStudentRecyclerViewAdapter;
import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.content.StudentContent;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class StudentFragment extends Fragment {

    private static final String ARG_TYEP = "class";

    public static final int TYPE_CLASS_ALL = 0;
    public static final int TYPE_CLASS_ONE = 1;
    public static final int TYPE_CLASS_TWO = 2;
    public static final int TYPE_CLASS_THREE = 3;
    public static final int TYPE_CLASS_FOUR = 4;

    private MyStudentRecyclerViewAdapter mAdapter;

    private Activity mParentAty;

    private List<Student> list = new ArrayList<>();

    /**
     * 班级类型
     * 0:所有班级
     * 1:一班
     * 2:二班
     * 3:三班
     * 4:四班
     */
    private int type = -1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static StudentFragment newInstance(int columnCount) {
        StudentFragment fragment = new StudentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYEP, columnCount);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type = getArguments().getInt(ARG_TYEP);
            setParentTitle();
        }
        //        if (mParentAty != null) {
        //            String title = "Error Title";
        //            switch (type) {
        //                case TYPE_CLASS_ALL:
        //                    list = StudentContent.ITEMS;
        //                    title = "所有班级: " + list.size() + "人";
        //                    break;
        //                case TYPE_CLASS_ONE:
        //                    list = StudentContent.ITEMS_CLASS_ONE;
        //                    title = "计科一班: " + list.size() + "人";
        //                    break;
        //                case TYPE_CLASS_TWO:
        //                    list = StudentContent.ITEMS_CLASS_TWO;
        //                    title = "计科二班: " + list.size() + "人";
        //                    break;
        //                case TYPE_CLASS_THREE:
        //                    list = StudentContent.ITEMS_CLASS_THREE;
        //                    title = "计科三班: " + list.size() + "人";
        //                    break;
        //                case TYPE_CLASS_FOUR:
        //                    list = StudentContent.ITEMS_CLASS_FOUR;
        //                    title = "计科四班: " + list.size() + "人";
        //                    break;
        //            }
        //            mParentAty.setTitle(title);
        //        }
    }

    private void setParentTitle() {
        if (mParentAty != null) {
            String title = "Error Title";
            switch (type) {
                case TYPE_CLASS_ALL:
                    list = StudentContent.ITEMS;
                    title = "所有班级: " + list.size() + "人";
                    break;
                case TYPE_CLASS_ONE:
                    list = StudentContent.ITEMS_CLASS_ONE;
                    title = "计科一班: " + list.size() + "人";
                    break;
                case TYPE_CLASS_TWO:
                    list = StudentContent.ITEMS_CLASS_TWO;
                    title = "计科二班: " + list.size() + "人";
                    break;
                case TYPE_CLASS_THREE:
                    list = StudentContent.ITEMS_CLASS_THREE;
                    title = "计科三班: " + list.size() + "人";
                    break;
                case TYPE_CLASS_FOUR:
                    list = StudentContent.ITEMS_CLASS_FOUR;
                    title = "计科四班: " + list.size() + "人";
                    break;
            }
            mParentAty.setTitle(title);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            // 设置动画
            recyclerView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.trans_animation));
            recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            mAdapter = new MyStudentRecyclerViewAdapter(list, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
        if (context instanceof Activity) {
            mParentAty = (Activity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Student item);
    }

    @Override
    public void onResume() {
        super.onResume();
        StudentContent.refreshData();
        if (mAdapter != null) {
            mAdapter.refresh(list);
            setParentTitle();
        }
    }


}

