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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class RandomizeFragment extends Fragment implements View.OnClickListener{
    //TODO RANDOMIZE
    ImageView iv_top, iv_bot, iv_accesory, iv_shoes;
    Button button_new_random, button_save_random;
    ArrayList<WardrobeDB> clothes_top = new ArrayList<>();
    ArrayList<WardrobeDB> clothes_bot = new ArrayList<>();
    ArrayList<WardrobeDB> clothes_accesories = new ArrayList<>();
    ArrayList<WardrobeDB> clothes_shoes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_randomize, container, false);

        iv_top = rootView.findViewById(R.id.imageView_top);
        iv_bot = rootView.findViewById(R.id.imageView_bot);
        iv_accesory = rootView.findViewById(R.id.imageView_accessory);
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

                final String tags[] = {"góra", "dół", "akcesoria", "buty"};
                final ArrayList[] arrays = {clothes_top, clothes_bot, clothes_accesories, clothes_shoes};

                for (int i = 0; i < tags.length; i++) {
                    List<WardrobeDB> lista_tags = dao.getClothesByTag(tags[i]);
                    System.out.println("LISTA TAGS: " + lista_tags);
                    for (int j = 0; j < lista_tags.size(); j++) {
                        arrays[i].add(lista_tags.get(j));
                    }
                }

                List<WardrobeDB> db = dao.getAll();
                return db;
            }

            @Override
            protected void onPostExecute(List<WardrobeDB> db) {
                super.onPostExecute(db);

                if (db.size() == 0) {
                    Toast.makeText(rootView.getContext(), "Za mało dodanych ubrań!", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("SIZE" + clothes_shoes.size());
                    for (int i = 0; i < clothes_shoes.size(); i++) {
                        //WardrobeDB obj = db.get(i); //object, used to remove a row
//                        System.out.println("ID: " + obj.getId());
//                        System.out.println("PATH: " + obj.getPath());
//                        System.out.println("TAG: " + obj.getTag());
//                        System.out.println("COLOR: " + obj.getColor());
                        System.out.println("BUUUUUTY:" + clothes_shoes.get(i).getColor());
                    }
                }
                loadImageFromStorage(clothes_shoes.get(1).getPath(), iv_shoes);
                loadImageFromStorage(clothes_shoes.get(2).getPath(), iv_top);
                loadImageFromStorage(clothes_shoes.get(0).getPath(), iv_bot);
            }
        }
        LoadFromDB load = new LoadFromDB();
        load.execute();

        for (int i = 0; i < clothes_shoes.size(); i++) {
            //WardrobeDB obj = db.get(i); //object, used to remove a row
//                        System.out.println("ID: " + obj.getId());
//                        System.out.println("PATH: " + obj.getPath());
//                        System.out.println("TAG: " + obj.getTag());
//                        System.out.println("COLOR: " + obj.getColor());
            System.out.print("BUUUUUTY:" + clothes_shoes.get(i).getColor());

        }
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
                break;
            case R.id.button_save_random:
                Toast.makeText(v.getContext(), "SAVE", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}