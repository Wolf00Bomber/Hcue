package com.appdest.hcue;

import android.text.Html;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedbackConfirmationActivity extends BaseActivity{

    private LinearLayout llConfirm;
    private TextView tvDownloadFooter;

    @Override
    public void initializeControls() {

        llConfirm = (LinearLayout) inflater.inflate(R.layout.feedback_confirmation, null);
        llBody.addView(llConfirm,layoutParams);
        tvDownloadFooter    =   (TextView)  llConfirm.findViewById(R.id.tvDownloadFooter);
        tvDownloadFooter.setText(Html.fromHtml("Download our <font color=\"#F57103\">hCue Patient App</font> from Google play store"));
    }

    @Override
    public void bindControls() {
        tvLogin.setEnabled(false);
    }
}
