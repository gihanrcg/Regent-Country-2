package com.example.gisilk.onlineorder2.com.functions.OrderLiquor;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
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


public class LiquorAdapter extends RecyclerView.Adapter<LiquorAdapter.MyViewHolder> {

    private Context mContext;
    private List<Liquor> liquorList;
    private String testTitle;

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


    public LiquorAdapter(Context mContext, List<Liquor> liquorList) {
        this.mContext = mContext;
        this.liquorList = liquorList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.liquor_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Liquor liquor = liquorList.get(position);
        holder.title.setText(liquor.getName());
        holder.count.setText(liquor.getSize());
        this.testTitle = liquor.getName();

        // loading album cover using Glide library
      //  Glide.with(mContext).load(liquor.getThumbnail()).into(holder.thumbnail);

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
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_liquor, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
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
                    Toast.makeText(mContext, "Ordered  " + testTitle, Toast.LENGTH_SHORT).show();
                    return true;

                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return liquorList.size();
    }
}
