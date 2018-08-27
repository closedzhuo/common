package com.close.common.library.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.close.common.library.R;

public class CustomDialog extends Dialog {

    private Context context;
    private View view;
    private int animationResId;
    private int gravity;
    private int height = -1;
    private int width = -1;
    private boolean cancelable = true;
    private boolean canceledOnTouchOutside = true;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(Builder builder) {
        super(builder.context, R.style.bottomDilaog);
        context = builder.context;
        view = builder.view;
        animationResId = builder.animationresId;
        gravity = builder.gravity;
        height = builder.height;
        width = builder.width;
        cancelable = builder.cancelable;
        canceledOnTouchOutside = builder.canceledOnTouchOutside;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
        Window window = getWindow();
        if (animationResId != 0)
            window.setWindowAnimations(animationResId);
        setCancelable(cancelable);
        setCanceledOnTouchOutside(canceledOnTouchOutside);
        WindowManager.LayoutParams params = window.getAttributes();
        params.height = height == -1 ? WindowManager.LayoutParams.WRAP_CONTENT : height;
        params.width = width == -1 ? WindowManager.LayoutParams.MATCH_PARENT : width;
        params.gravity = gravity;
        window.setAttributes(params);
    }

    public View getView() {
        return view;
    }

    public static class Builder {

        private Context context;
        private int height = -1;
        private View view;
        private int animationresId = 0;
        private int gravity = Gravity.CENTER;
        private int width = -1;
        private boolean cancelable = true;
        private boolean canceledOnTouchOutside = true;

        public Builder(Context context) {
            this.context = context;
        }

        public CustomDialog.Builder setView(int resView) {
            view = LayoutInflater.from(context).inflate(resView, null);
            return this;
        }

        public CustomDialog.Builder setViewOnClick(int viewId, View.OnClickListener listener) {
            view.findViewById(viewId).setOnClickListener(listener);
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public CustomDialog.Builder setGravity(int gravity) {
            this.gravity = gravity;
            return this;
        }

        public View getView() {
            return view;
        }

        public Builder setCancelable(boolean flag) {
            this.cancelable = flag;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean flag) {
            this.canceledOnTouchOutside = flag;
            return this;
        }

        public CustomDialog.Builder setAnimations(int resId) {
            this.animationresId = resId;
            return this;
        }

        public CustomDialog build() {
            return new CustomDialog(this);

        }
    }
}
