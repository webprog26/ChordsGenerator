package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androiddeveloper.webprog26.ghordsgenerator.R;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.adapters.ChordShapesAdapter;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.interfaces.callbacks.ChordShapesFragmentCallback;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.ChordShapesFragmentManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment, that shows list og {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord}
 * shapes images on the app's main screen
 */

public class ChordShapesFragment extends Fragment implements ChordShapesFragmentCallback{

    private static final String TAG = "ChordShapesFragment";

    //Tag to receive Chord shapes table name as an argument
    public static final String CHORD_SHAPES_TABLE_NAME = "chord_shapes_table_name";

    @BindView(R.id.rv_chord_images)
    RecyclerView mRvChordImages;

    private ChordShapesFragmentManager mChordShapesFragmentManager;
    private ChordShapesAdapter mChordShapesAdapter;
    private Unbinder unbinder;

    /**
     * Returns {@link ChordShapesFragment} filled with Chord shapes table name as an argument
     * @param chordShapesTableName {@link String}
     * @return ChordShapesFragment
     */
    public static ChordShapesFragment newInstance(String chordShapesTableName){
        Bundle args = new Bundle();
        args.putString(CHORD_SHAPES_TABLE_NAME, chordShapesTableName);
        ChordShapesFragment fragment = new ChordShapesFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();

        if(args != null){

            final String chordShapesTableName = args.getString(CHORD_SHAPES_TABLE_NAME);

            if(chordShapesTableName != null){

                //Initializing ChordShapesFragmentManager
                mChordShapesFragmentManager = new ChordShapesFragmentManager(chordShapesTableName, getActivity().getAssets(), this);

                //Initializing "empty" ChordShapesAdapter
                mChordShapesAdapter = new ChordShapesAdapter(mChordShapesFragmentManager.getChordShapesBitmaps(), getActivity());

                Log.i(TAG, "chordShapesTableName: " + chordShapesTableName);
            }

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chord_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        //Initialing RecyclerView
        initImagesRecyclerView();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        ChordShapesFragmentManager chordShapesFragmentManager = getChordShapesFragmentManager();

        if(chordShapesFragmentManager != null){

            getChordShapesFragmentManager().onStart();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ChordShapesFragmentManager chordShapesFragmentManager = getChordShapesFragmentManager();

        if(chordShapesFragmentManager != null){

            ChordShapesAdapter chordShapesAdapter = getChordShapesAdapter();

            if(!(chordShapesAdapter.getItemCount() > 0)){


                chordShapesFragmentManager.loadShapesBitmaps();

            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        ChordShapesFragmentManager chordShapesFragmentManager = getChordShapesFragmentManager();

        if(chordShapesFragmentManager != null){

            getChordShapesFragmentManager().onStop();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
        unbinder.unbind();
    }

    /**
     * Initializes chord images {@link RecyclerView} with necessary params
     */
    private void initImagesRecyclerView(){
        RecyclerView rvChordImages = getRvChordImages();
        rvChordImages.setHasFixedSize(true);
        rvChordImages.setItemAnimator(new DefaultItemAnimator());
        rvChordImages.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvChordImages.setAdapter(getChordShapesAdapter());
    }


    private RecyclerView getRvChordImages() {
        return mRvChordImages;
    }

    private ChordShapesFragmentManager getChordShapesFragmentManager() {
        return mChordShapesFragmentManager;
    }

    private ChordShapesAdapter getChordShapesAdapter() {
        return mChordShapesAdapter;
    }



    @Override
    public void onBitmapsArrayLoaded(ArrayList<Bitmap> bitmaps) {
        if(bitmaps != null){
            getChordShapesAdapter().updateAdapterData(bitmaps);
        }
    }
}
