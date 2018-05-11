package com.pdtrong.final_android.Login;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pdtrong.final_android.ChuyenTabLogin_TabSignUp.ChuyenTabLogin_SignUp;
import com.pdtrong.final_android.PassActivitytoFragment.BusStation;
import com.pdtrong.final_android.R;
import com.pdtrong.final_android.TruyenDuLieuDangNhap.TransactionLogin;
import com.squareup.otto.Subscribe;

import java.io.EOFException;

public class Login_fm extends Fragment implements View.OnClickListener {
    private Button btnSignup, btnSignin,btnExit;

    private EditText etUsername,etPassword;
    private TransactionLogin transactionLogin;
    private ChuyenTabLogin_SignUp chuyenTabLogin_signUp;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login_fm, container, false);

        transactionLogin = (TransactionLogin) getActivity();
        chuyenTabLogin_signUp = (ChuyenTabLogin_SignUp) getActivity();

        btnSignin = (Button) view.findViewById(R.id.btn_signin);
        btnSignup = (Button) view.findViewById(R.id.btn_signup);
        btnExit = (Button) view.findViewById(R.id.btn_exit);

        etUsername = (EditText) view.findViewById(R.id.et_username);
        etPassword = (EditText) view.findViewById(R.id.et_password);

        btnExit.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
        btnSignin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()){
            case R.id.btn_exit:
                break;
            case R.id.btn_signin:
                if(etUsername.getText().toString().isEmpty() ||etPassword.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(),"vui lòng điền đủ username và password",Toast.LENGTH_SHORT).show();
                }else{
                    transactionLogin.duLieuDangNhap(""+etUsername.getText().toString(),""+etPassword.getText().toString());
                }
                break;
            case R.id.btn_signup:
                chuyenTabLogin_signUp.chuyebTabLogin_SignUP(Login_fm.this);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        BusStation.getBus().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusStation.getBus().unregister(this);
    }

    @Subscribe
    public void recievedMessage(String message){
        if(message.equals("login_thatbai")){
            dangKiThatBai();
        }
    }

    public void dangKiThatBai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Đăng nhập không thành công \nTài khoản hoặc mật khẩu sai ♥ !!!");
        builder.setCancelable(false);
        builder.setPositiveButton("Đăng nhập lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Đăng kí ngay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chuyenTabLogin_signUp.chuyebTabLogin_SignUP(Login_fm.this);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
