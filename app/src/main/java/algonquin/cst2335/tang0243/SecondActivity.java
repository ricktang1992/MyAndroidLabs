package algonquin.cst2335.tang0243;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        TextView mytext=findViewById(R.id.textView);
        mytext.setText( "Welcome back " + emailAddress);
        ImageView profileImage = findViewById(R.id.imageView);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);


        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult() ,
                new ActivityResultCallback<ActivityResult>() {

                    @Override

                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");

                            //profileImage.setImageBitmap(thumbnail);
                            FileOutputStream fOut = null;

                            try { fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);

                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);

                                fOut.flush();

                                fOut.close();

                            }

                            catch (IOException e)

                            { e.printStackTrace();

                            }
                        }

                    }

                } );

        Button button2=findViewById(R.id.button2);;

        button2.setOnClickListener(clk->
        {
            cameraResult.launch(cameraIntent);
        } );


        File file = new File( getFilesDir(), "Picture.png");

        if(file.exists())

        {
            Bitmap theImage = BitmapFactory.decodeFile(file.getAbsolutePath());
            profileImage.setImageBitmap( theImage );
        }
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAdd = prefs.getString("LoginName    ", "");
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("LoginName", emailAddress );
        editor.putFloat("Hi",4.5f);
        editor.putInt("Age",35);
        editor.apply();
        prefs.getInt("PhoneNumber", 0);


        EditText phoneNumber=findViewById(R.id.editTextPhone);

        Button callButton=findViewById(R.id.callButton);
        callButton.setOnClickListener(clk->
        {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String phoneNum = phoneNumber.getText().toString();
            call.setData(Uri.parse("tel:" + phoneNum));
            startActivity(call);
        } );
    }
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        EditText phoneNumber=findViewById(R.id.editTextPhone);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber",phoneNumber.getText().toString());
        editor.apply();
    }
}