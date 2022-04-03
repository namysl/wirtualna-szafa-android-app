package com.example.wirtualnaszafa.ui.add_element;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wirtualnaszafa.R;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddElementFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddElementFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int GALLERY_REQUEST_CODE = 100;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button button_gallery, button_camera, button_save;
    ImageView selectedImageView;

    public AddElementFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment collections.
     */
    // TODO: Rename and change types and number of parameters
    public static AddElementFragment newInstance(String param1, String param2) {
        AddElementFragment fragment = new AddElementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_element, container, false);

        button_gallery = (Button) rootView.findViewById(R.id.button_add_from_gallery);
        button_camera = (Button) rootView.findViewById(R.id.button_add_from_camera);
        button_save = (Button) rootView.findViewById(R.id.button_save_photo);

        button_gallery.setOnClickListener(this);
        button_camera.setOnClickListener(this);
        button_save.setOnClickListener(this);

        selectedImageView = (ImageView) rootView.findViewById(R.id.imageView_add_photo);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_from_gallery:
                Toast.makeText(v.getContext(), "Kliknięto GALERIA", Toast.LENGTH_SHORT).show();
                open_gallery();
                break;
            case R.id.button_add_from_camera:
                Toast.makeText(v.getContext(), "Kliknięto KAMERA", Toast.LENGTH_SHORT).show();
                //kamera ma popieprzone permissions, nie ogarniam tego
                break;
            case R.id.button_save_photo:
                Toast.makeText(v.getContext(), "Kliknięto ZAPISZ", Toast.LENGTH_SHORT).show();
                //zapis do bazy zdjęć
                break;

            //dodawanie tagów zrobić później
        }
    }

    public void open_gallery(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        try {
            i.putExtra("return-data", true);
            startActivityForResult(Intent.createChooser(i, "Select Picture"), 100);
            System.out.println("tutaj");
        }catch (ActivityNotFoundException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // BitmapFactory: Unable to decode stream: java.io.FileNotFoundException:
        // /storage/emulated/0/Download/YU986-00P-040-1_12.jpg: open failed: EACCES (Permission denied)
        if (data != null) {
            Uri selectedImage = data.getData(); //imageURI

            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            System.out.println("PATH!! " + picturePath);
            String dir = picturePath;
            System.out.println("DIR " + dir);
            File mypath = new File(dir, "profile.jpg");

            selectedImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));

            cursor.close();
        } else {
            Toast.makeText(getActivity(), "Try Again!!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

//    public void open_gallery(){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
//    }
}