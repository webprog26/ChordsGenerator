package com.androiddeveloper.webprog26.ghordsgenerator.engine.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.ChordShapeImageClickEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Chord shapes adapter
 */

public class ChordShapesAdapter extends RecyclerView.Adapter<ChordShapesAdapter.ChordShapesViewholder>{

    private ArrayList<Bitmap> mChordShapesBitmapsList;
    private final Context mContext;

    public ChordShapesAdapter(ArrayList<Bitmap> chordShapesBitmapsList, Context context) {
        this.mChordShapesBitmapsList = chordShapesBitmapsList;
        this.mContext = context;
    }

    private ArrayList<Bitmap> getChordShapesBitmapsList() {
        return mChordShapesBitmapsList;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ChordShapesAdapter.ChordShapesViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChordShapesAdapter.ChordShapesViewholder(LayoutInflater.from(getContext()).inflate(R.layout.chord_image_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ChordShapesAdapter.ChordShapesViewholder holder, final int position) {
        final Bitmap chordShapeBitmap = getChordShapesBitmapsList().get(position);

        ImageView ivChordImage = holder.getIvChordImage();

        if(chordShapeBitmap != null)
            ivChordImage.setImageBitmap(chordShapeBitmap);

        ivChordImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChordShapeImageClickEvent(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return getChordShapesBitmapsList().size();
    }

    class ChordShapesViewholder extends RecyclerView.ViewHolder{

        @BindView(R.id.iv_chord_image)
        ImageView mIvChordImage;

        ChordShapesViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        ImageView getIvChordImage() {
            return mIvChordImage;
        }
    }

    private void setChordShapesList(ArrayList<Bitmap> chordShapesBitmaps) {
        this.mChordShapesBitmapsList = chordShapesBitmaps;
    }

    public void updateAdapterData(ArrayList<Bitmap> chordShapesBitmaps){
        setChordShapesList(chordShapesBitmaps);
        notifyDataSetChanged();
    }
}
