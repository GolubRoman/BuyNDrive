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

/**
 * Created by User on 27.02.2017.
 */

public class AddDialog extends DialogFragment {
    List<String> conditionList, saloonList;
    View dialog;
    MaterialSpinner condition_spinner, saloon_spinner;
    EditText color_edit_text, car_edit_text, price_edit_text;
    public interface AddDialogListener{
        void onAddItem(DialogInterface dialogInterface, String condition, String saloon, String color, String car, String price);
    }
    AddDialogListener addDialogListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        addDialogListener = (AddDialogListener)activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Dialog);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialog = inflater.inflate(R.layout.add_dialog, null);
        builder.setView(dialog).
                setTitle("Add your car").
                setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean numeric = isNumeric(price_edit_text.getText().toString());
                        if(condition_spinner.getText().toString().equals("Choose condition...") ||
                                saloon_spinner.getText().toString().equals("Choose saloon...") ||
                                color_edit_text.getText().toString().trim().length() == 0 ||
                                car_edit_text.getText().toString().trim().length() == 0 ||
                                price_edit_text.getText().toString().trim().length() == 0 ||
                                !numeric)
                            Toast.makeText(getContext(), "You cannot add car with such specifications!", Toast.LENGTH_SHORT).show();
                            else {
                            addDialogListener.onAddItem(dialogInterface,
                                    condition_spinner.getText().toString(), saloon_spinner.getText().toString(),
                                    color_edit_text.getText().toString(), car_edit_text.getText().toString(),
                                    price_edit_text.getText().toString());
                            dismiss();
                            Toast.makeText(getContext(), "Car was succesfully added!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        conditionList = Arrays.asList("Choose condition...", "New", "Used");
        saloonList = Arrays.asList("Choose saloon...", "Sedan", "Hatchback", "Coupe");
        condition_spinner = (MaterialSpinner)dialog.findViewById(R.id.add_condition);
        saloon_spinner = (MaterialSpinner)dialog.findViewById(R.id.add_saloon);
        color_edit_text = (EditText)dialog.findViewById(R.id.add_color);
        car_edit_text = (EditText)dialog.findViewById(R.id.add_car);
        price_edit_text = (EditText)dialog.findViewById(R.id.add_price);
        condition_spinner.setItems(conditionList);
        saloon_spinner.setItems(saloonList);
        return builder.create();
    }
    private static boolean isNumeric(String str)
    {
        try
        {
            double d = Double.parseDouble(str);
        }
        catch(NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
}
