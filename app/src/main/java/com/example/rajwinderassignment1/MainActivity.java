package com.example.rajwinderassignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.icu.text.DecimalFormat;
import android.icu.text.Transliterator;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    //For fill static value in spinner
    private String pizzaSize[] = {"Select","Medium","Large","XL"};

    //For Display Province in autocomplete
    String[] canadaProvince  ={"Alberta","British Columbia","Manitoba","New Brunswick","Newfoundland and Labrador","Northwest Territories",
            "Nova Scotia","Nunavut","Ontario","Prince Edward Island","Quebec","Saskatchewan","Yukon"};

    // variables to give reference to id in layout elements
    private Spinner spnPizzaSize;
    private RadioButton veggiePizza,nonVeggiePizza,olives,bellPepper,cherryTomatoes,ham,bacon,sausages;
    private EditText customerName,salesDate;
    private AutoCompleteTextView province;
    private CheckBox sauce,cheese;
    private TextView toppingLabel;
    private RadioGroup vegPizzaTopping,nonVegPizzaTopping;
    private Button submit;

    //to access values globally
    String selectedPizzaSize,selectedPizzaType,selectedTopping,selectedSauceChesse;

    //For Sales Date
    DatePickerDialog datePickerObj;
    private double cost=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get reference of all elements in layout by using their ID
        customerName = (EditText) findViewById(R.id.customerName);
        veggiePizza = (RadioButton) findViewById(R.id.veggiePizza);
        nonVeggiePizza = (RadioButton) findViewById(R.id.nonVeggiePizza);
        toppingLabel = (TextView) findViewById(R.id.toppings);
        vegPizzaTopping = (RadioGroup) findViewById(R.id.vegPizzaToppings);
        nonVegPizzaTopping = (RadioGroup) findViewById(R.id.nonVegPizzaToppings);
        sauce = (CheckBox) findViewById(R.id.sauce);
        cheese = (CheckBox) findViewById(R.id.cheese);
        olives = (RadioButton) findViewById(R.id.olives);
        bellPepper = (RadioButton) findViewById(R.id.bellPepper);
        cherryTomatoes = (RadioButton) findViewById(R.id.cherryTomatoes);
        ham = (RadioButton) findViewById(R.id.ham);
        bacon = (RadioButton) findViewById(R.id.bacon);
        sausages = (RadioButton) findViewById(R.id.sausages);
        submit = (Button) findViewById(R.id.submit);
        spnPizzaSize = (Spinner) findViewById(R.id.sizeOfPizza);

        //use array adapter to fill pizza size spinner
        ArrayAdapter adtPizzaSize = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,pizzaSize);
        adtPizzaSize.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spnPizzaSize.setAdapter(adtPizzaSize);

        //at starting as customer did not select any veg or non veg pizza, toppings are hiding.
        vegPizzaTopping.setVisibility(View.GONE);
        nonVegPizzaTopping.setVisibility(View.GONE);
        toppingLabel.setVisibility(View.GONE);

        //On change of pizza type
        final RadioGroup pizzaTypeGroup = (RadioGroup) findViewById(R.id.pizzaType);
        pizzaTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int id = pizzaTypeGroup.getCheckedRadioButtonId();
                toppingLabel.setVisibility(View.VISIBLE);
                switch (id){
                    case R.id.veggiePizza:
                        selectedPizzaType = veggiePizza.getText().toString();
                        nonVegPizzaTopping.setVisibility(View.INVISIBLE);
                        vegPizzaTopping.setVisibility(View.VISIBLE);
                        break;
                    case R.id.nonVeggiePizza:
                        selectedPizzaType = nonVeggiePizza.getText().toString();
                        vegPizzaTopping.setVisibility(View.INVISIBLE);
                        nonVegPizzaTopping.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        //Autocomplete Province
        province = (AutoCompleteTextView)findViewById(R.id.province);
        ArrayAdapter<String> adtProvince = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,canadaProvince);
        province.setAdapter(adtProvince);


        //To get Date Calender for Sales Date
        salesDate = (EditText)findViewById(R.id.saleDate);
        salesDate.setInputType(InputType.TYPE_NULL);
        salesDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calender = Calendar.getInstance();
                int daySales = calender.get(Calendar.DAY_OF_MONTH);
                int monthSales = calender.get(Calendar.MONTH);
                int yearSales = calender.get(Calendar.YEAR);

                datePickerObj = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        salesDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },yearSales,monthSales,daySales);
                datePickerObj.show();
            }
        });

        //on click of submit button
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //add cost according to veg and non veg pizza
                if(veggiePizza.isChecked())
                    cost = cost + 0.75;
                else if(nonVeggiePizza.isChecked())
                    cost = cost + 1.50;

                //selected value from Veggie Toppings
               if(veggiePizza.isChecked()){
                   if(olives.isChecked())
                      selectedTopping = olives.getText().toString();
                   else if(bellPepper.isChecked())
                      selectedTopping = bellPepper.getText().toString();
                   else if(cherryTomatoes.isChecked())
                      selectedTopping = cherryTomatoes.getText().toString();
               }else{
                   if(ham.isChecked())
                       selectedTopping = ham.getText().toString();
                   else if(bacon.isChecked())
                       selectedTopping = bacon.getText().toString();
                   else if(sausages.isChecked())
                       selectedTopping = sausages.getText().toString();
               }

                //Selected value from spinner
                if(spnPizzaSize.getSelectedItem().toString() == "Medium"){
                    selectedPizzaSize ="Medium";
                    cost = cost + 6.99;
                } else if(spnPizzaSize.getSelectedItem().toString() == "Large"){
                    selectedPizzaSize = "Large";
                    cost = cost + 8.99;
                } else if(spnPizzaSize.getSelectedItem().toString()  == "XL"){
                    selectedPizzaSize = "XL";
                    cost = cost + 10.99;
                }

                //add cost according to selected checkbox
                if(cheese.isChecked() && sauce.isChecked()){
                    selectedSauceChesse = sauce.getText().toString() +" and "+ cheese.getText().toString();
                    cost = cost + 1.00 + 1.25;
                }
                else if(cheese.isChecked()){
                    selectedSauceChesse = cheese.getText().toString();
                    cost = cost + 1.25;
                }
                else if(sauce.isChecked()){
                    selectedSauceChesse = sauce.getText().toString();
                    cost = cost + 1.00;
                }

                // apply 13% tax on calculated cost
                cost = cost + cost * 0.13;

                //round off cost with two decimal points
                String costFormat = String.format("%.2f",cost);

                // use a string variables to store message
                String output = "On "+salesDate.getText()+",for " +customerName.getText()+" from "+province.getText()+ " a "+selectedPizzaSize
                        +" " +selectedPizzaType+ ", with "+selectedSauceChesse+ ", and "+selectedTopping+" toppings, cost: $"+costFormat+"";

                //use a toast to display result
                Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
            }
        });
    }
}