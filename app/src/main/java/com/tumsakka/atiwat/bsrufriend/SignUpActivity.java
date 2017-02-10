package com.tumsakka.atiwat.bsrufriend;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;

public class SignUpActivity extends AppCompatActivity {

    //Explicit
    private EditText nameEdiText, userEditText, passEditText;
    private ImageView imageView;
    private RadioGroup radioGroup;
    private Button button;
    private String nameString, userString, passString,
            pathImageString, nameImageString;
    private Uri uri;
    private boolean aBoolean = true;
    private int anInt = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Bind Widget
        bindWidget();


        //Button Controller
        buttonController();

        //Image Controller
        imageController();

        //RadioController

        radioController();
    } //Main Method

    private void radioController() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton5:
                        anInt = 0;
                        break;
                    case R.id.radioButton4:
                        anInt = 1;
                        break;
                    case R.id.radioButton3:
                        anInt = 2;
                        break;
                    case R.id.radioButton2:
                        anInt = 3;
                        break;
                    case R.id.radioButton:
                        anInt = 4;
                        break;

                }   //switch
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            aBoolean = false;
            uri = data.getData();
            //SetUP image Choose to UmageView
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //Find Path of Image Choose
            String[] srting = new String[]{MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, srting, null, null, null);

            if (cursor != null) {

                cursor.moveToFirst();
                int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                pathImageString = cursor.getString(index);
            } else {
                pathImageString = uri.getPath();
            }

            Log.d("10febV1", "pathimage ==> " + pathImageString);

        } //if
    }   //onActivityResult

    private void imageController() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "โปรดเลือกแอพดูภาพ"), 1);

            }   //onClick
        });
    }   //imageController

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
                } else if (aBoolean) {
                    //Non Choose image
                    MyAlert myAlert = new MyAlert(SignUpActivity.this);
                    myAlert.myDialog("ยังไม่เลือกรูปภาพ", "กรุณาเลือกรูปภาพ");
                } else {
                    //EveryThing OK


                    uploadValueToServer();

                }

            }   //onClick
        });

    }

    private void uploadValueToServer() {
        try {
            //upload Image To server
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy
                    .Builder()
                    .permitAll()
                    .build();
            StrictMode.setThreadPolicy(policy);
            SimpleFTP simpleFTP = new SimpleFTP();
            simpleFTP.connect("ftp.swiftcodingthai.com", 21, "bsru@swiftcodingthai.com", "Abc12345");
            simpleFTP.bin();
            simpleFTP.cwd("Image_master");
            simpleFTP.stor(new File(pathImageString));
            simpleFTP.disconnect();

            //Uploade Text
            String tag = "10febV2";
            Log.d(tag, "Name ==> " + nameString);
            Log.d(tag, "User ==> " + userString);
            Log.d(tag, "Password ==> " + passString);

            nameImageString = "http://swiftcodingthai.com/bsru/image_Miniball"+ pathImageString.substring(pathImageString.lastIndexOf("/"));
            Log.d(tag, "Image ==> " + nameImageString);
            Log.d(tag, "Avatar ==> " + anInt);

            AddValueToUser addValueToUser = new AddValueToUser(SignUpActivity.this,
                    nameString, userString, passString, nameImageString,
                    Integer.toString(anInt));
            addValueToUser.execute("http://swiftcodingthai.com/bsru/add_miniball.php");
            String s = addValueToUser.get();
            Log.d(tag, "Result ==> " + s);

            if (Boolean.parseBoolean(s)) {
                finish();
            } else {
                MyAlert myAlert = new MyAlert(SignUpActivity.this);
                myAlert.myDialog("Connot Upload", "Upload Fail");
            }

        } catch (Exception e) {
            Log.d("10febV1", "e upload ==> " + e.toString());
        }
    }   //upload

    private void bindWidget() {

        nameEdiText = (EditText) findViewById(R.id.editText5);
        userEditText = (EditText) findViewById(R.id.editText4);
        passEditText = (EditText) findViewById(R.id.editText6);
        imageView = (ImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.ragAvatar);
        button = (Button) findViewById(R.id.button3);

    }   //bindWidget
}   //Main Class
