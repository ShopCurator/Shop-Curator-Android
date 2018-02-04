package in.frontend.shopcurator;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainLaunchActivity extends AppCompatActivity {

    Context mContext;
    RecyclerView mRecyclerView;

    TextView notFound;
    EditText productSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_launch);


        mRecyclerView = (RecyclerView) findViewById(R.id.product_list_view);
        notFound = (TextView) findViewById(R.id.notFound);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mContext = this.getApplicationContext();


        ImageButton b = (ImageButton) findViewById(R.id.searchButton);
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                searchForProduct(v);
            }
        });
        productSearchView = (EditText) findViewById(R.id.searchProduct);
        //productSearchView.setFocusedByDefault(true);

    }

    protected void searchForProduct(View view) {

        String product = productSearchView.getText().toString();
        hideKeyboard(this);
        getSearchResults(product);
    }

    protected void getSearchResults(String product) {
        String url = "http://54.169.53.19/api/values/" + product;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        try {
                            if (response.length() > 0) {
                                ProductViewAdapter mAdapter = new ProductViewAdapter(response, mContext);
                                mRecyclerView.setAdapter(mAdapter);
                                mRecyclerView.setVisibility(View.VISIBLE);
                                notFound.setVisibility(View.GONE);
                            } else {
                                notFound.setVisibility(View.VISIBLE);
                                mRecyclerView.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {

                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(jsonArrayRequest);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


}
