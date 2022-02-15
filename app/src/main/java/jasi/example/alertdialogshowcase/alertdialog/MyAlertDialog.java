package jasi.example.alertdialogshowcase.alertdialog;

import android.app.AlertDialog;
import android.app.Dialog;
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
import androidx.lifecycle.ViewModelProvider;

import jasi.example.alertdialogshowcase.R;

public class MyAlertDialog extends DialogFragment {

    private static AlertDialogInterface mCallback;

    public MyAlertDialog() {
    }

    public static MyAlertDialog newInstance(AlertDialogInterface callback) {
        mCallback = callback;
        return new MyAlertDialog();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        //get shared ViewModel
        MyAlertDialogViewModel viewModel = new ViewModelProvider(requireActivity()).get(MyAlertDialogViewModel.class);

        //get AlertOptions which decide how the shown dialog will look and behave
        final AlertOptions mOptions = viewModel.getOptions().getValue() != null
                ? viewModel.getOptions().getValue()
                : AlertOptions.create(AlertType.unknownError);

        //-----------------------------------LOAD UI------------------------------------------------
        final View customView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog_layout, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(requireContext());
        alertBuilder.setView(customView);
        AlertDialog dialog = alertBuilder.create();
        // This is needed to display my custom shape
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        final ImageView iconIV = customView.findViewById(R.id.alert_dialog_icon);
        final FrameLayout circleFL = customView.findViewById(R.id.alert_dialog_circle);
        final TextView titleTV = customView.findViewById(R.id.alert_dialog_title);
        final TextView textTV = customView.findViewById(R.id.alert_dialog_text);
        final TextView alternativeOptionTV = customView.findViewById(R.id.alert_dialog_alternative_option);
        final TextView mainOptionTV = customView.findViewById(R.id.alert_dialog_main_option);

        iconIV.setImageDrawable(ContextCompat.getDrawable(requireContext(), mOptions.getIcon()));
        titleTV.setText(mOptions.getTitle());
        alternativeOptionTV.setText(mOptions.getAlternativeText());
        mainOptionTV.setText(mOptions.getMainText());
        setCancelable(mOptions.isCancelable());

        //if mainText field of AlertOptions is empty then hide it
        if (mOptions.getMainText().isEmpty()) {
            mainOptionTV.setVisibility(View.GONE);
        } else {
            mainOptionTV.setOnClickListener(v -> {
                mCallback.alertDialogMainOption(mOptions.getType());
                dismiss();
            });
        }

        //if alternativeText field of AlertOptions is empty then hide it
        if (mOptions.getAlternativeText().isEmpty()) {
            alternativeOptionTV.setVisibility(View.GONE);
        } else {
            alternativeOptionTV.setOnClickListener(v -> {
                mCallback.alertDialogAlternativeOption(mOptions.getType());
                dismiss();
            });
        }

        //change colors depending on AlertCategory
        AlertCategory alertCategory = getAlertCategory(mOptions.getType());
        if (alertCategory == AlertCategory.warning) {
            circleFL.setBackgroundResource(R.drawable.alert_dialog_circle_warning);
            alternativeOptionTV.setTextColor(requireContext().getColor(R.color.warning_color));
            mainOptionTV.setBackgroundResource(R.drawable.background_rounded_button_warning);
        } else if (alertCategory == AlertCategory.success) {
            circleFL.setBackgroundResource(R.drawable.alert_dialog_circle_success);
            alternativeOptionTV.setTextColor(requireContext().getColor(R.color.correct_green));
            mainOptionTV.setBackgroundResource(R.drawable.background_rounded_button_success);
        }

        //------------------------Observe changes in ViewModel---------------------------------------

        // I only alter the text in my dynamic alert dialog but here you could theoretically alter
        // everything dynamically
        viewModel.getOptions().observe(this, options -> {
            textTV.setText(options.getText());
            // titleTV.setText(options.getTitle()); <---[Example]
        });

        // I use the shared ViewModel to observe if from somewhere else cancel() has been called
        // If MyAlertDialogViewModel.cancel.getValue() == true, then dismiss dialog
        viewModel.isCanceled().observe(this, cancel -> {
            if (cancel) {
                dismiss();
            }
        });

        return dialog;
    }

    /**
     * @param type takes AlertType which is unique for every alert dialog of same purpose
     * @return AlertCategory (currently: primary, warning or success)
     */
    private AlertCategory getAlertCategory(AlertType type) {
        //category success
        if (type == AlertType.exampleSuccess) {
            return AlertCategory.success;
        }
        //category primary
        if (type == AlertType.dynamicAlert || type == AlertType.examplePrimary) {
            return AlertCategory.primary;
        } else {
            //category warning
            return AlertCategory.warning;
        }
    }

    public interface AlertDialogInterface {
        void alertDialogMainOption(AlertType type);

        void alertDialogAlternativeOption(AlertType type);
    }
}
