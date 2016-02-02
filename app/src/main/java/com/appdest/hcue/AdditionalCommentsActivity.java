package com.appdest.hcue;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by shyamprasadg on 02/02/16.
 */
public class AdditionalCommentsActivity extends BaseActivity implements View.OnClickListener
{
    private LinearLayout llComments;
    private ImageView ivImage;
    private TextView tvName,tvSpecality;
    private EditText edtEnterComments;
    private Button btnSubmit;
    @Override
    public void initializeControls()
    {
        llComments = (LinearLayout) inflater.inflate(R.layout.additional_comments, null);

        llBody.addView(llComments);

        edtEnterComments    =   (EditText)  llComments.findViewById(R.id.edtEnterComments);

        tvName              =   (TextView)  llComments.findViewById(R.id.tvName);
        tvSpecality         =   (TextView)  llComments.findViewById(R.id.tvSpecality);

        ivImage             =   (ImageView) llComments.findViewById(R.id.ivImage);

        btnSubmit           =   (Button)    llComments.findViewById(R.id.btnSubmit);

    }

    @Override
    public void bindControls() {

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSubmit:
                break;
        }

    }
}
