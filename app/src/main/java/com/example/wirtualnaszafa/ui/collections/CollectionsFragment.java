package com.example.wirtualnaszafa.ui.collections;

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
import com.example.wirtualnaszafa.db.collections.SavedCollectionsDAO;
import com.example.wirtualnaszafa.db.collections.SavedCollectionsDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

    public class CollectionsFragment extends Fragment {
    Button button_next, button_previous, button_delete;
    ImageView iv_top, iv_bot, iv_accesories, iv_shoes;

    List<SavedCollectionsDB> saved_items;
    int current = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collections, container, false);

        button_previous = rootView.findViewById(R.id.button_previous);
        button_next = rootView.findViewById(R.id.button_next);
        button_delete = rootView.findViewById(R.id.button_delete_collection);

        iv_top = rootView.findViewById(R.id.imageView_top);
        iv_bot = rootView.findViewById(R.id.imageView_bot);
        iv_accesories = rootView.findViewById(R.id.imageView_accessory);
        iv_shoes = rootView.findViewById(R.id.imageView_shoes);

        class LoadFromDB extends AsyncTask<Void, Void, List<SavedCollectionsDB>> {
            @Override
            protected List<SavedCollectionsDB> doInBackground(Void... voids) {
                //retrieve data from DB
                SavedCollectionsDAO dao = ClientDB.getInstance(getContext())
                                          .getAppDatabase()
                                          .savedCollectionsDAO();

                saved_items = dao.getAllDesc();
                System.out.println("NEW_ELEM2: " + saved_items);
                return saved_items;
            }

            @Override
            protected void onPostExecute(List<SavedCollectionsDB> db) {
                super.onPostExecute(db);

                if(db.size() == 0){
                    Toast.makeText(rootView.getContext(), "Nie zapisano jeszcze żadnego zestawu", Toast.LENGTH_LONG).show();
                }
                else {
                    loadImageFromStorage(saved_items.get(current).getPath_top(), iv_top);
                    loadImageFromStorage(saved_items.get(current).getPath_bot(), iv_bot);
                    loadImageFromStorage(saved_items.get(current).getPath_accesories(), iv_accesories);
                    loadImageFromStorage(saved_items.get(current).getPath_shoes(), iv_shoes);

                    button_previous.setOnClickListener(new View.OnClickListener() {
                        //move to left
                        public void onClick(View v) {
                            if(current == 0){
                                Toast.makeText(getContext(), "To pierwszy zapisany zestaw", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                current -= 1;
                                loadImageFromStorage(saved_items.get(current).getPath_top(), iv_top);
                                loadImageFromStorage(saved_items.get(current).getPath_bot(), iv_bot);
                                loadImageFromStorage(saved_items.get(current).getPath_accesories(), iv_accesories);
                                loadImageFromStorage(saved_items.get(current).getPath_shoes(), iv_shoes);
                            }
                        }
                    });

                    button_next.setOnClickListener(new View.OnClickListener() {
                        //move to left
                        public void onClick(View v) {
                            if(current == saved_items.size()-1){
                                Toast.makeText(getContext(), "To ostatni zapisany zestaw", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                current += 1;
                                loadImageFromStorage(saved_items.get(current).getPath_top(), iv_top);
                                loadImageFromStorage(saved_items.get(current).getPath_bot(), iv_bot);
                                loadImageFromStorage(saved_items.get(current).getPath_accesories(), iv_accesories);
                                loadImageFromStorage(saved_items.get(current).getPath_shoes(), iv_shoes);
                            }
                        }
                    });

                    button_delete.setOnClickListener(new View.OnClickListener() {
                        //delete entry from DB
                        public void onClick(View v) {
                            if(saved_items.size() > 1) {
                                deleteEntryInDB(saved_items.get(current));
                                saved_items.remove(current);

                                current = 0; //need to return to first item, because this was a bad idea
                                loadImageFromStorage(saved_items.get(current).getPath_top(), iv_top);
                                loadImageFromStorage(saved_items.get(current).getPath_bot(), iv_bot);
                                loadImageFromStorage(saved_items.get(current).getPath_accesories(), iv_accesories);
                                loadImageFromStorage(saved_items.get(current).getPath_shoes(), iv_shoes);
                            }
                            else if(saved_items.size() == 1){
                                iv_top.setImageResource(R.drawable.ic_hanger);
                                iv_bot.setImageResource(R.drawable.ic_hanger);
                                iv_accesories.setImageResource(R.drawable.ic_hanger);
                                iv_shoes.setImageResource(R.drawable.ic_hanger);

                                deleteEntryInDB(saved_items.get(current));
                                saved_items.remove(current);
                                button_delete.setText(R.string.deleted_from_db);
                                current = 0;
                            }
                            else{
                                current = 0;
                                System.out.println("list is empty");
                            }

                        }
                    });
                }
            }
        }
        LoadFromDB load = new LoadFromDB();
        load.execute();

        return rootView;
    }

    private void deleteEntryInDB(final SavedCollectionsDB task) {
        class DeleteFromDB extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                ClientDB.getInstance(getContext()).getAppDatabase()
                        .savedCollectionsDAO()
                        .delete(task);
                return null;
            }
        }
        DeleteFromDB delete = new DeleteFromDB();
        delete.execute();
    }

    private void loadImageFromStorage(String path, ImageView img) {
        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}