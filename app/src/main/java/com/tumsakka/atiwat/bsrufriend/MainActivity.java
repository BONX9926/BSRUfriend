package com.tumsakka.atiwat.bsrufriend;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // Explicit การประกาศตัวแปร
    private Button sigInButton, signUpButton;
    private EditText userEditText, passEditText;
    private String userString, passString;
    private String[] loginStrings = new String[8];
    // static final คือค่าตัวแปรที่ไม่สามารถเปลี่ยนแปลงได้
    private static final String urlPHP = "http://swiftcodingthai.com/bsru/get_user_miniball.php";
    private boolean aBoolean = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind Widget คือ การ Initial Var กับ view บน Xml
        sigInButton = (Button) findViewById(R.id.button2);
        signUpButton = (Button) findViewById(R.id.button);
        userEditText = (EditText) findViewById(R.id.editText);
        passEditText = (EditText) findViewById(R.id.editText2);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        sigInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //check Apace and get Value from Edit Text
                userString = userEditText.getText().toString().trim();
                passString = passEditText.getText().toString().trim();
                if (userString.equals("") || passString.equals("")) {
                    //Have Space
                    MyAlert myAlert = new MyAlert(MainActivity.this);
                    myAlert.myDialog("มีช่องว่าง","กรุณากรอกให้ครบทุกช่อง ค่ะ");

                } else {
                    //No Space
                    checkUserPass();

                }

            }   //onClick
        });

    }   // Main Method

    private void checkUserPass() {
        try {

            GetUser getUser = new GetUser(MainActivity.this);
            getUser.execute(urlPHP);
            String strJSON = getUser.get();
            Log.d("16febV1", "strJSON ==> " + strJSON);

            JSONArray jsonArray = new JSONArray(strJSON);
            for (int i=0;i<jsonArray.length();i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (userString.equals(jsonObject.get("User"))) {

                    loginStrings[0] = jsonObject.getString("id");
                    loginStrings[1] = jsonObject.getString("Name");
                    loginStrings[2] = jsonObject.getString("User");
                    loginStrings[3] = jsonObject.getString("Password");
                    loginStrings[4] = jsonObject.getString("Image");
                    loginStrings[5] = jsonObject.getString("Avata");
                    loginStrings[6] = jsonObject.getString("Lat");
                    loginStrings[7] = jsonObject.getString("Lng");

                    aBoolean = false;

                }
            }   // Loop for

            if (aBoolean) {
                //User False
                MyAlert myAlert = new MyAlert(MainActivity.this);
                myAlert.myDialog("หา User นี่ไม่เจอ ?", "ไม่มี "+userString+" ในฐานข้อมูลของเรา");
            } else if (!passString.equals(loginStrings[3])) {
                //password false
                MyAlert myAlert = new MyAlert(MainActivity.this);
                myAlert.myDialog("Password False", "Please Try Again Password False");

            } else {
                //password True
                Toast.makeText(MainActivity.this, "Welcome " + loginStrings[1],
                        Toast.LENGTH_SHORT).show();
            }


        } catch (Exception e) {
            Log.d("16febV1", "e checkUserPass ==>" + e.toString());
        }
    }   //checkUserPass
}   // Main Class
