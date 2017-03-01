package com.golubroman.golub.warehouse;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by User on 27.02.2017.
 */

public class AddDialog extends DialogFragment {
    View dialog;
    List<String> conditionList = Arrays.asList("Choose condition...", "New", "Used"),
            saloonList = Arrays.asList("Choose saloon...", "Sedan", "Hatchback", "Coupe");
    MaterialSpinner condition_spinner, saloon_spinner;
    EditText color_edit_text, car_edit_text, price_edit_text;
    String dialogTitle = "Add your car",
            chooseCondition = "Choose condition...",
            chooseSaloon = "Choose saloon...",
            cannotAddCar = "You cannot add car with such specifications!",
            succesAddCar = "Car was succesfully added!";

    /* Defining of AddDialogListener interface for
            transmitting information from AddDialog to MainActivity
            */

    public interface AddDialogListener{
        void onAddItem(DialogInterface dialogInterface, String condition, String saloon, String color, String car, String price);
    }

    AddDialogListener addDialogListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addDialogListener = (AddDialogListener)activity;
    }

    /* Method for creating the dialog and views,
            that are into it
            */

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialog = inflater.inflate(R.layout.add_dialog, null);
        builder.setView(dialog).
                setTitle(dialogTitle).
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean numeric = isNumeric(price_edit_text.getText().toString());
                        if(condition_spinner.getText().toString().equals(chooseCondition) ||
                                saloon_spinner.getText().toString().equals(chooseSaloon) ||
                                color_edit_text.getText().toString().trim().length() == 0 ||
                                car_edit_text.getText().toString().trim().length() == 0 ||
                                price_edit_text.getText().toString().trim().length() == 0 ||
                                !numeric)
                            Toast.makeText(getContext(), cannotAddCar, Toast.LENGTH_SHORT).show();
                            else {
                            addDialogListener.onAddItem(dialogInterface,
                                    condition_spinner.getText().toString(), saloon_spinner.getText().toString(),
                                    color_edit_text.getText().toString(), car_edit_text.getText().toString(),
                                    price_edit_text.getText().toString());
                            dismiss();
                            Toast.makeText(getContext(), succesAddCar, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        /* Binding of dialog views with ButterKnife
            */

        condition_spinner = ButterKnife.findById(dialog, R.id.add_condition);
        saloon_spinner = ButterKnife.findById(dialog, R.id.add_saloon);
        color_edit_text = ButterKnife.findById(dialog, R.id.add_color);
        car_edit_text = ButterKnife.findById(dialog, R.id.add_car);
        price_edit_text = ButterKnife.findById(dialog, R.id.add_price);
        condition_spinner.setItems(conditionList);
        saloon_spinner.setItems(saloonList);

        return builder.create();
    }

    /* Method for checking String on being Numeric
            */

    private static boolean isNumeric(String str)
    {
        try{double d = Double.parseDouble(str);}
        catch(NumberFormatException nfe) {return false;}
        return true;
    }

}
