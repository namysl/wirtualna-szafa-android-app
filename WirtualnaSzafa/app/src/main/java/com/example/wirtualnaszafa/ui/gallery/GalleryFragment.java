package com.example.wirtualnaszafa.ui.gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GalleryFragment extends Fragment {
    private LinearLayout linearLayout;

    //we should import photos and tags from app or do it with API, dunno
    private String[] brands = {"tag1", "tag2", "tag3", "tag4",
            "tag5", "tag6", "tag7"};
    private int[] images = {
            R.color.orange,
            R.color.white,
            R.color.black,
            R.color.grayish,
            R.color.flesh,
            R.color.brown,
            R.color.lightorange};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //needed elements in the fragment
        View rootView = inflater.inflate(R.layout.fragment_gallery_scroll, container, false);

        linearLayout = rootView.findViewById(R.id.linear1);
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());


        for(int i = 0; i < brands.length; i++) {
            View view = layoutInflater.inflate(R.layout.fragment_gallery_singleitem, linearLayout, false);

            ImageView imageView = view.findViewById(R.id.display_saved_img);
            //imageView.setImageResource(images[i]);
            loadImageFromStorage("/data/user/0/com.example.wirtualnaszafa/app_imageDir", imageView);

            TextView tv = view.findViewById(R.id.display_tag);
            tv.setText(brands[i]);

            linearLayout.addView(view);
        }

        return rootView;
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