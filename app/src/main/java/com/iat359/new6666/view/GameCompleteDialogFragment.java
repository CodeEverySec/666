package com.iat359.new6666.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class GameCompleteDialogFragment extends DialogFragment {

    public interface GameCompleteDialogListener {
        void onFragmentInteraction(boolean result);
    }

    private GameCompleteDialogFragment.GameCompleteDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (GameCompleteDialogFragment.GameCompleteDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement GameOverDialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Game Complete")
                .setMessage("666666......")
                .setNegativeButton("Keep Playing", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onFragmentInteraction(false);
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Quit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
        return builder.create();
    }
}
