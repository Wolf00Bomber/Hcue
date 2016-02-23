package com.appdest.hcue;

import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;

public class FeedbackConfirmationActivity extends BaseActivity{

    private LinearLayout llConfirm;
    private TextView tvDownloadFooter,tvHeading;

    @Override
    public void initializeControls() {

        llConfirm = (LinearLayout) inflater.inflate(R.layout.feedback_confirmation, null);
        llBody.addView(llConfirm,layoutParams);
        tvBack.setVisibility(View.INVISIBLE);
        tvTitle.setVisibility(View.INVISIBLE);
        tvDownloadFooter    =   (TextView)  llConfirm.findViewById(R.id.tvDownloadFooter);
        tvHeading           =   (TextView)  llConfirm.findViewById(R.id.tvHeading);
        tvDownloadFooter.setText(Html.fromHtml("Download our <font color=\"#F57103\">hCue Patient App</font> from Google play store"));
        tvDownloadFooter.setTypeface(AppConstants.WALSHEIM_LIGHT);
        tvHeading.setTypeface(AppConstants.WALSHEIM_MEDIUM);

    }

    @Override
    public void bindControls() {
        tvLogin.setEnabled(false);
    }
}
