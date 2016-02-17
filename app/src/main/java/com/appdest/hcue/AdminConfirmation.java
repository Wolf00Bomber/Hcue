package com.appdest.hcue;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.utils.Preference;

/**
 * Created by Vinsan on 13/02/16.
 */
public class AdminConfirmation extends BaseActivity implements View.OnClickListener
{
    private LinearLayout layout;
    private Button btnGoBack, btnConfirm;
    private int HospitalID;

    @Override
    public void initializeControls()
    {
        layout = (LinearLayout) inflater.inflate(R.layout.admin_confirmation, null);
        llBody.addView(layout,layoutParams);

        btnGoBack           = (Button) layout.findViewById(R.id.btnGoBack);
        btnConfirm           = (Button) layout.findViewById(R.id.btnConfirm);

        btnGoBack.setOnClickListener(this);
        btnConfirm.setOnClickListener(this);
        tvBack.setOnClickListener(this);

        setSpecificTypeFace(layout, AppConstants.WALSHEIM_LIGHT);
    }

    @Override
    public void bindControls()
    {
        HospitalID = getIntent().getIntExtra("HospitalID", 19);
        tvLogin.setEnabled(false);
        tvBack.setText("Previous Page");
        tvTitle.setText("Configuration confirmation");
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tvBack:
            case R.id.btnGoBack:
                finish();
                break;
            case R.id.btnConfirm:
                // Saving the Hospital ID of the Login.
                Preference preference = new Preference(AdminConfirmation.this);
                preference.saveIntInPreference("HospitalID", HospitalID);
                preference.commitPreference();

                showToast("Configuration is successfully updated.");
                Intent intent = new Intent(AdminConfirmation.this, SelectDoctorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;
        }
    }

}
