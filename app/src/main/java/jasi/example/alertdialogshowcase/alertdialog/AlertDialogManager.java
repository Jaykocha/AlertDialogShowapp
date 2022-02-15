package jasi.example.alertdialogshowcase.alertdialog;

import android.content.Context;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class AlertDialogManager {
    public static MyAlertDialogViewModel initializeViewModel(ViewModelStoreOwner owner){
        return new ViewModelProvider(owner).get(MyAlertDialogViewModel.class);
    }

    public static void showMyDialog(MyAlertDialog.AlertDialogInterface callback,
                                    AlertType type,
                                    MyAlertDialogViewModel viewModel,
                                    Context context
                                    ){
        MyAlertDialog dialog = MyAlertDialog.newInstance(callback);
        viewModel.setOptions(AlertOptions.create(type));
        viewModel.showDialog(); //important otherwise won't show
        dialog.show(((FragmentActivity)context).getSupportFragmentManager(), type.toString());
    }

    public static boolean isVisible(Context context, AlertType type){
        FragmentManager fragmentManager = ((FragmentActivity)context).getSupportFragmentManager();
        return fragmentManager.findFragmentByTag(type.toString()) != null;
    }
}
