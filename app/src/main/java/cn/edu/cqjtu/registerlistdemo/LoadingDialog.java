package cn.edu.cqjtu.registerlistdemo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by JohnNiang on 2016/11/2.
 */

public class LoadingDialog {

    /**
     * 得到自定义的progressDialog
     * @param context
     * @param msg
     * @return
     */
    public static Dialog createLoadingDialog(Context context, String msg) {
        // 得到整个View
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        // 获得整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_layout);
        ImageView imgLoading = (ImageView) view.findViewById(R.id.imgLoading);
        TextView tvTip = (TextView) view.findViewById(R.id.tvTip);
        // 加载动画
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 显示动画
        imgLoading.startAnimation(animation);
        tvTip.setText(msg);

        // 创建自定义样式的Dialog
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setCancelable(false);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return loadingDialog;
    }

    public static Dialog createSuccessDialog(Context context,String msg){
        // 得到整个View
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        // 获得整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_layout);
        ImageView imgLoading = (ImageView) view.findViewById(R.id.imgLoading);
        TextView tvTip = (TextView) view.findViewById(R.id.tvTip);
        tvTip.setText(msg);

        // 创建自定义样式的Dialog
        Dialog successDialog = new Dialog(context, R.style.loading_dialog);
        successDialog.setCancelable(false);
        successDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return successDialog;
    }

    public static Dialog createFialedDialog(Context context,String msg){
        // 得到整个View
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        // 获得整个布局
        LinearLayout layout = (LinearLayout) view.findViewById(R.id.dialog_layout);
        ImageView imgLoading = (ImageView) view.findViewById(R.id.imgLoading);
        TextView tvTip = (TextView) view.findViewById(R.id.tvTip);
        tvTip.setText(msg);

        // 创建自定义样式的Dialog
        Dialog failedDialog = new Dialog(context, R.style.loading_dialog);
        failedDialog.setCancelable(false);
        failedDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        return failedDialog;
    }
}
