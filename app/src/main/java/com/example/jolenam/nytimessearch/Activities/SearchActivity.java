package com.example.jolenam.nytimessearch.Activities;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jolenam.nytimessearch.Article;
import com.example.jolenam.nytimessearch.ArticleArrayAdapter;
import com.example.jolenam.nytimessearch.EndlessRecyclerViewScrollListener;
import com.example.jolenam.nytimessearch.R;
import com.example.jolenam.nytimessearch.SpacesItemsDecoration;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    /*EditText etQuery;
    //GridView gvResults;
    Button btnSearch;*/

    ArrayList<Article> articles;
    ArticleArrayAdapter adapter;
    RecyclerView rvArticles;

    String savedQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupViews();
    }

    public void setupViews() {
        rvArticles = (RecyclerView) findViewById(R.id.rvArticles);
    }


    public void customLoadMoreDataFromApi(int page) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
        RequestParams params = new RequestParams();

        params.put("api-key", "7075fa7943644766a780d669cacbd68b");
        params.put("page", page);
        params.put("q", savedQuery);

        // make network request
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG", response.toString());
                JSONArray articleJsonResults = null;

                try {
                    articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                    articles.addAll(Article.fromJSONArray(articleJsonResults));
                    int curSize = adapter.getItemCount();
                    adapter.notifyItemRangeInserted(curSize, articles.size() - 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Get news on...");


        // attempt to change text color of SearchView query
        /*int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
        View searchPlate = searchView.findViewById(searchPlateId);

        if (searchPlate!=null) {
            int searchTextId = searchPlate.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
            TextView searchText = (TextView) searchPlate.findViewById(searchTextId);
            if (searchText!=null) {
                searchText.setTextColor(Color.BLUE);
                searchText.setHintTextColor(Color.WHITE);
            }
        }*/

        // Expand the search view and request focus
        //searchItem.expandActionView();
        //searchView.requestFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // allows for search results following first search to have endless scrolling
                rvArticles.clearOnScrollListeners();

                articles = new ArrayList<>();
                adapter = new ArticleArrayAdapter(articles);
                rvArticles.setAdapter(adapter);
                final StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
                staggeredGridLayoutManager.setGapStrategy(
                        StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
                rvArticles.setLayoutManager(staggeredGridLayoutManager);

                SpacesItemsDecoration decoration = new SpacesItemsDecoration(10);
                rvArticles.addItemDecoration(decoration);

                rvArticles.addOnScrollListener(new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount) {
                        customLoadMoreDataFromApi(page);
                    }
                });

                //String query = etQuery.getText().toString();
                savedQuery = query;

                AsyncHttpClient client = new AsyncHttpClient();
                String url = "https://api.nytimes.com/svc/search/v2/articlesearch.json";
                RequestParams params = new RequestParams();

                params.put("api-key", "7075fa7943644766a780d669cacbd68b");
                params.put("page", 0);
                params.put("q", query);

                // make network request
                client.get(url, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        Log.d("DEBUG", response.toString());
                        JSONArray articleJsonResults = null;

                        try {
                            articleJsonResults = response.getJSONObject("response").getJSONArray("docs");
                            Log.d("DEBUG", articleJsonResults.toString());
                            articles.clear();
                            articles.addAll(Article.fromJSONArray(articleJsonResults));
                            adapter.notifyDataSetChanged();
                            Log.d("DEBUG", articles.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onArticleSearch(View view) {


    }
}
