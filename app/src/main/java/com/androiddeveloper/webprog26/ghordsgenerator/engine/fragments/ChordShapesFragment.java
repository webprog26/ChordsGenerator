package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments;

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
import com.androiddeveloper.webprog26.ghordsgenerator.engine.chords_generator_app.ChordsGeneratorApp;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.BitmapsArrayLoadedEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.events.LoadChordShapesBitmapsEvent;
import com.androiddeveloper.webprog26.ghordsgenerator.engine.managers.fragments_managers.ChordShapesFragmentManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment, that shows list og {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.Chord}
 * shapes images on the app's main screen
 */

public class ChordShapesFragment extends Fragment {

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

        EventBus.getDefault().register(this);

        Bundle args = getArguments();

        if(args != null){

            final String chordShapesTableName = args.getString(CHORD_SHAPES_TABLE_NAME);

            if(chordShapesTableName != null){

                //Initializing ChordShapesFragmentManager
                mChordShapesFragmentManager = new ChordShapesFragmentManager(chordShapesTableName, getActivity().getAssets());

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ChordShapesFragmentManager chordShapesFragmentManager = getChordShapesFragmentManager();

        if(chordShapesFragmentManager != null){
            //load bitmaps from assets via separate Thread, using EventBus
            chordShapesFragmentManager.loadShapesBitmaps();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");
        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.i(TAG, "onDestroy");
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

    /**
     * Handles {@link LoadChordShapesBitmapsEvent} Adds loaded from assets bitmaps to {@link ChordShapesFragmentManager} list
     * of {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape}s images
     * @param loadChordShapesBitmapsEvent {@link LoadChordShapesBitmapsEvent}
     */
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onLoadChordShapesBitmapsEvent(LoadChordShapesBitmapsEvent loadChordShapesBitmapsEvent){
        getChordShapesFragmentManager()
                .addShapesBitmapsToList(ChordsGeneratorApp
                                        .getChordsDBProvider()
                                        .getChordShapesBitmapsPath(loadChordShapesBitmapsEvent
                                                                   .getChordShapesTableName()));
    }

    /**
     * Handles {@link BitmapsArrayLoadedEvent} Update {@link ChordShapesAdapter} data with
     * {@link com.androiddeveloper.webprog26.ghordsgenerator.engine.models.ChordShape} bitmaps
     * @param bitmapsArrayLoadedEvent {@link BitmapsArrayLoadedEvent}
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBitmapsArrayLoadedEvent(BitmapsArrayLoadedEvent bitmapsArrayLoadedEvent){
        getChordShapesAdapter().updateAdapterData(bitmapsArrayLoadedEvent.getBitmaps());
    }
}
