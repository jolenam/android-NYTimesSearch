package com.example.jolenam.nytimessearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;

import com.example.jolenam.nytimessearch.R;
import com.example.jolenam.nytimessearch.SearchFilters;

public class FilterActivity extends AppCompatActivity {

    SearchFilters searchFilters;

    Spinner spSort, spMonth, spDay, spYear;

    boolean checkedArts, checkedFashion, checkedSports;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        spSort = (Spinner) findViewById(R.id.spSort);
        spMonth = (Spinner) findViewById(R.id.spinMonth);
        spDay = (Spinner) findViewById(R.id.spinDay);
        spYear = (Spinner) findViewById(R.id.spinYear);

    }


    public void onFilterSubmit(View view) {
        String sortType = spSort.getSelectedItem().toString();

        String month = spMonth.getSelectedItem().toString();
        String day = spDay.getSelectedItem().toString();
        String year = spYear.getSelectedItem().toString();

        searchFilters = new SearchFilters(month, day, year, sortType, checkedArts, checkedFashion, checkedSports);

        Intent data = new Intent();

        data.putExtra("filter", searchFilters);

        setResult(RESULT_OK, data);
        finish();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        checkedArts = false;
        checkedFashion = false;
        checkedSports = false;

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cbArts:
                if (checked) {
                    checkedArts = true;
                }
                break;
            case R.id.cbFashionStyle:
                if (checked) {
                    checkedFashion = true;
                }
                break;
            case R.id.cbSports:
                if (checked) {
                    checkedSports = true;
                }
                break;
        }
    }
}
