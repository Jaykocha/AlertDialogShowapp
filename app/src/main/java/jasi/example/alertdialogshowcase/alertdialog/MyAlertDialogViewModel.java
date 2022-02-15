package jasi.example.alertdialogshowcase.alertdialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyAlertDialogViewModel extends ViewModel {
    private final MutableLiveData<AlertOptions> options = new MutableLiveData<>();
    private final MutableLiveData<Boolean> cancel = new MutableLiveData<>();

    public LiveData<AlertOptions> getOptions() {
        return options;
    }

    public void setOptions(AlertOptions newOptions) {
        options.setValue(newOptions);
    }

    public void showDialog() {
        cancel.setValue(false);
    }

    public void cancelAlert() {
        cancel.setValue(true);
    }

    public LiveData<Boolean> isCanceled() {
        return cancel;
    }
}
