package com.example.gisilk.onlineorder2.com.functions.OrderFood;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gisilk.onlineorder2.R;



import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyViewHolder> {
    private Context mContext;
    private List<Food> foodList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public FoodAdapter(Context mContext, List<Food> foodList) {
        this.mContext = mContext;
        this.foodList = foodList;
    }

    @Override
    public FoodAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.food_card, parent, false);

        return new FoodAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Food food = foodList.get(position);
        holder.title.setText(food.getName());
        holder.count.setText(food.getSize());;

        // loading album cover using Glide library
        try {
            Glide.with(mContext).load(food.getThumbnail()).into(holder.thumbnail);
        }catch (Exception e){
            Log.i("Error",e.getMessage());
            e.printStackTrace();
        }
        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });


        }

        /**
         * Showing popup menu when tapping on 3 dots
         */
        private void showPopupMenu (View view){
            // inflate menu
            PopupMenu popup = new PopupMenu(mContext, view);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_liquor, popup.getMenu());
            popup.setOnMenuItemClickListener(new FoodAdapter.MyMenuItemClickListener());
            popup.show();
        }

        /**
         * Click listener for popup menu items
         */
        class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

            public MyMenuItemClickListener() {
            }

            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.orderNow:
                        Toast.makeText(mContext, "Ordered ", Toast.LENGTH_SHORT).show();
                        return true;

                    default:
                }
                return false;
            }
        }

        @Override
        public int getItemCount () {
            return foodList.size();
        }
    }
