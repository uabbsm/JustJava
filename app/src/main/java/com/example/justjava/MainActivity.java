package com.example.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText namefield = (EditText) findViewById(R.id.name_field);
        String name = namefield.getText().toString();

        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCreamCheckbox = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCheckBox = chocolateCheckBox.isChecked();

        int price = calculatePrice(quantity, hasWhippedCreamCheckbox, hasChocolateCheckBox);
        String priceMessage = createOrderSummary(name, price, hasWhippedCreamCheckbox, hasChocolateCheckBox);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: "));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(int quantity, boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;

        if (addWhippedCream) {

            basePrice = basePrice + 1;
        }

        if (addChocolate) {

            basePrice = basePrice + 2;
        }
        return this.quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {

        String priceMessage = getString(R.string.Name) + " " + name;
        priceMessage += "\n" + getString(R.string.addwhippedcream) + " " + addWhippedCream;
        priceMessage += "\n" + getString(R.string.addChocolate) + " " + addChocolate;
        priceMessage += "\n" + getString(R.string.quant) + " "  + quantity;
        priceMessage += "\n" + getString(R.string.total)  + " " + price;
        priceMessage += "\n" + getString(R.string.thankYou);
        return priceMessage;
    }


    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
        } else {
            quantity = 0;

            Context context = getApplicationContext();
            CharSequence text = "Não pode ser menor que 0";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}