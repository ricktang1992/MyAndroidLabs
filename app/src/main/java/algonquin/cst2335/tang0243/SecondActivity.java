package algonquin.cst2335.tang0243;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

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

                            profileImage.setImageBitmap(thumbnail);

                        }

                    }

                } );

        Button button2=findViewById(R.id.button2);;

        button2.setOnClickListener(clk->
        {
            cameraResult.launch(cameraIntent);
        } );



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
}