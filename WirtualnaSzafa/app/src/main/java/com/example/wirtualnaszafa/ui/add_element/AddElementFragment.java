package com.example.wirtualnaszafa.ui.add_element;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddElementFragment extends Fragment implements View.OnClickListener {
    Button button_gallery, button_camera, button_save;
    private ImageView imageView;
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;

    public AddElementFragment() {
        // Required empty public constructor
    }

    /*
    public static AddElementFragment newInstance(String param1, String param2) {
        AddElementFragment fragment = new AddElementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
*/
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

        imageView = (ImageView) rootView.findViewById(R.id.imageView_add_photo);

        return rootView;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        //first check permissions, then decide what to do
        if(checkAndRequestPermissions(getActivity())){
            switch (v.getId()) {
                case R.id.button_add_from_gallery:
                    //choose from external storage
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                    break;
                case R.id.button_add_from_camera:
                    //open camera and snap the photo
                    Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                    break;
                case R.id.button_save_photo:
                    Toast.makeText(v.getContext(), "Kliknięto ZAPISZ", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    public static boolean checkAndRequestPermissions(final Activity context) {
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


    // Handles permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext().getApplicationContext(),
                        "Requires access to camera.", Toast.LENGTH_SHORT)
                        .show();

            } else if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext().getApplicationContext(),
                        "Requires access to your storage.",
                        Toast.LENGTH_SHORT).show();

            } else {
                System.out.println("Uzyskano dostęp");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        imageView.setImageBitmap(selectedImage);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK && data != null) {
//                        Uri selectedImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        if (selectedImage != null) {
//                            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                            if (cursor != null) {
//                                cursor.moveToFirst();
//
//                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                                String picturePath = cursor.getString(columnIndex);
//                                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                                cursor.close();
//                            }
//                        }
                        Uri selectedImage = data.getData();

                        Bitmap bitmap = null;
                        try {
                            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Bitmap resized = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
                        imageView.setImageBitmap(bitmap);

                    }
                    break;
            }
        }
    }


}