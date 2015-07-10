package com.monroe.tipcalculator;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import java.text.NumberFormat;
import android.widget.SeekBar;
import android.widget.Toast;

public class TipCalculatorActivity extends ActionBarActivity
    implements OnEditorActionListener, OnClickListener
{
    // create private variables for the widgets
    private TextView percentTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private SeekBar percentSeekBar;
    private Button applyButton;
    private Button resetButton;
    private EditText billAmountEditText;

    private float tipPercent = .15f;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

        // get reference to the widget
        billAmountEditText = (EditText) findViewById(R.id.billAmountEditText);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        applyButton = (Button) findViewById(R.id.applyButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);

        // set your listeners
        billAmountEditText.setOnEditorActionListener(this);
        applyButton.setOnClickListener((View.OnClickListener) this);
        resetButton.setOnClickListener((View.OnClickListener) this);

        calculateAndDisplay();
    }

    public void calculateAndDisplay()
    {
        String billAmountString = billAmountEditText.getText().toString();
        float billAmount;
        if (billAmountString.equals(""))
        {
            billAmount = 0;
        }
        else
        {
            billAmount = Float.parseFloat(billAmountString);
        }

        int progress = percentSeekBar.getProgress();
        tipPercent = (float) progress / 100;

        float tipAmount = billAmount * tipPercent;
        float totalAmount = billAmount + tipAmount;

        NumberFormat currency = NumberFormat.getCurrencyInstance();
        tipTextView.setText(currency.format(tipAmount));
        totalTextView.setText(currency.format(totalAmount));

        NumberFormat percent = NumberFormat.getPercentInstance();
        percentTextView.setText(percent.format(tipPercent));
    }

    //Toast.makeText(this, "We are ready", Toast.LENGTH_SHORT).show();
    //Toast is just another item described in the book that does not work as shown
    //Do not want to spend hours to make something work that is supposed to help fix
    //things exactly like Toast no working!

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
    {
        if(actionId== EditorInfo.IME_ACTION_DONE || actionId== EditorInfo.IME_ACTION_UNSPECIFIED)
        {
            calculateAndDisplay();
        }
        return false;
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.applyButton:
                tipPercent = tipPercent - .01f;
                calculateAndDisplay();
                break;
            case R.id.resetButton:
                billAmountEditText.setText("");
                percentSeekBar.setProgress(15);
                tipPercent = .15f;
                calculateAndDisplay();
                break;
        }
    }
}
