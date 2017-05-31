package com.androiddeveloper.webprog26.ghordsgenerator.engine.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.androiddeveloper.webprog26.ghordsgenerator.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Reference dialog
 */

public class ReferenceDialog extends DialogFragment{

    public static final String REFERENCE_CHORD_TITLE = "reference_chord_title";


    @BindView(R.id.iv_reference_image)
    ImageView mIvReferenceImage;

    @BindView(R.id.tv_reference_text)
    TextView mTvReferenceText;

    private Unbinder unbinder;

    public static ReferenceDialog newInstance(String chordTitle){
        Bundle args = new Bundle();
        args.putString(REFERENCE_CHORD_TITLE, chordTitle);
        ReferenceDialog referenceDialog = new ReferenceDialog();
        referenceDialog.setArguments(args);

        return referenceDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.reference));

        View referenceDialogView = getActivity().getLayoutInflater().inflate(R.layout.reference_dialog, null);

        unbinder = ButterKnife.bind(this, referenceDialogView);

        builder.setView(referenceDialogView);

        builder.setCancelable(true);

        if(getArguments() != null){

            String referenceTextString = getArguments().getString(REFERENCE_CHORD_TITLE);

            if(referenceTextString != null){
                getTvReferenceText().setText(getString(R.string.reference_text, referenceTextString));
            }
        }

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReferenceDialog.this.dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public ImageView getIvReferenceImage() {
        return mIvReferenceImage;
    }

    public TextView getTvReferenceText() {
        return mTvReferenceText;
    }
}
