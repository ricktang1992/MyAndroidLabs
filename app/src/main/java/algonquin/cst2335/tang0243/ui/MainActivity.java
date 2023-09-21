package algonquin.cst2335.tang0243.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.tang0243.data.MainViewModel;
import algonquin.cst2335.tang0243.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private MainViewModel model;
    private ActivityMainBinding variableBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No ids loaded yet.

        model = new ViewModelProvider(this).get(MainViewModel.class);

        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());


        TextView tv=variableBinding.myTextView;//same as getElementById() from JS
        Button b=variableBinding.myButton;//same as getElementById() from JS
        EditText ev=variableBinding.myEditText;//same as getElementById() from JS
        CheckBox cb=variableBinding.myCheckBox;
        Switch sb=variableBinding.mySwitch;
        RadioButton rb=variableBinding.myRadioButton;
        ImageView iv=variableBinding.myImageView;
        ImageButton ib=variableBinding.myImageButton;

        model.editString.observe(this, s -> {
            tv.setText("Your edit text has " + s);
        });
        b.setOnClickListener(
                ( v ) -> {

                   model.editString.postValue(ev.getText().toString());
                   model.editString.observe(this, s -> {
                       tv.setText("Your edit text has " + s);
                   });
        });


        model.isSelected.observe(this, selected -> {
            CharSequence text = "The value is now: ";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this /* MyActivity */, text+selected.toString(), duration);
            toast.show();
            cb.setChecked(selected);
            sb.setChecked(selected);
            rb.setChecked(selected);
        });

        cb.setOnCheckedChangeListener( (btn, isChecked) -> {
            if(isChecked){
                model.isSelected.postValue(Boolean.TRUE);
            } else {
                model.isSelected.postValue(Boolean.FALSE);
            }
        } );
        rb.setOnCheckedChangeListener( (btn, isChecked) -> {
            if(isChecked){
                model.isSelected.postValue(Boolean.TRUE);
            } else {
                model.isSelected.postValue(Boolean.FALSE);
            }
        } );
        sb.setOnCheckedChangeListener( (btn, isChecked) -> {
            if(isChecked){
                model.isSelected.postValue(Boolean.TRUE);
            } else {
                model.isSelected.postValue(Boolean.FALSE);
            }
        } );

        iv.setOnClickListener(
                ( v ) -> {

                    CharSequence text = "You Clicked The Image";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(this /* MyActivity */, text, duration);
                    toast.show();

                });
        ib.setOnClickListener(
                ( v ) -> {
                    CharSequence text = "You Clicked The Image";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(this /* MyActivity */, "The width = " + ib.getWidth() + " and height = " + ib.getHeight(), duration);
                    toast.show();

                });
        //OnClickListener       //anonymous class
        /*b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText(R.string.app_name);
                ev.setText("You clicked the button");
                b.setText("You clicked the button");


            }
        });*/
    }

}