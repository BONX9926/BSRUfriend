package com.tumsakka.atiwat.bsrufriend;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEdiText, userEditText, passEditText;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private Button button;
    private String nameString, userString, passString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();


        //Button Controller
        buttonController();
    }

    private void buttonController() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Get Value From Edit Text
                nameString = nameEdiText.getText().toString().trim();
                userString = userEditText.getText().toString().trim();
                passString = passEditText.getText().toString().trim();

                //Check Space
                if (nameString.equals("") || userString.equals("") || passString.equals("")) {
                    //True ==> Have Space
                    MyAlert myAlert = new MyAlert(SignUpActivity.this);
                    myAlert.myDialog("มีช่องว่าง", "กรุรากรอกให้ครบทุกช่องคะ ");
                }

            }   //onClick
        });

    }

    private void bindWidget() {

        nameEdiText = (EditText) findViewById(R.id.editText5);
        userEditText = (EditText) findViewById(R.id.editText4);
        passEditText = (EditText) findViewById(R.id.editText6);
        imageView = (ImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvatar);
        button = (Button) findViewById(R.id.button3);

    }   //bindWidget
}   //Main Class
