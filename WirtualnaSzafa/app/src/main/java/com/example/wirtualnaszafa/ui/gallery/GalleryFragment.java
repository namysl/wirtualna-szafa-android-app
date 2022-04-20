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

import com.example.wirtualnaszafa.AppDB;
import com.example.wirtualnaszafa.ClientDB;
import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.WardrobeDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class GalleryFragment extends Fragment {
    private LinearLayout linearLayout;
    private AppDB appDB;

    /*
    String[] path = {};
    String[] tag = {};
    String[] color = {};
    */

    //PLACEHOLDERS: delete later TODO
    private String[] brands = {"tag1", "tag2", "tag3", "tag4",
            "tag5", "tag6", "tag7"};
    private String[] img = {"/data/user/0/com.example.wirtualnaszafa/app_UdHUFAt9ZXD1h5GhmCZn",
        "/data/user/0/com.example.wirtualnaszafa/app_MzUwYePEqbsnJ9ghSrBZ"};
    //PLACEHOLDERS: delete later

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //needed elements in the fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery_scroll, container, false);

        linearLayout = rootView.findViewById(R.id.linear1);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        getTasks();
//        List<String> path = new ArrayList<String>();
//        List<String> tag = new ArrayList<String>();
//        List<String> color = new ArrayList<String>();
//        System.out.println(path.size());
//        for(int i=0; i<path.size(); i++) {
//            System.out.println("2137");
//            System.out.println(path.get(i));
//            System.out.println(tag.get(i));
//            System.out.println(color.get(i));
//        }

        for(int i = 0; i < img.length; i++) {
            View view = layoutInflater.inflate(R.layout.fragment_gallery_singleitem, linearLayout, false);

            ImageView imageView = view.findViewById(R.id.display_saved_img);
            TextView tv_tag = view.findViewById(R.id.display_tag);
            TextView tv_color = view.findViewById(R.id.display_color);

            loadImageFromStorage(img[i], imageView);
            tv_tag.setText(brands[i]);
            tv_color.setText(brands[i] + "kolor fqwvcd");
//            loadImageFromStorage(path.get(i), imageView);
//            tv_tag.setText(tag.get(i));
//            tv_color.setText(color.get(i));

            //TODO action listener
            Button button_del = view.findViewById(R.id.button_delete);

            linearLayout.addView(view);
        }
        return rootView;
    }

    private void getTasks() {
        //retrieve data from DB
        class GetTasks extends AsyncTask<Void, Void, List<WardrobeDB>> {
            @Override
            protected List<WardrobeDB> doInBackground(Void... voids) {
                List<WardrobeDB> taskList = ClientDB
                        .getInstance(getContext())
                        .getAppDatabase()
                        .wardrobeDAO()
                        .getAll();

                return taskList;
            }

            @Override
            protected void onPostExecute(List<WardrobeDB> tasks) {
                super.onPostExecute(tasks);
                for(int i=0; i<tasks.size(); i++){
                    WardrobeDB obj = tasks.get(i);
                    System.out.println("ID: " + obj.getId());  //przyda sie do usuwania
                    System.out.println("PATH: " + obj.getPath());
                    System.out.println("TAG: " + obj.getTag());
                    System.out.println("COLOR: " + obj.getColor());

//                    path.add(obj.getPath());
//                    tag.add(obj.getTag());
//                    color.add(obj.getColor());
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
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
}