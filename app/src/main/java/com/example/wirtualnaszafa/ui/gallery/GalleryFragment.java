package com.example.wirtualnaszafa.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.db.ClientDB;
import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class GalleryFragment extends Fragment {
    private LinearLayout linearLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery_scroll, container, false);

        linearLayout = rootView.findViewById(R.id.linear1);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        class LoadFromDB extends AsyncTask<Void, Void, List<WardrobeDB>> {
            @Override
            protected List<WardrobeDB> doInBackground(Void... voids) {
                //retrieve data from DB
                List<WardrobeDB> list_elem = ClientDB
                        .getInstance(getContext())
                        .getAppDatabase()
                        .wardrobeDAO()
                        .getAllDesc();  //first new elements

                return list_elem;
            }

            @Override
            protected void onPostExecute(List<WardrobeDB> db) {
                super.onPostExecute(db);
                if (db.size() == 0){
                    View view = layoutInflater.inflate(R.layout.fragment_gallery_empty, linearLayout, false);
                    linearLayout.addView(view);
                }
                else{
                    for (int i = 0; i < db.size(); i++) {
                        WardrobeDB obj = db.get(i); //object, used to remove a row too
//                        System.out.println("ID: " + obj.getId());
//                        System.out.println("PATH: " + obj.getPath());
//                        System.out.println("TAG: " + obj.getTag());
//                        System.out.println("COLOR: " + obj.getColor());

                        View view = layoutInflater.inflate(R.layout.fragment_gallery_singleitem, linearLayout, false);

                        ImageView imageView = view.findViewById(R.id.display_saved_img);
                        TextView tv_tag = view.findViewById(R.id.display_tag);
                        TextView tv_color = view.findViewById(R.id.display_color);

                        loadImageFromInternalStorage(obj.getPath(), imageView);

                        String tag = getResources().getString(R.string.tag, obj.getTag());
                        tv_tag.setText(tag);
                        String color = getResources().getString(R.string.color, obj.getColor());
                        tv_color.setText(color);

                        Button button_del = view.findViewById(R.id.button_delete);

                        button_del.setOnClickListener(new View.OnClickListener() {
                            //delete entry in internal memory & DB
                            //change text on the button and display placeholder
                            public void onClick(View v) {
                                deleteEntryInDB(obj);
                                deleteImageFromInternalStorage(obj.getPath());
                                imageView.setImageResource(R.drawable.ic_hanger);
                                tv_tag.setText("");
                                tv_color.setText("");
                                button_del.setText(R.string.deleted_from_db);
                            }
                        });
                        linearLayout.addView(view);
                    }
                }
            }
        }
        LoadFromDB load = new LoadFromDB();
        load.execute();

        return rootView;
    }

    private void loadImageFromInternalStorage(String path, ImageView img){
        try{
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    private boolean deleteImageFromInternalStorage(String path){
        File f = new File(path, "profile.jpg");
        return f.delete();
    }

    private void deleteEntryInDB(final WardrobeDB task) {
        class DeleteFromDB extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                ClientDB.getInstance(getContext()).getAppDatabase()
                        .wardrobeDAO()
                        .delete(task);
                return null;
            }
        }
        DeleteFromDB delete = new DeleteFromDB();
        delete.execute();
    }
}