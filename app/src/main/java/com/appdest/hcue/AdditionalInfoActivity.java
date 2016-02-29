package com.appdest.hcue;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.appdest.hcue.common.AppConstants;
import com.appdest.hcue.model.AdditionalInfoRequest;
import com.appdest.hcue.model.AdditionalInfoResponse;
import com.appdest.hcue.services.RestCallback;
import com.appdest.hcue.services.RestClient;
import com.appdest.hcue.services.RestError;

import retrofit.client.Response;

/**
 * Created by Admin on 29-02-2016.
 */
public class AdditionalInfoActivity extends BaseActivity{
    private LinearLayout llAdditional_info , llreferral_source;
    private TextView tv_referral_source , tv_select , tv_marital_status , tv_single , tv_married;
    private EditText edt_educational , edt_occupation;
    private boolean isSingleSelected = false;
    private Button btn_confirm_addition_details ;
  //  private Dialog dialog ;

    @Override
    public void initializeControls() {

        llAdditional_info = (LinearLayout) inflater.inflate(R.layout.additional_info, null);
        llBody.addView(llAdditional_info);

        llreferral_source  =  (LinearLayout) llAdditional_info.findViewById(R.id.llreferral_source);
        tv_referral_source = (TextView) llAdditional_info.findViewById(R.id.tv_referral_source);
        tv_select          = (TextView) llAdditional_info.findViewById(R.id.tv_select);
        tv_marital_status  = (TextView) llAdditional_info.findViewById(R.id.tv_marital_status);
        tv_single          = (TextView) llAdditional_info.findViewById(R.id.tv_single);
        tv_married         = (TextView) llAdditional_info.findViewById(R.id.tv_married);
        edt_educational    = (EditText) llAdditional_info.findViewById(R.id.edt_educational);
        edt_occupation    = (EditText) llAdditional_info.findViewById(R.id.edt_occupation);
        btn_confirm_addition_details    = (Button) llAdditional_info.findViewById(R.id.btn_confirm_addition_details);

        tv_single.performClick();

        tv_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSingleSelected = true;
                tv_single.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon, 0, 0, 0);
                tv_married.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon, 0, 0, 0);

            }
        });
        tv_married.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isSingleSelected = false ;
                tv_married.setCompoundDrawablesWithIntrinsicBounds(R.drawable.check_icon,0,0,0);
                tv_single.setCompoundDrawablesWithIntrinsicBounds(R.drawable.un_check_icon,0,0,0);

            }
        });

        llreferral_source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                showSelectSourcePopup();
            }
        });


        btn_confirm_addition_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(tv_select.getTag() == null)
                {
                    Toast.makeText(AdditionalInfoActivity.this,"Please select referral source.", Toast.LENGTH_LONG).show();
                }else
                {
                    callWebService();
                }
            }
        });
    }

    private void callWebService() {

        final AdditionalInfoRequest additionalInfoRequest = new AdditionalInfoRequest();
        String url = "http://d1lmwj8jm5d3bc.cloudfront.net";
        RestClient.getAPI(url).additionalInfo(additionalInfoRequest, new RestCallback<AdditionalInfoResponse>() {
            @Override
            public void success(AdditionalInfoResponse additionalInfoResponse, Response response) {

            }

            @Override
            public void failure(RestError restError) {

            }
        });
    }

    private void showSelectSourcePopup() {

        /*if(dialog == null) {
            dialog = new Dialog(this);*/
            View view = LayoutInflater.from(getBaseContext()).inflate(R.layout.custom_dialog,null);
            final PopupWindow popupWindow = new PopupWindow(getBaseContext());
            popupWindow.setContentView(view);

           // dialog.setContentView(R.layout.custom_dialog);
            ListView lv_source_names = (ListView) view.findViewById(R.id.lv_source_names);
            //lv_source_names.setAdapter(new MyCustomAdapter());
            ArrayAdapter<CharSequence> referral_names_adapter = ArrayAdapter
                    .createFromResource(this, R.array.referral_source_names,
                            android.R.layout.simple_spinner_item);
            lv_source_names.setAdapter(referral_names_adapter);
            popupWindow.showAsDropDown(llreferral_source);
            lv_source_names.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.v("item", (String) parent.getItemAtPosition(position));
                    tv_select.setTag((String) parent.getItemAtPosition(position));
                    tv_select.setText((String) parent.getItemAtPosition(position));
                    popupWindow.dismiss();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


    }

    @Override
    public void bindControls() {

        tv_referral_source.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        tv_select.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        tv_marital_status.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        tv_single.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        tv_married.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        edt_educational.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        edt_occupation.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);
        btn_confirm_addition_details.setTypeface(AppConstants.WALSHEIM_BOLD_OBLIQUE);

    }

    public class MyCustomAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
