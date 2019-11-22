package example.proyectocibertec;


import android.content.Context;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.GestureDetectorCompat;

public class MyImageCharlaMultimedia extends AppCompatImageView {

    private GestureDetectorCompat scrollGestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    public MyImageCharlaMultimedia(Context context) {
        super(context);
        init();
    }

    public MyImageCharlaMultimedia(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyImageCharlaMultimedia(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setScaleType(ScaleType.MATRIX);
        scrollGestureDetector = new GestureDetectorCompat(getContext(),
                new ScrollListener());
        scaleGestureDetector = new ScaleGestureDetector(getContext(),
                new ScaleListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scrollGestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    private float scaleFactor = 1f;
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scaleFactor = scaleFactor * detector.getScaleFactor();
            scaleFactor = Math.max(0.1f,Math.min(scaleFactor,5.0f));
            Matrix matrix = new Matrix(getImageMatrix());
            matrix.setScale(scaleFactor,scaleFactor,
                    detector.getFocusX(),detector.getFocusY());
            setImageMatrix(matrix);
            return true;
        }
    }

    float scrollX = getScrollX();
    float scrollY = getScrollY();
    private class ScrollListener extends GestureDetector.SimpleOnGestureListener{
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            scrollX = scrollX + distanceX;
            scrollY += distanceY;

            scrollX = Math.max(-10000f,Math.min(scrollX,getDrawable().getIntrinsicWidth()));
            scrollY = Math.max(-10000f,Math.min(scrollY,getDrawable().getIntrinsicHeight()));

            scrollTo((int) scrollX, (int) scrollY);
            return true;
        }
    }
}
