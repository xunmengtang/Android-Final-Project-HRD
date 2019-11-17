package com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> implements Filterable {

    List<Category> categoryList;
    List<Category> categoryListFiltered;
    View view;
    Context context;

    public CategoryAdapter(List<Category> categoryList, Context context) {
        this.categoryList = categoryList;
        this.categoryListFiltered = categoryList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO
        view = LayoutInflater.from(context).inflate(R.layout.custom_edit_category_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //TODO
        if(holder instanceof CategoryAdapter.ViewHolder){
            Category category = categoryListFiltered.get(position);
            ((CategoryAdapter.ViewHolder) holder).binding(category);
        }
    }

    @Override
    public int getItemCount() {
        return categoryListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String key = constraint.toString();
                if(key.isEmpty()){
                    categoryListFiltered = categoryList;
                }else {
                    List<Category> lstCategory = new ArrayList<>();
                    for(Category category : categoryList){
                        if(category.getCategoryName().toLowerCase().contains(key.toLowerCase())){
                            lstCategory.add(category);
                        }
                    }
                    categoryListFiltered = lstCategory;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = categoryListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                categoryListFiltered = (List<Category>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCategoryName;
        ImageView btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }

        public void binding(Category category){
            tvCategoryName.setText(category.getCategoryName() !=null ? category.getCategoryName() : "");

            btnDelete.setOnClickListener(v->{
                if(categoryCallBack!=null){
                    PopupMenu menu=new PopupMenu(context,v);
                    menu.inflate(R.menu.edit_and_remove_menu);
                    menu.setOnMenuItemClickListener(b->{
                        switch (b.getItemId()){
                            case R.id.btnEdit:
                                categoryCallBack.onEditCategory(category,getAdapterPosition());
                                return true;
                            case R.id.btnRemove:
                                new AlertDialog.Builder(context)
                                        .setTitle("Alert")
                                    .setMessage(R.string.alert_delete)
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    })
                                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            remove(getAdapterPosition());
                                            categoryCallBack.onDeleteCategory(category);
                                        }
                                    })
                                    .show();
                                return true;

                                default : return false;
                        }
                    });
                    menu.show();
                }
            });
        }
    }

    public void remove(int i) {
        this.categoryList.remove(i);
        notifyDataSetChanged();
    }

    interface CategoryCallBack{
        void onEditCategory(Category category, int position);
        void onDeleteCategory(Category category);
    }

    private CategoryCallBack categoryCallBack;

    public void setCategoryCallBack(CategoryCallBack categoryCallBack) {
        this.categoryCallBack = categoryCallBack;
    }
}
