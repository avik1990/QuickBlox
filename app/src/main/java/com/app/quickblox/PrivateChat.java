package com.app.quickblox;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.quickblox.adapter.ChatDialogAdapter;
import com.quickblox.auth.QBAuth;
import com.quickblox.auth.session.BaseService;
import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSettings;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.BaseServiceException;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.users.model.QBUser;

import java.util.ArrayList;

public class PrivateChat extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvLoadChatdialog;
    FloatingActionButton fabAddChatUser;

    Context mContext;
    String user, password;
    ChatDialogAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        mContext = this;

        user = getIntent().getExtras().getString("userId");
        password = getIntent().getExtras().getString("userPassword");

        //  initializeQuickBlox();
        initView();
        createsessionForChat();
        loadchatdialog();

    }

    private void loadchatdialog() {

        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> qbChatDialogs, Bundle bundle) {
                adapter = new ChatDialogAdapter(mContext, qbChatDialogs);
                rvLoadChatdialog.setAdapter(adapter);
            }

            @Override
            public void onError(QBResponseException e) {

            }
        });


    }

    private void createsessionForChat() {
        final ProgressDialog mDilaog = new ProgressDialog(mContext);
        mDilaog.setMessage("Please wait");
        mDilaog.show();

        final QBUser qbUser = new QBUser(user, password);
        QBAuth.createSession(qbUser).performAsync(new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                qbUser.setId(qbSession.getId());

                try {
                    qbUser.setPassword(BaseService.getBaseService().getToken());
                } catch (BaseServiceException e) {
                    e.printStackTrace();
                }

                QBChatService.getInstance().login(qbUser, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        mDilaog.dismiss();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });

            }

            @Override
            public void onError(QBResponseException e) {

            }
        });

    }

    private void initView() {
        rvLoadChatdialog = findViewById(R.id.rvLoadChatdialog);
        /*fabAddChatUser = findViewById(R.id.fabAddChatUser);
        fabAddChatUser.setOnClickListener(this);*/
        rvLoadChatdialog.setLayoutManager(new LinearLayoutManager(this));
        rvLoadChatdialog.setItemAnimator(new DefaultItemAnimator());

    }

    private void initializeQuickBlox() {
        QBSettings.getInstance().init(mContext, Constants.APPLICATIONID, Constants.AUTHORIZATIONKEY, Constants.AUTHORIZATIONSECRET);
        QBSettings.getInstance().setAccountKey(Constants.ACCOUNTKEY);
    }

    @Override
    public void onClick(View v) {
    }
}
