package com.randallarms.dionysium.guac_a_mole;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class MedalsDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Bundle args = getArguments();
        String name = args.getString("name", "");
        String desc = args.getString("desc", "");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_HOLO_DARK);

        builder.setMessage(desc).setTitle(name)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Closes the dialog
                    }
                });

        // Create the AlertDialog object and return it
        return builder.create();

    }

}