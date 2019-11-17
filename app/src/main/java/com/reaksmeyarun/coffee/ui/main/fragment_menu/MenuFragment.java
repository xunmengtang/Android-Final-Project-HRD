package com.reaksmeyarun.coffee.ui.main.fragment_menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Category;
import com.reaksmeyarun.coffee.model.Global;
import com.reaksmeyarun.coffee.model.Item;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.adapter.CategoryAdapter;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.adapter.ItemAdapter;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.category.category.CategoryActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.feature.item.item.ItemActivity;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp.ItemMVP;
import com.reaksmeyarun.coffee.ui.main.fragment_menu.mvp.ItemPresenter;

import java.util.ArrayList;
import java.util.List;


public class MenuFragment extends Fragment implements ItemMVP.View {

    View view;
    private CategoryAdapter categoryAdapter;
    private ItemAdapter itemAdapter;
    private List<Category> categoryList = new ArrayList<>();
    private List<Item> itemList = new ArrayList<>();

    private ImageView btnEdit;
    private RecyclerView rvCategory, rvItem;

    private ItemMVP.Presenter presenter;

    private static MenuFragment menuFragment;

    public static MenuFragment getInstance(){
        if(menuFragment ==null)
            return new MenuFragment();
        return menuFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.item_fragment,container,false);

        initUI();
        setUpItem(itemList);
        setUpCategory(categoryList,itemList);

        presenter = new ItemPresenter(this);
        presenter.onGetCategory();
        presenter.onGetItem();

        if(!Global.onCheck()){
            btnEdit.setVisibility(View.GONE);
        }

        return view;
    }

    private void initUI() {

        rvCategory = view.findViewById(R.id.rvItem);
        rvItem = view.findViewById(R.id.rvItems);
        btnEdit = view.findViewById(R.id.btnEditPopUp);

        btnEdit.setOnClickListener(v->{
            PopupMenu menu=new PopupMenu(getContext(),v);
            menu.inflate(R.menu.goto_menu);
            menu.setOnMenuItemClickListener(b->{
                switch (b.getItemId()){
                    case R.id.btnEditCategory:
                        startActivity(new Intent(getContext(), CategoryActivity.class));
                        getActivity().finish();
                        return true;
                    case R.id.btnEditItem:
                        startActivity(new Intent(getContext(), ItemActivity.class));
                        getActivity().finish();
                        return true;
                    default : return false;
                }
            });
            menu.show();
        });
    }

    @Override
    public void onShowLoading() {
    }

    @Override
    public void onHideLoading() {
    }

    @Override
    public void onItemSuccess(List<Item> itemList) {
            //Item
        itemAdapter.addMoreItem(itemList);
        this.itemList = itemList;
    }

    @Override
    public void onCategoryError(String msg) {
        Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemError(String msg) {
        Toast.makeText(getContext(), ""+msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCategorySuccess(List<Category> categoryList) {
        categoryAdapter.addMoreCategory(categoryList,this.itemList);
        this.categoryList = categoryList;

    }

    private void setUpItem(List<Item> itemList){
        itemAdapter = new ItemAdapter(itemList,getContext());
        rvItem.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvItem.setAdapter(itemAdapter);
    }

    private void setUpCategory(List<Category> categoryList, List<Item> itemList){
        categoryAdapter = new CategoryAdapter(categoryList, itemList, getContext());
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvCategory.setAdapter(categoryAdapter);
    }
}
