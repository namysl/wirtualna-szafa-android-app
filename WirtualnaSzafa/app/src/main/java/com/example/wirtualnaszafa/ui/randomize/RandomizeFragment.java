package com.example.wirtualnaszafa.ui.randomize;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.db.ClientDB;
import com.example.wirtualnaszafa.db.WardrobeDAO;
import com.example.wirtualnaszafa.db.WardrobeDB;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RandomizeFragment extends Fragment implements View.OnClickListener{
    //TODO RANDOMIZE
    ImageView iv_top, iv_bot, iv_accesories, iv_shoes;
    Button button_new_random, button_save_random;

    List<WardrobeDB> clothes_top = new ArrayList<>();
    List<WardrobeDB> clothes_bot = new ArrayList<>();
    List<WardrobeDB> clothes_accesories = new ArrayList<>();
    List<WardrobeDB> clothes_shoes = new ArrayList<>();

    List<List<WardrobeDB>> cartesian_product;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_randomize, container, false);

        iv_top = rootView.findViewById(R.id.imageView_top);
        iv_bot = rootView.findViewById(R.id.imageView_bot);
        iv_accesories = rootView.findViewById(R.id.imageView_accessory);
        iv_shoes = rootView.findViewById(R.id.imageView_shoes);

        button_new_random = rootView.findViewById(R.id.button_new_random);
        button_save_random = rootView.findViewById(R.id.button_save_random);

        button_new_random.setOnClickListener(this);
        button_save_random.setOnClickListener(this);

        class LoadFromDB extends AsyncTask<Void, Void, List<WardrobeDB>> {
            @Override
            protected List<WardrobeDB> doInBackground(Void... voids) {
                //retrieve data from DB by tag and save it into the array

                WardrobeDAO dao = ClientDB.getInstance(getContext()).getAppDatabase().wardrobeDAO();

                final String[] tags = {"góra", "dół", "akcesoria", "buty"};
                final List[] arrays = {clothes_top, clothes_bot, clothes_accesories, clothes_shoes};

                for (int i = 0; i < tags.length; i++) {
                    List<WardrobeDB> lista_tags = dao.getClothesByTag(tags[i]);
                    System.out.println("LISTA DAO: " + lista_tags);
                    for (int j = 0; j < lista_tags.size(); j++) {
                        arrays[i].add(lista_tags.get(j));
                    }
                }

                return dao.getAll();
            }

            @Override
            protected void onPostExecute(List<WardrobeDB> db) {
                super.onPostExecute(db);
                int size_top = clothes_top.size();
                int size_bot = clothes_bot.size();
                int size_accesories = clothes_accesories.size();
                int size_shoes = clothes_shoes.size();

                if (db.size() == 0) {
                    Toast.makeText(rootView.getContext(), "Galeria aplikacji jest pusta," +
                            "                                   \ndodaj nowe elementy", Toast.LENGTH_LONG).show();
                }
                else if (size_top == 0 || size_bot == 0 || size_accesories == 0 || size_shoes == 0) {
                    String empty_tag;

                    if(size_top == 0){
                        empty_tag = "GÓRA";
                    }
                    else if(size_bot == 0){
                        empty_tag = "DÓŁ";
                    }
                    else if (size_accesories == 0){
                        empty_tag = "AKCESORIA";
                    }
                    else {
                        empty_tag = "BUTY";
                    }

                    String empty_tag_to_toast = "Aby losować zestawy, dodaj chociaż jeden element z tagiem "
                                                + empty_tag;
                    Toast.makeText(rootView.getContext(), empty_tag_to_toast, Toast.LENGTH_LONG).show();
                }
                else {
                    System.out.println("top: " + clothes_top.size());
                    System.out.println("bot: " + clothes_bot.size());
                    System.out.println("accessory: " + clothes_accesories.size());
                    System.out.println("shoes: " + clothes_shoes.size());

                    //Guava library
                    cartesian_product = Lists.cartesianProduct(clothes_top,
                                                               clothes_bot,
                                                               clothes_accesories,
                                                               clothes_shoes);
                    System.out.println(cartesian_product);
                    System.out.println("SIZE OF CARTESIAN: " + cartesian_product.size());

                    for (int i = 0; i < clothes_shoes.size(); i++) {
                        //WardrobeDB obj = db.get(i); //object, used to remove a row
//                        System.out.println("ID: " + obj.getId());
//                        System.out.println("PATH: " + obj.getPath());
//                        System.out.println("TAG: " + obj.getTag());
//                        System.out.println("COLOR: " + obj.getColor());
                        System.out.println("BUUUUUTY:" + clothes_shoes.get(i).getColor());
                    }

                    loadImageFromStorage(clothes_top.get(0).getPath(), iv_top);
                    loadImageFromStorage(clothes_bot.get(0).getPath(), iv_bot);
                    loadImageFromStorage(clothes_accesories.get(0).getPath(), iv_accesories);
                    loadImageFromStorage(clothes_shoes.get(0).getPath(), iv_shoes);
                }
            }
        }
        LoadFromDB load = new LoadFromDB();
        load.execute();

        return rootView;
    }

    private void loadImageFromStorage(String path, ImageView img){
        try{
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_new_random:
                Toast.makeText(v.getContext(), "NEW", Toast.LENGTH_SHORT).show();

                for (int i = 0; i < clothes_top.size(); i++) {
                    System.out.println("TOP:" + clothes_top.get(i).getColor());
                }

                break;
            case R.id.button_save_random:
                Toast.makeText(v.getContext(), "SAVE", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}