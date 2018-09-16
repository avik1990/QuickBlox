package com.app.quickblox;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.session.QBSettings;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    EditText et_email, et_password;
    Button btn_submit;

    String st_email, st_password;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        initializeQuickBlox();
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);

        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(this);

    }

    private void initializeQuickBlox() {
        QBSettings.getInstance().init(mContext, Constants.APPLICATIONID, Constants.AUTHORIZATIONKEY, Constants.AUTHORIZATIONSECRET);
        QBSettings.getInstance().setAccountKey(Constants.ACCOUNTKEY);
    }

    @Override
    public void onClick(View v) {
        if (v == btn_submit) {
            st_email = et_email.getText().toString().trim();
            st_password = et_password.getText().toString().trim();

            QBUser qbUser = new QBUser(st_email, st_password);
            QBUsers.signIn(qbUser).performAsync(new QBEntityCallback<QBUser>() {

                @Override
                public void onSuccess(QBUser qbUser, Bundle bundle) {
                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(mContext,Dashboard.class);
                    i.putExtra("userId",st_email);
                    i.putExtra("userPassword",st_password);
                    startActivity(i);
                }

                @Override
                public void onError(QBResponseException e) {
                    Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
