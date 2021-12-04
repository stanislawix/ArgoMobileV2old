package pl.argo.argomobile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.SeekBar;

import androidx.appcompat.widget.AppCompatSeekBar;

@SuppressLint("AppCompatCustomView")
public class SeekBarWithValue extends SeekBar {

    public SeekBarWithValue(Context context) {
        super(context);
    }

    public SeekBarWithValue(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SeekBarWithValue(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int thumb_x = (int) (((double)this.getProgress()/this.getMax()) * (double)(this.getWidth() - this.getPaddingLeft() - this.getPaddingRight()));
        float middle = (float) (this.getHeight());

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(40);
        canvas.drawText(this.getProgress() + " Hz", thumb_x, middle, paint);
    }
}
