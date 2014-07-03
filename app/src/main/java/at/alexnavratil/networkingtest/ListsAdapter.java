package at.alexnavratil.networkingtest;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by alexnavratil on 30.06.14.
 */
public class ListsAdapter extends RecyclerView.Adapter<ListsAdapter.ViewHolder> {
    private String[] data = new String[]{"Einkaufsliste", "ToDo-Liste", "Wunschliste"};

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv;
        public ViewHolder(View v){
            super(v);
            this.tv = (TextView)v.findViewById(R.id.text);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lists_view, viewGroup, false);
        final TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setBackgroundColor(Color.WHITE);

        /**
         * Animation
         */
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    // create and start the animator for this view
                    // (the start radius is zero)
                    tv.setBackgroundColor(Color.LTGRAY);
                    ValueAnimator anim = ViewAnimationUtils.createCircularReveal(tv, (int)motionEvent.getX(), (int)motionEvent.getY(), 0, v.getWidth());
                    anim.setDuration(400);
                    anim.start();
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            tv.setBackgroundColor(Color.WHITE);
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {}
                        @Override
                        public void onAnimationRepeat(Animator animator) {}
                    });
                }
                return false;
            }
        });

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv = (TextView) view.findViewById(R.id.text);
                Toast.makeText(SampleActivity.context.getApplicationContext(), "The item " + tv.getText() + " got clicked!", Toast.LENGTH_SHORT).show();
            }
        });

        final ViewHolder holder = new ViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.tv.setText(data[i]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

}
