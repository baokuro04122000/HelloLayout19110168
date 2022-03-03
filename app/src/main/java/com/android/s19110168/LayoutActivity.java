package com.android.s19110168;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

class AppsModel {
    /** Step 1: Variables **/
    private String name;
    private int price;
    private int thumbnail;

    /** Step 2:Constructors: **/
    public AppsModel(){

    }
    public AppsModel(String name, int price, int thumbnail) {
        this.name = name;
        this.price = price;
        this.thumbnail = thumbnail;
    }
    /** Step 3: getters & setters **/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
class AppsAdapter extends
        RecyclerView.Adapter<AppsAdapter.MyViewHolder>
{
    private Context context;
    private List<AppsModel> appList;



    /** Step 1: My view holder Class **/
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView title, appDown;
        public ImageView thumbnail;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //Initializing the views
            title = itemView.findViewById(R.id.title);
            appDown = itemView.findViewById(R.id.price);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }
    }
    /** Step 2: Variables & Constructors **/
    public AppsAdapter(Context context, List<AppsModel> appList) {
        this.context = context;
        this.appList = appList;
    }

    /** Step 3: Implementing Methods of Adapter **/
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_activity, parent, false);
        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AppsModel appsModel = appList.get(position);


        holder.title.setText(appsModel.getName());
        holder.appDown.setText("$"+appsModel.getPrice());

        // Displaying the image using Glide Library
        Glide.with(context)
                .load(appsModel.getThumbnail())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }
}
public class LayoutActivity extends AppCompatActivity {

    //Step 1: Declaring some variables
    private List<AppsModel> appsList;
    private AppsAdapter adapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // RecyclerView Widget
        recyclerView = findViewById(R.id.recycler_view);

        appsList = new ArrayList<>();
        adapter = new AppsAdapter(this,appsList);


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);

        //Item Decoration:
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2,dpToPx(10),true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // preparing the cards:
        InsertDataIntoCards();
    }




    private void InsertDataIntoCards() {
        // Add the cards data and display them:
        int[] appsCovers = new int[]{
                R.drawable.bag,
                R.drawable.sunglass,
                R.drawable.belt,
                R.drawable.ring,
                R.drawable.thing,
                R.drawable.short_1

        };

        AppsModel a = new AppsModel("Vagabond sack",120,appsCovers[0]);
        appsList.add(a);

        a = new AppsModel("Stella sunglasses",58,appsCovers[1]);
        appsList.add(a);

        a = new AppsModel("Whitney belt",35,appsCovers[2]);
        appsList.add(a);

        a = new AppsModel("Garden strand",98,appsCovers[3]);
        appsList.add(a);

        a = new AppsModel("Wood thing",10,appsCovers[4]);
        appsList.add(a);

        a = new AppsModel("Sport short",5,appsCovers[5]);
        appsList.add(a);




    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,r.getDisplayMetrics()));
    }


    private class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {

            int position = parent.getChildAdapterPosition(view);
            int column = position % spanCount;

            if(includeEdge){
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1)* spacing / spanCount;

                if(position < spanCount){
                    outRect.top = spacing;
                }
                outRect.bottom = spacing;
            }else{
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing /spanCount;
                if(position >= spanCount){
                    outRect.top = spacing;
                }
            }
        }
    }




}

