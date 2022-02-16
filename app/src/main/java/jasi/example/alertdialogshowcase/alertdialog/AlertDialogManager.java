package jasi.example.alertdialogshowcase.alertdialog;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class AlertDialogManager {

    /**
     * @param owner if out of Activity usually it is enough to just call 'this' or 'ActivityName.this'.
     *              if initialized in a fragment always call requireActivity() instead
     * @return MyAlertDialogViewModel which holds alert dialogs options
     */
    public static MyAlertDialogViewModel initializeViewModel(ViewModelStoreOwner owner) {
        return new ViewModelProvider(owner).get(MyAlertDialogViewModel.class);
    }

    /**
     * @param callback  usually this, or ActivityName.this. Has to be class which extends any FragmentActivity
     * @param type      the AlertType which defines what kind of alert to show
     * @param viewModel the MyAlertDialogViewModel instance of the activity of where this function is called
     * @param context   If called inside of a fragment, put requireActivity()
     */
    public static void showMyDialog(MyAlertDialog.AlertDialogInterface callback,
                                    AlertType type,
                                    MyAlertDialogViewModel viewModel,
                                    Context context
    ) {
        MyAlertDialog dialog = MyAlertDialog.newInstance(callback);
        viewModel.setOptions(AlertOptions.create(type));
        viewModel.showDialog(); //important otherwise won't show
        dialog.show(((FragmentActivity) context).getSupportFragmentManager(), type.toString());
    }
}
