package com.example.wirtualnaszafa.ui.add_element;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddElementFragment extends Fragment implements View.OnClickListener{
    Button button_gallery, button_camera, button_save;
    EditText tag_editT, color_editT;
    private ImageView imageView;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //needed elements in the fragment
        View rootView = inflater.inflate(R.layout.fragment_add_element, container, false);

        button_gallery = rootView.findViewById(R.id.button_add_from_gallery);
        button_camera = rootView.findViewById(R.id.button_add_from_camera);
        button_save = rootView.findViewById(R.id.button_save_photo);

        tag_editT = rootView.findViewById(R.id.editText_tag_photo);
        color_editT = rootView.findViewById(R.id.editText_color_photo);

        button_gallery.setOnClickListener(this);
        button_camera.setOnClickListener(this);
        button_save.setOnClickListener(this);

        imageView = rootView.findViewById(R.id.imageView_add_photo);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v){
        //first check permissions, then decide what to do
        if(checkAndRequestPermissions(getActivity())){
            switch (v.getId()){
                case R.id.button_add_from_gallery:
                    //choose from external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto, 1);
                    break;
                case R.id.button_add_from_camera:
                    //open camera and snap the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                    break;
                case R.id.button_save_photo:
                    //save picked photo to internal memory of app and/or use API

                    System.out.println(tag_editT.getText());
                    System.out.println(color_editT.getText()); //.toString());

                    if(isEmpty(tag_editT) || isEmpty(color_editT)){  // uwzględnić kategorie tagów?
                        Toast.makeText(v.getContext(), "Pola tag i kolor muszą być wypełnione", Toast.LENGTH_SHORT).show();
                    }
                    else{
//                      //tu API heh
                        String picture = saveToInternalStorage(((BitmapDrawable)imageView.getDrawable()).getBitmap());
                        System.out.println("SHOW MY DIR: " + picture);
                        //chyba działa! jeszcze zapisać gdzieś dir do zdjęć
                        Toast.makeText(v.getContext(), "Zapisano", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath = new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public static boolean checkAndRequestPermissions(final Activity context){
        //check permissions
        int WExtstorePermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED){
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()){
            ActivityCompat.requestPermissions(context, listPermissionsNeeded.toArray(new String[0]),
                                              REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        // Handles permission result
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext().getApplicationContext(),
                          "Requires access to camera.", Toast.LENGTH_SHORT).show();
            }
            else if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext().getApplicationContext(),
                          "Requires access to your storage.", Toast.LENGTH_SHORT).show();
            }
            else{
                System.out.println("Permission granted.");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED){
            System.out.println("DATA1: " + data);
            switch (requestCode){
                case 0:
                    //camera
                    if (resultCode == RESULT_OK && data != null){
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    //gallery
                    if (resultCode == RESULT_OK && data != null){
                        Uri selectedImage = data.getData();

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
                                                                       selectedImage);
                        } catch (IOException e){
                            e.printStackTrace();
                        }
                        //display picked photo
                        imageView.setImageBitmap(bitmap);
                    }
                    break;
            }
            System.out.println("DATA2: " + data.getData());
        }
    }
}