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

import com.example.wirtualnaszafa.ClientDB;
import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.WardrobeDB;

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
                        .getAll();  //tutaj może być też inne query, ale tak jest wygodnie

                return list_elem;
            }

            @Override
            protected void onPostExecute(List<WardrobeDB> tasks) {
                super.onPostExecute(tasks);
                for(int i=0; i<tasks.size(); i++){
                    WardrobeDB obj = tasks.get(i);
                    System.out.println("ID: " + obj.getId()); //do usuwania elementu
                    System.out.println("PATH: " + obj.getPath());
                    System.out.println("TAG: " + obj.getTag());
                    System.out.println("COLOR: " + obj.getColor());

                    View view = layoutInflater.inflate(R.layout.fragment_gallery_singleitem, linearLayout, false);

                    ImageView imageView = view.findViewById(R.id.display_saved_img);
                    TextView tv_tag = view.findViewById(R.id.display_tag);
                    TextView tv_color = view.findViewById(R.id.display_color);

                    loadImageFromStorage(obj.getPath(), imageView);
                    tv_tag.setText(obj.getTag());
                    tv_color.setText(obj.getColor());
                    //dodać numerację?

                    Button button_del = view.findViewById(R.id.button_delete);
                    //TODO action listener -> usuwanie wybranego elementu, teraz już izi!
                    button_del.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            delete_entry(obj);
                            button_del.setText(R.string.deleted_from_db);
                        }
                    });
                    linearLayout.addView(view);
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

    private void delete_entry(final WardrobeDB task) {
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