package com.example.wirtualnaszafa.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    //we should import photos and tags from app or do it with API, dunno
    private String[] brands = {"tag1", "tag2", "tag3", "tag4",
            "tag5", "tag6", "tag7"};

    private String[] img = {"/data/user/0/com.example.wirtualnaszafa/app_UdHUFAt9ZXD1h5GhmCZn",
        "/data/user/0/com.example.wirtualnaszafa/app_MzUwYePEqbsnJ9ghSrBZ"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //needed elements in the fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery_scroll, container, false);

        linearLayout = rootView.findViewById(R.id.linear1);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        getTasks();

        for(int i = 0; i < img.length; i++) {
            View view = layoutInflater.inflate(R.layout.fragment_gallery_singleitem, linearLayout, false);

            ImageView imageView = view.findViewById(R.id.display_saved_img);
            //imageView.setImageResource(img[i]);
            //loadImageFromStorage("/data/user/0/com.example.wirtualnaszafa/app_imageDir", imageView);
            loadImageFromStorage(img[i], imageView);

            TextView tv = view.findViewById(R.id.display_tag);
            tv.setText(brands[i]);

            linearLayout.addView(view);
        }

        return rootView;
    }

    private void getTasks() {
        //returns obj
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
                    System.out.println("ID: " + obj.getId());
                    System.out.println("PATH: " + obj.getPath());
                    System.out.println("TAG: " + obj.getTag());
                    System.out.println("COLOR: " + obj.getColor());
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


    private void loadImageFromStorage(String path, ImageView img){
        try{
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}