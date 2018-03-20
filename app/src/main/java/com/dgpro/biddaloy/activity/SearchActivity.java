package com.dgpro.biddaloy.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.database.MatrixCursor;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dgpro.biddaloy.CallBack.RecycleViewItemClickListener;
import com.dgpro.biddaloy.Network.ApiUtil.ApiUtils;
import com.dgpro.biddaloy.Network.Model.SearchDataModel;
import com.dgpro.biddaloy.Network.Model.SearchModel;
import com.dgpro.biddaloy.Network.Remot.RetroService;
import com.dgpro.biddaloy.R;
import com.dgpro.biddaloy.adapter.SearchAdapter;
import com.dgpro.biddaloy.application.BiddaloyApplication;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity implements RecycleViewItemClickListener {

    BiddaloyApplication biddaloyApplication;

    SearchView searchView;
    List<String> suggestionsList;
    CursorAdapter suggestionAdapter;

    Spinner searchCatagorySpinner;
    String selectedUserCatagory = "Student";

    SearchAdapter mSearchAdapter;
    RecyclerView recyclerView;
    List<SearchDataModel> finalSearchList;

    int resultAction = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        biddaloyApplication = ((BiddaloyApplication)this.getApplicationContext());

        Toolbar myToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("Search User");

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        bindView();
        createSearchCatagorySpinner();
        createSearchView();
        setResltListView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
    void bindView(){
        finalSearchList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.search_list_recyclerview) ;
        searchCatagorySpinner = findViewById(R.id.search_catagory_spinner);
        searchView = (SearchView)findViewById(R.id.search_view);
    }
    void createSearchCatagorySpinner(){
        final String []searchSpinnerItem = {"Student","Guardian","Teacher"};
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,R.layout.spinner_search_row,searchSpinnerItem);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCatagorySpinner.setAdapter(spinnerAdapter);
        searchCatagorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("selected user type ", ""+searchSpinnerItem[i]);
                selectedUserCatagory = searchSpinnerItem[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    void createSearchView(){
        EditText searchEditText = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(getResources().getColor(R.color.white));
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));

        suggestionAdapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_1,
                null,
                new String[]{SearchManager.SUGGEST_COLUMN_TEXT_1},
                new int[]{android.R.id.text1},
                0);
        suggestionsList = new ArrayList<>();
        searchView.setSuggestionsAdapter(suggestionAdapter);

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                searchView.setQuery(suggestionsList.get(position), false);
                searchView.clearFocus();
                Log.e("suggestionClicked",": "+suggestionsList.get(position));
                getSearchList(suggestionsList.get(position),true);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchList(newText,false);
                return false;
            }
        });

        View closeButton = searchView.findViewById(android.support.v7.appcompat.R.id.search_close_btn);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) findViewById(R.id.search_src_text);
                et.setText("");
                finalSearchList.clear();
                mSearchAdapter.notifyDataSetChanged();
                //searchView.clearFocus();
            }
        });
    }

    void getSearchList(String suggestionStr, final Boolean suggestoinClicked){

        final RetroService mService = ApiUtils.getLoginDataService(biddaloyApplication.baseUrl);
        mService.searchUser(suggestionStr,selectedUserCatagory)
                .enqueue(new Callback<SearchModel>() {

                    @Override
                    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
                        if(response.isSuccessful()) {
                            suggestionsList.clear();
                            for(SearchDataModel mOdel:response.body().getSearch_result()){
                                String str = mOdel.getName();
                                //if(str.isEmpty() || str.equals("")){continue;}
                                suggestionsList.add(str);
                            }
                            if(suggestoinClicked){
                                finalSearchList.clear();
                                finalSearchList.addAll(response.body().getSearch_result());
                                //setResltListView();
                                mSearchAdapter.notifyDataSetChanged();
                                return;
                            }
                            String[] columns = {
                                    BaseColumns._ID,
                                    SearchManager.SUGGEST_COLUMN_TEXT_1,
                                    SearchManager.SUGGEST_COLUMN_INTENT_DATA
                            };

                            MatrixCursor cursor = new MatrixCursor(columns);

                            for (int i = 0; i < suggestionsList.size(); i++) {
                                String[] tmp = {Integer.toString(i), suggestionsList.get(i),suggestionsList.get(i)};
                                cursor.addRow(tmp);
                            }
                            suggestionAdapter.swapCursor(cursor);
                        }else {
                            int statusCode  = response.code();
                            Log.e("fail ","fail to download by retrofit");
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchModel> call, Throwable t) {
                        Log.e("error ","error to download by retrofit");
                    }
                });
    }
    void setResltListView(){
        mSearchAdapter = new SearchAdapter(this,finalSearchList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(mSearchAdapter);
    }

    @Override
    public void onItemClick(int position) {
        SearchDataModel sData = finalSearchList.get(position);
        sData.setCategory(selectedUserCatagory);
        if(null == getCallingActivity()){

            Intent intent = new Intent(this,SearchDetailsActivity.class);

            Bundle bundle = new Bundle();
            bundle.putParcelable("search_result", Parcels.wrap(sData));

            intent.putExtras(bundle);
            startActivity(intent);

            //this.finish();
        }
        else{

            Intent output = new Intent();
            output.putExtra("name", sData.getName());
            output.putExtra("category", selectedUserCatagory);
            output.putExtra("id", sData.getId());
            setResult(RESULT_OK, output);
            finish();
        }
    }
}
