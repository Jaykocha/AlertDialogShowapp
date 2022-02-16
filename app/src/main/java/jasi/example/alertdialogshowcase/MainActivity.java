package jasi.example.alertdialogshowcase;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import jasi.example.alertdialogshowcase.alertdialog.AlertDialogManager;
import jasi.example.alertdialogshowcase.alertdialog.AlertOptions;
import jasi.example.alertdialogshowcase.alertdialog.AlertType;
import jasi.example.alertdialogshowcase.alertdialog.MyAlertDialog;
import jasi.example.alertdialogshowcase.alertdialog.MyAlertDialogViewModel;

public class MainActivity extends AppCompatActivity implements MyAlertDialog.AlertDialogInterface {

    private MyAlertDialogViewModel alertDialogViewModel;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate shared ViewModel for alertDialogs
        alertDialogViewModel = AlertDialogManager.initializeViewModel(this);

        Button primaryBtn = findViewById(R.id.primaryBtn);
        Button successBtn = findViewById(R.id.successBtn);
        Button warningBtn = findViewById(R.id.warningBtn);
        Button oneOptionBtn = findViewById(R.id.oneOptionBtn);
        Button dynamicBtn = findViewById(R.id.dynamicBtn);

        primaryBtn.setOnClickListener(view -> showMyDialogFragment(AlertType.examplePrimary));

        successBtn.setOnClickListener(view -> showMyDialogFragment(AlertType.exampleSuccess));

        warningBtn.setOnClickListener(view -> showMyDialogFragment(AlertType.exampleWarning));

        oneOptionBtn.setOnClickListener(view -> showMyDialogFragment(AlertType.exampleOneOption));

        dynamicBtn.setOnClickListener(view -> {
                    showMyDialogFragment(AlertType.dynamicAlert);
                    updateAlertDialogTextEverySecond();
                }
        );
    }

    private void showMyDialogFragment(AlertType type) {
        AlertDialogManager.showMyDialog(this,
                type, alertDialogViewModel, this);
    }

    private void updateAlertDialogTextEverySecond() {
        String message = "This window will close automatically in %d seconds. Chose now or let it close on its own";
        countDownTimer = new CountDownTimer(10000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                AlertOptions currentOptions = alertDialogViewModel.getOptions().getValue();
                if (currentOptions != null) {
                    alertDialogViewModel.setOptions(currentOptions.updateText(
                            String.format(message, (millisUntilFinished / 1000) + 1)
                    ));
                }
            }

            @Override
            public void onFinish() {
                //update ViewModels property cancel to false which get's then observed by
                alertDialogViewModel.cancelAlert();
            }
        }.start();
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
                countDownTimer.cancel();
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
                countDownTimer.cancel();
                break;
        }
    }
}