package jasi.example.alertdialogshowcase.alertdialog;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

import jasi.example.alertdialogshowcase.R;


/**
 * Class which holds the options for AlertDialogMaster
 */
public class AlertOptions {
    private final String title;
    private final String text;
    private final String alternativeText;
    private final String mainText;
    private final int icon;
    private final boolean isCancelable;
    private final AlertType type;

    /**
     * Class which holds the options for AlertDialogMaster
     *
     * @param title           String to show as title
     * @param text            String to show as text
     * @param alternativeText String to be displayed at alternative button
     * @param mainText        String to be displayed at main button
     * @param icon            Resource ID of the icon drawable
     * @param isCancelable    True if should be cancelable
     * @param type            The AlertType (to be returned as callback to calling activity)
     */
    public AlertOptions(String title,
                        String text,
                        String alternativeText,
                        String mainText,
                        @DrawableRes int icon,
                        boolean isCancelable,
                        AlertType type) {
        this.title = title;
        this.text = text;
        this.alternativeText = alternativeText;
        this.mainText = mainText;
        this.icon = icon;
        this.isCancelable = isCancelable;
        this.type = type;
    }

    public static AlertOptions create(AlertType type) {
        switch (type) {
            case examplePrimary:
                return new AlertOptions(
                        "Primary Alert!",
                        "This is an example of a simple primary alert. I sometimes call them also info alerts.",
                        "No, thanks",
                        "Suggested Option",
                        R.drawable.icon_primary,
                        false,
                        type);
            case exampleWarning:
                return new AlertOptions(
                        "Warning Alert!",
                        "This is an example of a warning alert. I show this to users when they have to be careful.",
                        "I don't care",
                        "Do as I should",
                        R.drawable.icon_warning,
                        false,
                        type);
            case exampleSuccess:
                return new AlertOptions(
                        "Success Alert!",
                        "Bravo you successfully opened a success alert! Good job. Check out the dynamic alert as well!",
                        "Back",
                        "Finish",
                        R.drawable.icon_success,
                        true,
                        type);
            case exampleOneOption:
                return new AlertOptions(
                        "Just to notify you!",
                        "This is a sigle option alert. Do you wish to proceed?",
                        "",
                        "Proceed",
                        R.drawable.icon_primary,
                        false,
                        type
                );
            case dynamicAlert:
                return new AlertOptions(
                        "Dynamic Alert!",
                        "This is a dynamic alert",
                        "Option",
                        "Better Option",
                        R.drawable.icon_primary,
                        false,
                        type);
            default:
                return new AlertOptions(
                        "Unknown Error",
                        "Ups, something went wrong :(",
                        "",
                        "close",
                        R.drawable.icon_warning,
                        false,
                        AlertType.unknownError
                );
        }
    }

    public AlertOptions updateText(String newText){
        return new AlertOptions(
                title,
                newText,
                alternativeText,
                mainText,
                icon,
                isCancelable,
                type
        );
    }

    public AlertType getType() {
        return type;
    }

    public int getIcon() {
        return icon;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public String getMainText() {
        return mainText;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCancelable() {
        return isCancelable;
    }

}