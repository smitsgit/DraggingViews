package com.example.smitald.draggingviews;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by smitald on 7/19/2015.
 */
public class DropTargetView extends ImageView implements View.OnDragListener {

    private boolean mDropped;


    public DropTargetView(Context context) {
        super(context);
        init();
    }

    public DropTargetView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DropTargetView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

/*    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DropTargetView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }*/

    private void init (){
        setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        PropertyValuesHolder phvX, phvY;

        switch (event.getAction()){
            case DragEvent.ACTION_DRAG_STARTED:
                 phvX = PropertyValuesHolder.ofFloat("scaleX", 0.5f);
                 phvY = PropertyValuesHolder.ofFloat("scaleY", 0.5f);
                 ObjectAnimator.ofPropertyValuesHolder(this, phvX, phvY).start();

                 // clear the current Drop Image on a new Event
                 setImageDrawable(null);
                 mDropped = false;
                 break;
            case DragEvent.ACTION_DRAG_ENDED:
                if(!mDropped) {
                    phvX = PropertyValuesHolder.ofFloat("scaleX", 1f);
                    phvY = PropertyValuesHolder.ofFloat("scaleY", 1f);
                    ObjectAnimator.ofPropertyValuesHolder(this, phvX, phvY).start();
                    mDropped = false;
                }
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                phvX = PropertyValuesHolder.ofFloat("scaleX", 0.75f);
                phvY = PropertyValuesHolder.ofFloat("scaleY", 0.75f);
                ObjectAnimator.ofPropertyValuesHolder(this, phvX, phvY).start();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                phvX = PropertyValuesHolder.ofFloat("scaleX", 0.5f);
                phvY = PropertyValuesHolder.ofFloat("scaleY", 0.5f);
                ObjectAnimator.ofPropertyValuesHolder(this, phvX, phvY).start();
                break;
            case DragEvent.ACTION_DROP:
                Keyframe frame_0 = Keyframe.ofFloat(0f, 0.75f);
                Keyframe frame_1 = Keyframe.ofFloat(0.5f, 1f);
                Keyframe frame_2 = Keyframe.ofFloat(1f, 0.75f);
                phvX = PropertyValuesHolder.ofKeyframe("scaleX", frame_0, frame_1, frame_2);
                phvY = PropertyValuesHolder.ofKeyframe("scaleY", frame_0, frame_1, frame_2);
                ObjectAnimator.ofPropertyValuesHolder(this, phvX, phvY).start();

                setImageDrawable((Drawable) event.getLocalState());
                mDropped = true;
                break;
            default:
                 return false;
        }
        return true;
    }
}
