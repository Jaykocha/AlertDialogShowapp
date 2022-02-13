package jasi.example.alertdialogshowcase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jasi.example.alertdialogshowcase.alertdialog.MyAlertDialog;
import jasi.example.alertdialogshowcase.alertdialog.AlertOptions;
import jasi.example.alertdialogshowcase.alertdialog.AlertType;

public class MainActivity extends AppCompatActivity implements MyAlertDialog.AlertDialogInterface {

    MyAlertDialog myDynamicAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button primaryBtn = findViewById(R.id.primaryBtn);
        Button successBtn = findViewById(R.id.successBtn);
        Button warningBtn = findViewById(R.id.warningBtn);
        Button oneOptionBtn = findViewById(R.id.oneOptionBtn);
        Button dynamicBtn = findViewById(R.id.dynamicBtn);

        primaryBtn.setOnClickListener(view -> new MyAlertDialog(
                MainActivity.this,
                MainActivity.this,
                AlertOptions.create(AlertType.examplePrimary))
                .show(getSupportFragmentManager(), null)
        );

        successBtn.setOnClickListener(view -> new MyAlertDialog(
                MainActivity.this,
                MainActivity.this,
                AlertOptions.create(AlertType.exampleSuccess))
                .show(getSupportFragmentManager(), null)
        );

        warningBtn.setOnClickListener(view -> new MyAlertDialog(
                MainActivity.this,
                MainActivity.this,
                AlertOptions.create(AlertType.exampleWarning))
                .show(getSupportFragmentManager(), null)
        );

        oneOptionBtn.setOnClickListener(view -> new MyAlertDialog(
                MainActivity.this,
                MainActivity.this,
                AlertOptions.create(AlertType.exampleOneOption))
                .show(getSupportFragmentManager(), null)
        );

        dynamicBtn.setOnClickListener(view -> {
                    myDynamicAlert = new MyAlertDialog(MainActivity.this, MainActivity.this,
                            AlertOptions.create(AlertType.dynamicAlert));
                    myDynamicAlert.show(getSupportFragmentManager(), null);
            String message = "This window will close automatically in %d seconds. Chose now or let it close on its own";
            new CountDownTimer(9000, 1000) {
                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long millisUntilFinished) {
                    myDynamicAlert.updateText(String.format(message, (millisUntilFinished / 1000) + 1));
                }

                @Override
                public void onFinish() {
                    myDynamicAlert.dismiss();
                }
            }.start();
        }
        );
    }

    @Override
    public void alertDialogMainOption(AlertType type) {
        switch (type) {
            case examplePrimary:
                Toast.makeText(this, "You chose the suggested option", Toast.LENGTH_SHORT).show();
                break;
            case exampleWarning:
                Toast.makeText(this, "You followed my warning. Great!", Toast.LENGTH_SHORT).show();
                break;
            case exampleSuccess:
                Toast.makeText(this, "Yay you made it!", Toast.LENGTH_SHORT).show();
                break;
            case dynamicAlert:
                Toast.makeText(this, "A dynamic alert is something beautiful", Toast.LENGTH_SHORT).show();
                break;
            case exampleOneOption:
                Toast.makeText(this, "You didn't have any real choice dign't you?", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void alertDialogAlternativeOption(AlertType type) {
        switch (type) {
            case examplePrimary:
                Toast.makeText(this, "You chose the alternative option", Toast.LENGTH_SHORT).show();
                break;
            case exampleWarning:
                Toast.makeText(this, "You decided to ignore my warning :(", Toast.LENGTH_SHORT).show();
                break;
            case exampleSuccess:
                Toast.makeText(this, "Ok, if you want to go that way", Toast.LENGTH_SHORT).show();
                break;
            case dynamicAlert:
                Toast.makeText(this, "Selected alternative option. Ok.", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}