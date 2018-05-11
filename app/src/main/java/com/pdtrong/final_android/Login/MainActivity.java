package com.pdtrong.final_android.Login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.pdtrong.final_android.ChuyenTabLogin_TabSignUp.ChuyenTabLogin_SignUp;
import com.pdtrong.final_android.PassActivitytoFragment.BusStation;
import com.pdtrong.final_android.R;
import com.pdtrong.final_android.Search.ListSearch;
import com.pdtrong.final_android.ThongBaoThongTinDangNhapCu.UserLoginBefore;
import com.pdtrong.final_android.TruyenDuLieuDangKi.TransactionSignUp;
import com.pdtrong.final_android.TruyenDuLieuDangNhap.TransactionLogin;
import com.pdtrong.final_android.UserDatabase.DatabaseHandler;
import com.pdtrong.final_android.UserDatabase.UserInfo;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends FragmentActivity implements TransactionLogin, TransactionSignUp,ChuyenTabLogin_SignUp{
    private android.support.v4.app.FragmentManager fm;
    private Login_fm login_fm;
    private SignUp_fm signup_fm;
    private DatabaseHandler databaseHandler;
    private Realm realm;
    private int flag = 0;

    //0 la lan dau tien
    //1 la lan dang xuat va xoa du lieu cu, vao luu lai luc dang nhap

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHandler = new DatabaseHandler(this);

        Realm.init(this);
        realm=Realm.getDefaultInstance();
        realm.beginTransaction();
//        realm.deleteAll(); //xoa toan bo data trong realm database
        realm.commitTransaction();

        if(databaseHandler.getNumUser()!=0){
                RealmResults<UserLoginBefore> userLoginBefore = realm.where(UserLoginBefore.class).findAll();
                if(userLoginBefore.size()!=0){
                    UserInfo user = databaseHandler.getUser(userLoginBefore.get(userLoginBefore.size()-1).getNameUser());
                    Intent myListSearch = new Intent(MainActivity.this, ListSearch.class);
                    myListSearch.putExtra("userinfo",user);
                    startActivity(myListSearch);
                }
        }

        fm = getSupportFragmentManager();
        login_fm = new Login_fm();
        signup_fm = new SignUp_fm();

        //chay fragment 1
        FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.add(R.id.framelayout_main,login_fm);
        ft_add.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //chay fragment 1
        FragmentTransaction ft_add = fm.beginTransaction();
        ft_add.replace(R.id.framelayout_main,login_fm);
        ft_add.commit();

    }

    @Override
    public void duLieuDangKi( UserInfo userInfoSignUp ) {
        if(!databaseHandler.checkUserExisted(userInfoSignUp)){
            databaseHandler.addUser(userInfoSignUp);
            BusStation.getBus().post("signup_thanhcong");
        }else{
            BusStation.getBus().post("signup_thatbai");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    @Override
    public void duLieuDangNhap( final String usernameLogin, String passwordLogin ) {
            if(databaseHandler.checkUserLogin(usernameLogin,passwordLogin)){
                Toast.makeText(getBaseContext(),"dang nhap thanh cong",Toast.LENGTH_SHORT).show();
                UserInfo user = databaseHandler.getUser(usernameLogin);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        UserLoginBefore userBefore = realm.createObject(UserLoginBefore.class);
                        userBefore.setNameUser(usernameLogin);
                    }
                });

                Intent myListSearch = new Intent(MainActivity.this, ListSearch.class);
                myListSearch.putExtra("userinfo",user);
                startActivity(myListSearch);

            }else{
                BusStation.getBus().post("login_thatbai");
            }

    }

    @Override
    public void chuyebTabLogin_SignUP( Fragment fragment ) {
        if(fragment == login_fm){
            FragmentTransaction ft_add = fm.beginTransaction();
            ft_add.replace(R.id.framelayout_main,signup_fm);
            ft_add.commit();
        }
        if(fragment == signup_fm){
            FragmentTransaction ft_add = fm.beginTransaction();
            ft_add.replace(R.id.framelayout_main,login_fm);
            ft_add.commit();
        }
    }
}
