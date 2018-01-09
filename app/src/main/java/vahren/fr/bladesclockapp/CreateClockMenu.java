package vahren.fr.bladesclockapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class CreateClockMenu extends DialogFragment {

    public int nbSector;

    @Override
    public void setArguments(Bundle args) {
        nbSector = args.getInt("nbSector");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setView(inflater.inflate(R.layout.create_menu,null))
                .setTitle("Create new "+nbSector+"-clock")
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogPositiveClick(CreateClockMenu.this);
                    }
                })
                .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onDialogNegativeClick(CreateClockMenu.this);
                        CreateClockMenu.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    /* The activity that creates an instance of this dialog fragment must
     * implement this interface in order to receive event callbacks.
     * Each method passes the DialogFragment in case the host needs to query it. */
    public interface CreateClockMenuListener {
        public void onDialogPositiveClick(CreateClockMenu dialog);
        public void onDialogNegativeClick(CreateClockMenu dialog);
    }

    // Use this instance of the interface to deliver action events
    CreateClockMenuListener listener;

    // Override the Fragment.onAttach() method to instantiate the CreateClockMenuListener
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the CreateClockMenuListener so we can send events to the host
            listener = (CreateClockMenuListener) activity;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(activity.toString()
                    + " must implement CreateClockMenuListener");
        }
    }

}
