package sg.edu.rp.c346.id18015938.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText weight;
    EditText height;
    Button calculate;
    Button reset;
    TextView last_date;
    TextView last_bmi;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weight = findViewById(R.id.editTextWeight);
        height = findViewById(R.id.editTextHeight);
        calculate = findViewById(R.id.buttonCalculate);
        reset = findViewById(R.id.buttonReset);
        last_date = findViewById(R.id.textViewLastDate);
        last_bmi = findViewById(R.id.textViewLastBMI);
        status = findViewById(R.id.textViewLastStatus);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
                String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                        (now.get(Calendar.MONTH)+1) + "/" +
                        now.get(Calendar.YEAR) + " " +
                        now.get(Calendar.HOUR_OF_DAY) + ":" +
                        now.get(Calendar.MINUTE);


                String Last_Date = "Last Calculated Date: " + datetime;

                String Status = status.getText().toString();

                float Weight = Float.parseFloat(weight.getText().toString());
                float Height = Float.parseFloat(height.getText().toString());

                float BMI = Weight / (Height * Height);

                if (BMI < 18.5) {
                     Status = "You are underweight";
                }else if (BMI >= 18.5 && BMI <= 24.9){
                     Status = "Your BMI is normal";
                }else if (BMI >= 25 && BMI <= 29.9){
                     Status = "You are overweight";
                }else{
                     Status = "You are obese";
                }

                String Last_BMI = "Last Calculated BMI: " + String.valueOf(BMI);


                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("Last_Date", Last_Date);
                prefEdit.putString("Last_BMI", Last_BMI);
                prefEdit.putString("Status", Status);
                prefEdit.commit();

                last_date.setText(Last_Date);
                last_bmi.setText(Last_BMI);
                status.setText(Status);

            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                weight.setText("");
                height.setText("");

                String Last_Date = "Last Calculated Date: ";
                String Last_BMI = "Last Calculated BMI:0.0 ";


                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor prefEdit = prefs.edit();
                prefEdit.putString("Last_Date", Last_Date);
                prefEdit.putString("Last_BMI", Last_BMI);
                prefEdit.putString("Status", "");
                prefEdit.commit();

                last_date.setText(Last_Date);
                last_bmi.setText(Last_BMI);
                status.setText("");


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        //Step 2a: Obtain an instance of the SharedPreferences
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //Step 2b: Retrieve the saved data with the key "greeting" from the SharedPreference object
        String Last_Date = prefs.getString("Last_Date", "Last Calculated Date:");
        String Last_BMI = prefs.getString("Last_BMI", "Last Calculated BMI:0.0");
        String Status = prefs.getString("Status", "");

        last_date.setText(Last_Date);
        last_bmi.setText(Last_BMI);
        status.setText(Status);

    }


}
