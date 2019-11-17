package com.reaksmeyarun.coffee.ui.main.fragment_menu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Item;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Category> categoryList;
    List<Item> itemList;
    Context context;
    View view;

    public CategoryAdapter(List<Category> categoryList,List<Item> itemList, Context context) {
        this.categoryList = categoryList;
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.custom_view_category,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(holder instanceof ViewHolder){
            Category category = categoryList.get(position);
            ((ViewHolder) holder).binding(category);
        }
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView mCategoryName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mCategoryName = itemView.findViewById(R.id.tvItem);
        }

        public void binding(Category category) {
            mCategoryName.setText(category.getCategoryName() != null ? category.getCategoryName() : "");
        }
    }
    public void addMoreCategory(List<Category> categoryList,List<Item> itemList){
        this.itemList.clear();
        this.categoryList.clear();
        this.itemList.addAll(itemList);
        this.categoryList.addAll(categoryList);
        notifyDataSetChanged();
    }
}
