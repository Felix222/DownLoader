package com.felixpc.down.downloader.View.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.MaterialEditText;
import android.widget.TextView;

import com.felixpc.down.downloader.R;

/**
 * Created by Felix on 2016/4/13.
 */
public class InputDialog extends Dialog {
    private String TAG = InputDialog.class.getName() + " ";
    private MaterialEditText urleditText;
    private Button okButton, cancleButton;
    private LinearLayout input_parent_lay;
    private WindowManager windowManager;

    public InputDialog(Context context) {
        super(context, R.style.MyDialogStyle);

        initDialog();
    }


    private void initDialog() {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.input_file_url, null);
        urleditText = (MaterialEditText) dialogView.findViewById(R.id.path_lay);

        okButton = (Button) dialogView.findViewById(R.id.inputPathLay_ok);
        cancleButton = (Button) dialogView.findViewById(R.id.inputPathLay_cancle);

        input_parent_lay = (LinearLayout) dialogView.findViewById(R.id.input_parent_lay);

        urleditText = (MaterialEditText) dialogView.findViewById(R.id.path_lay);
        urleditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    Log.i(TAG, " 键盘回车事件");
                } else if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Log.i(TAG, " 键盘关闭事件");
                    try {
                        input_parent_lay.requestFocus();
                        urleditText.clearFocus();
                    } catch (Exception e) {
                    }
                }
                return false;
            }
        });
        super.setContentView(dialogView);

    }


    public InputDialog addOKButton(View.OnClickListener listener) {
        if (this.okButton != null) {
            this.okButton.setOnClickListener(listener);
        }
        return this;
    }

    public String getInputUrl() {
        return urleditText.getText().toString().trim();
    }

    public InputDialog addCancleButton(View.OnClickListener listener) {
        if (this.cancleButton != null) {
            this.cancleButton.setOnClickListener(listener);
        }
        return this;
    }

    @Override
    public void dismiss() {
        input_parent_lay.requestFocus();
        super.dismiss();
    }

    @Override
    public void show() {
        super.show();
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.width = (int) (display.getWidth() * 0.75); //设置宽度
        this.getWindow().setAttributes(lp);
    }
}
