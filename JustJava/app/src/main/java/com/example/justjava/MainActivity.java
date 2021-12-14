package com.example.justjava;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    String orderSummary;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void decrement(View view)    {
        if (quantity>0) {
            quantity--;
            display(quantity);
        }
        else    {
            String text = "Quantity can't be negative";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }

    public void increment(View view)    {
        if(quantity >= 0 && quantity < 100) {
            quantity++;
            display(quantity);
        }
        else    {
            text = "Max. Cups reached";
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price = quantity*5;

        CheckBox whippedCreamCheck = (CheckBox) findViewById(R.id.whippedCream_checkBox);
        boolean creamCheck = whippedCreamCheck.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkBox);
        boolean chocolateCheck = chocolateCheckBox.isChecked();

        EditText nameField = (EditText) findViewById(R.id.name_field_view);
        String nameText = nameField.getText().toString();

        if (creamCheck) price += 1;
        if (chocolateCheck) price += 2;

        orderSummary = getString(R.string.order_summary_name, nameText) +
                "\n" + getString(R.string.order_summary_whipped_cream, creamCheck) +
                "\n" + getString(R.string.order_summary_chocolate, chocolateCheck) +
                "\n" + getString(R.string.order_summary_quantity, quantity) +
                "\n" + getString(R.string.order_summary_price ,Integer.toString(price))+
                "\n" + getString(R.string.thank_you);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, nameText));
        intent.putExtra(Intent.EXTRA_TEXT , orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     * @param number is used here for the no. of cups ordered.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}