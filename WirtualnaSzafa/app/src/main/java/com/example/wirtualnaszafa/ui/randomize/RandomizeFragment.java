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
import com.example.wirtualnaszafa.db.all_elements.WardrobeDAO;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDB;
import com.example.wirtualnaszafa.db.collections.SavedCollectionsDB;
import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomizeFragment extends Fragment implements View.OnClickListener{
    ImageView iv_top, iv_bot, iv_accesories, iv_shoes;
    Button button_new_random, button_save_random;

    List<WardrobeDB> clothes_top = new ArrayList<>();
    List<WardrobeDB> clothes_bot = new ArrayList<>();
    List<WardrobeDB> clothes_accesories = new ArrayList<>();
    List<WardrobeDB> clothes_shoes = new ArrayList<>();

    final String[] tags = {"góra", "dół", "akcesoria", "buty"};
    final List[] arrays = {clothes_top, clothes_bot, clothes_accesories, clothes_shoes};

    List<List<WardrobeDB>> cartesian_product;
    int cartesian_product_size;

    List <Integer> displayed = new ArrayList<>();
    List <Integer> not_displayed = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

                for(int i=0; i < tags.length; i++){
                    List<WardrobeDB> lista_tags = dao.getClothesByTag(tags[i]);
                    for (int j=0; j < lista_tags.size(); j++){
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

                if(db.size() == 0){
                    Toast.makeText(rootView.getContext(), "Galeria aplikacji jest pusta," +
                                                               "\ndodaj nowe elementy", Toast.LENGTH_LONG).show();
                }
                else if(size_top == 0 || size_bot == 0 || size_accesories == 0 || size_shoes == 0) {
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
//                    System.out.println(" TOP: " + clothes_top.size() +
//                                       " BOT: " + clothes_bot.size() +
//                                       " ACCESORIES: " + clothes_accesories.size() +
//                                       " SHOES: " + clothes_shoes.size());

                    //Guava library, cartesian product
                    cartesian_product = Lists.cartesianProduct(clothes_top,
                                                               clothes_bot,
                                                               clothes_accesories,
                                                               clothes_shoes);

                    cartesian_product_size = cartesian_product.size();

                    //to always display random collection
                    Random rand = new Random();
                    int rnd = rand.nextInt(cartesian_product_size);

                    loadImageFromStorage(cartesian_product.get(rnd).get(0).getPath(), iv_top);
                    loadImageFromStorage(cartesian_product.get(rnd).get(1).getPath(), iv_bot);
                    loadImageFromStorage(cartesian_product.get(rnd).get(2).getPath(), iv_accesories);
                    loadImageFromStorage(cartesian_product.get(rnd).get(3).getPath(), iv_shoes);

                    iv_top.setTag(cartesian_product.get(rnd).get(0).getPath());
                    iv_bot.setTag(cartesian_product.get(rnd).get(1).getPath());
                    iv_accesories.setTag(cartesian_product.get(rnd).get(2).getPath());
                    iv_shoes.setTag(cartesian_product.get(rnd).get(3).getPath());

                    System.out.println("FIRST:" + iv_top.getTag());
                    displayed.add(rnd);
                    populate_and_shuffle_list(not_displayed, rnd);
                }
            }
        }
        LoadFromDB load = new LoadFromDB();
        load.execute();

        return rootView;
    }

    private void populate_and_shuffle_list(List <Integer> list_to_populate, int picked_random){
        for(int i=0; i<cartesian_product_size; i++){
            list_to_populate.add(i);
        }
        list_to_populate.remove(picked_random);
        Collections.shuffle(list_to_populate);
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
                if(not_displayed.size() == 0){
                    //empty, all combinations displayed, start from the beginning
                    Toast.makeText(v.getContext(), "Wyświetlono wszystkie możliwe kombinacje, zestawy pokazywane są od początku", Toast.LENGTH_SHORT).show();

                    Random rand = new Random();
                    int rnd = rand.nextInt(cartesian_product_size);

                    displayed.clear();
                    populate_and_shuffle_list(not_displayed, rnd);

                    loadImageFromStorage(cartesian_product.get(rnd).get(0).getPath(), iv_top);
                    loadImageFromStorage(cartesian_product.get(rnd).get(1).getPath(), iv_bot);
                    loadImageFromStorage(cartesian_product.get(rnd).get(2).getPath(), iv_accesories);
                    loadImageFromStorage(cartesian_product.get(rnd).get(3).getPath(), iv_shoes);

                    iv_top.setTag(cartesian_product.get(rnd).get(0).getPath());
                    iv_bot.setTag(cartesian_product.get(rnd).get(1).getPath());
                    iv_accesories.setTag(cartesian_product.get(rnd).get(2).getPath());
                    iv_shoes.setTag(cartesian_product.get(rnd).get(3).getPath());

                    System.out.println("IF:" + iv_top.getTag());

                    displayed.add(rnd);
                }
                else {
                    //still enough to display
                    loadImageFromStorage(cartesian_product.get(not_displayed.get(0)).get(0).getPath(), iv_top);
                    loadImageFromStorage(cartesian_product.get(not_displayed.get(0)).get(1).getPath(), iv_bot);
                    loadImageFromStorage(cartesian_product.get(not_displayed.get(0)).get(2).getPath(), iv_accesories);
                    loadImageFromStorage(cartesian_product.get(not_displayed.get(0)).get(3).getPath(), iv_shoes);

                    iv_top.setTag(cartesian_product.get(0).get(0).getPath());
                    iv_bot.setTag(cartesian_product.get(0).get(1).getPath());
                    iv_accesories.setTag(cartesian_product.get(0).get(2).getPath());
                    iv_shoes.setTag(cartesian_product.get(0).get(3).getPath());

                    System.out.println("ELSE:" + iv_top.getTag());

                    displayed.add(not_displayed.get(0));
                    not_displayed.remove(0);
                }
                break;

            case R.id.button_save_random:
                class SaveToDB extends AsyncTask<Void, Void, Void> {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        //adding new element
                        SavedCollectionsDB new_elem = new SavedCollectionsDB();
                        new_elem.setPath_top(iv_top.getTag().toString());
                        new_elem.setPath_bot(iv_bot.getTag().toString());
                        new_elem.setPath_accesories(iv_accesories.getTag().toString());
                        new_elem.setPath_shoes(iv_shoes.getTag().toString());

                        //push to database
                        ClientDB.getInstance(getContext())
                                .getAppDatabase()
                                .savedCollectionsDAO()
                                .insert(new_elem);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid){
                        super.onPostExecute(aVoid);
                        Toast.makeText(v.getContext(), "Zestaw został zapisany", Toast.LENGTH_SHORT).show();
                    }
                }
                SaveToDB save = new SaveToDB();
                save.execute();
                break;
        }
    }
}