package jasi.example.alertdialogshowcase.alertdialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class AlertDialogView extends View {

    public AlertDialogView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setupPaint();
        rect = new RectF();
        displayMetrics = getDisplayMetrics();
    }

    // defines paint and canvas
    private Paint drawPaint;
    private final RectF rect;
    private final DisplayMetrics displayMetrics;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rect.right = displayMetrics.widthPixels - dp2px(20);
        rect.top = 0;
        rect.left = 0;
        rect.bottom = dp2px(270);
        canvas.drawPath(shapePath(), drawPaint);
    }

    // Setup paint with color and stroke styles
    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(Color.WHITE);
        drawPaint.setAntiAlias(true);
    }

    // This conversion is needed as drawing requires the actual pixels but in XML we specify in dp
    private int dp2px(int dp){
        return (int) (dp * displayMetrics.density + 0.5f);
    }

    private Path shapePath(){
        Path path = new Path();
        path.setFillType(Path.FillType.WINDING);
        path.addRoundRect(rect, 16, 16, Path.Direction.CW);
        path.addCircle((displayMetrics.widthPixels - dp2px(19))/2f, 0, dp2px(64), Path.Direction.CCW);
        return  path;
    }

    private DisplayMetrics getDisplayMetrics(){
        return Resources.getSystem().getDisplayMetrics();
    }
}
