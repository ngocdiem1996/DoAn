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
import com.pdtrong.final_android.TruyenDuLieuDangKi.TransactionSignUp;
import com.pdtrong.final_android.UserDatabase.UserInfo;
import com.squareup.otto.Subscribe;

public class SignUp_fm extends Fragment implements View.OnClickListener {

    private Button btnSignUp1,btnCancel;
    private EditText etFullname,etBirthday,etPhone,etUsername1,etPassword1;
    private TransactionSignUp transactionSignUp;
    private ChuyenTabLogin_SignUp chuyenTabLogin_signUp;
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up_fm, container, false);

        transactionSignUp = (TransactionSignUp) getActivity();
        chuyenTabLogin_signUp = (ChuyenTabLogin_SignUp) getActivity();

        btnSignUp1 = (Button) view.findViewById(R.id.btn_signup1);
        btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        etFullname=(EditText) view.findViewById(R.id.et_fullname);
        etBirthday=(EditText) view.findViewById(R.id.et_birthday);
        etPhone=(EditText) view.findViewById(R.id.et_phone);
        etUsername1=(EditText) view.findViewById(R.id.et_username1);
        etPassword1=(EditText) view.findViewById(R.id.et_password1);

        btnCancel.setOnClickListener(this);
        btnSignUp1.setOnClickListener(this);

        return view;
    }

    private UserInfo getUserInfo(){
        UserInfo userInfo = new UserInfo(etUsername1.getText().toString(),etPassword1.getText().toString(),etFullname.getText().toString(),etBirthday.getText().toString(),
                etPhone.getText().toString());
        return userInfo;
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
        if(message.equals("signup_thanhcong")){
            dangKiThanhCong();
        }
        if(message.equals("signup_thatbai")){
            dangKiThatBai();
        }
    }

    public void dangKiThanhCong(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn đã đăng kí thành công ♥ !!!");
        builder.setCancelable(false);
        builder.setPositiveButton("Trở về đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                chuyenTabLogin_signUp.chuyebTabLogin_SignUP(SignUp_fm.this);
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void dangKiThatBai(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thông báo");
        builder.setMessage("Đăng kí không thành công \nTài khoản đã được đăng kí ♥ !!!");
        builder.setCancelable(false);
        builder.setPositiveButton("Đăng kí lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                etUsername1.setText("");
                etPassword1.setText("");
            }
        });
        builder.setNegativeButton("Trở về đăng nhập", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onClick( View v ) {
        switch (v.getId()){
            case R.id.btn_cancel:
                chuyenTabLogin_signUp.chuyebTabLogin_SignUP(SignUp_fm.this);
                break;
            case R.id.btn_signup1:
                if(getUserInfo().getUsername().isEmpty()|| getUserInfo().getPassword().isEmpty()){
                    Toast.makeText(getActivity(),"Vui lòng điền đủ username và password",Toast.LENGTH_SHORT).show();
                }else{
                    transactionSignUp.duLieuDangKi(getUserInfo());
                    break;
                }
        }
    }
}
