package jasi.example.alertdialogshowcase.alertdialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import jasi.example.alertdialogshowcase.R;

public class MyAlertDialog extends DialogFragment {

    public interface AlertDialogInterface {
        void alertDialogMainOption(AlertType type);
        void alertDialogAlternativeOption(AlertType type);
    }

    public MyAlertDialog(Context context, AlertDialogInterface callback, AlertOptions options){
        this.context = context;
        this.options = options;
        this.callback = callback;
    }

    private TextView textTV;
    private AlertDialog dialog;
    private final Context context;
    private final AlertOptions options;
    private final AlertDialogInterface callback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View customView = LayoutInflater.from(context).inflate(R.layout.alert_dialog_layout, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setView(customView);

        final ImageView iconIV = customView.findViewById(R.id.alert_dialog_icon);
        final FrameLayout circleFL = customView.findViewById(R.id.alert_dialog_circle);
        final TextView titleTV = customView.findViewById(R.id.alert_dialog_title);
        textTV = customView.findViewById(R.id.alert_dialog_text);
        final TextView alternativeOptionTV = customView.findViewById(R.id.alert_dialog_alternative_option);
        final TextView mainOptionTV = customView.findViewById(R.id.alert_dialog_main_option);

        iconIV.setImageDrawable(ContextCompat.getDrawable(context, options.getIcon()));
        titleTV.setText(options.getTitle());
        textTV.setText(options.getText());
        alternativeOptionTV.setText(options.getAlternativeText());
        mainOptionTV.setText(options.getMainText());
        setCancelable(options.isCancelable());

        if (options.getMainText().isEmpty()) {
            mainOptionTV.setVisibility(View.GONE);
        }else{
            mainOptionTV.setOnClickListener(v -> {
                callback.alertDialogMainOption(options.getType());
                dialog.cancel();
            });
        }

        if (options.getAlternativeText().isEmpty()) {
            alternativeOptionTV.setVisibility(View.GONE);
        } else {
            alternativeOptionTV.setOnClickListener(v -> {
                callback.alertDialogAlternativeOption(options.getType());
                dialog.cancel();
            });
        }

        //depending on AlertCategory
        AlertCategory alertCategory = getAlertCategory(options.getType());
        if (alertCategory == AlertCategory.warning) {
            circleFL.setBackgroundResource(R.drawable.alert_dialog_circle_warning);
            alternativeOptionTV.setTextColor(context.getColor(R.color.warning_color));
            mainOptionTV.setBackgroundResource(R.drawable.background_rounded_button_warning);
        }else if(alertCategory == AlertCategory.success){
            circleFL.setBackgroundResource(R.drawable.alert_dialog_circle_success);
            alternativeOptionTV.setTextColor(context.getColor(R.color.correct_green));
            mainOptionTV.setBackgroundResource(R.drawable.background_rounded_button_success);
        }

        dialog = alertBuilder.create();

        // This is needed to display my custom shape
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return dialog;
    }

    private AlertCategory getAlertCategory(AlertType type) {
        //category success
        if (type == AlertType.exampleSuccess) {
            return AlertCategory.success;
        }
        //category primary
        if (type == AlertType.dynamicAlert ||type == AlertType.examplePrimary) {
            return AlertCategory.primary;
        } else {
            //category warning
            return AlertCategory.warning;
        }
    }

    public void updateText(String newText) {
        textTV.setText(newText);
    }
}
