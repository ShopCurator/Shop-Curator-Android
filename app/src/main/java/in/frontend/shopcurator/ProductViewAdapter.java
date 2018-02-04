package in.frontend.shopcurator;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by keerthanamaddi on 4/2/18.
 */

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {
    private JSONArray mDataset;

private  Context mContext;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ProductViewAdapter(JSONArray myDataset,Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ProductViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        // Inflate the custom layout
        View productView = inflater.inflate(R.layout.list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(productView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        try {
            JSONObject object = (JSONObject) mDataset.get(position);
//            holder.mTextView.setText(mDataset.get(position));

            TextView productNameTextView = holder.productNameTextView;
            productNameTextView.setText(object.get("Name").toString());
            TextView priceTextView = holder.priceTextView;
            priceTextView.setText(object.get("Price").toString());
            String companyName = object.get("SellerCompany").toString();
            TextView companyTextView = holder.companyTextView;
            companyTextView.setText(companyName);
            LinearLayout bgColorView = holder.bgColorView;
//            bgColorView.setBackgroundColor(Color.parseColor(getColor(companyName)));
//            priceTextView.setBackgroundColor(Color.parseColor(getColor(companyName)));
//            companyTextView.setBackgroundColor(Color.parseColor(getColor(companyName)));
            LayerDrawable layerDrawable = (LayerDrawable) mContext.getResources()
                    .getDrawable(R.drawable.price_view_bg);
            GradientDrawable gradientDrawable = (GradientDrawable) layerDrawable
                    .findDrawableByLayerId(R.id.filling);
            gradientDrawable.setColor(Color.parseColor(getColor(companyName)));
            bgColorView.setBackgroundDrawable(layerDrawable);

        }catch (Exception e){

        }


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView productNameTextView;
        public TextView priceTextView;
        public TextView companyTextView;
        public LinearLayout bgColorView;
        //public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            productNameTextView = (TextView) itemView.findViewById(R.id.productNameView);
            priceTextView = (TextView) itemView.findViewById(R.id.priceView);
            companyTextView = (TextView) itemView.findViewById(R.id.companyNameView);
            bgColorView = (LinearLayout) itemView.findViewById(R.id.bgColorView);
           // messageButton = (Button) itemView.findViewById(R.id.message_button);
        }
    }


    public String getColor(String companyName){
        if(companyName.toLowerCase().contains("officeworks")){
            return "#005BAB";
        }else if(companyName.toLowerCase().contains("jbhifi")){
            return "#FFEC0F";
        }else if(companyName.toLowerCase().contains("harveynorman")){
            return "#004378";
        }else if(companyName.toLowerCase().contains("thegoodguys")){
            return "#1967AF";
        }else if(companyName.toLowerCase().contains("binglee")){
            return "#0066B3";
        }else{
            return "#cccccc";
        }
    }
}



