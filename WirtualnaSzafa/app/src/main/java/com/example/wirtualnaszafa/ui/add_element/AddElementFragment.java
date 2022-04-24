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
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.db.ClientDB;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDB;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class AddElementFragment extends Fragment implements View.OnClickListener{
    Button button_gallery, button_camera, button_save;
    EditText tag_editT, color_editT;
    Spinner spinner_tag, spinner_color;
    private ImageView imageView;

    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 101;
    public static final int RESULT_CANCELED = 0;
    public static final int RESULT_OK = -1;

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        //needed elements in the fragment
        View rootView = inflater.inflate(R.layout.fragment_add_element, container, false);

        button_gallery = rootView.findViewById(R.id.button_add_from_gallery);
        button_camera = rootView.findViewById(R.id.button_add_from_camera);
        button_save = rootView.findViewById(R.id.button_save_photo);

        tag_editT = rootView.findViewById(R.id.editText_tag_photo);
        color_editT = rootView.findViewById(R.id.editText_color_photo);

        //tags
        spinner_tag = rootView.findViewById(R.id.spinner_tag);
        ArrayAdapter<CharSequence> staticAdapter_tag = ArrayAdapter.createFromResource(
                rootView.getContext(),
                R.array.add_tag, //array of strings
                R.layout.spinner_text); //layout

        staticAdapter_tag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_tag.setAdapter(staticAdapter_tag);
        spinner_tag.setSelection(4, true); //it's a very stupid solution, but it works!

        spinner_tag.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag_editT.setText((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //colors
        spinner_color = rootView.findViewById(R.id.spinner_color);
        ArrayAdapter<CharSequence> staticAdapter_color = ArrayAdapter.createFromResource(
                rootView.getContext(),
                R.array.add_color, //array of strings
                R.layout.spinner_text); //layout

        staticAdapter_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_color.setAdapter(staticAdapter_color);
        spinner_color.setSelection(13, true); //it's a very stupid solution, but it works!

        spinner_color.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                color_editT.setText((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
                    //save picked photo to internal memory of the app
                    if(isEmpty(tag_editT) || isEmpty(color_editT)){
                        Toast.makeText(v.getContext(), "Pola tag i kolor muszą być wypełnione", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        //to be sure that user is not stupid and added a picture :)
                        try {
                            String picture = saveToInternalStorage(((BitmapDrawable) imageView.getDrawable()).getBitmap());

                            class SaveToDB extends AsyncTask<Void, Void, Void>{
                                @Override
                                protected Void doInBackground(Void... voids){
                                    //adding new element
                                    WardrobeDB new_elem = new WardrobeDB();
                                    new_elem.setPath(picture);
                                    new_elem.setTag(tag_editT.getText().toString());
                                    new_elem.setColor(color_editT.getText().toString());

                                    //push to database
                                    ClientDB.getInstance(getContext())
                                            .getAppDatabase()
                                            .wardrobeDAO()
                                            .insert(new_elem);
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid){
                                    super.onPostExecute(aVoid);
                                    //getActivity().finish(); //closes fragment
                                    //startActivity(new Intent(getContext(), MainActivity.class)); //moves to homepage
                                    tag_editT.setText("");
                                    color_editT.setText("");
                                    tag_editT.clearFocus();
                                    color_editT.clearFocus();
                                    spinner_tag.setSelection(4, true);
                                    spinner_color.setSelection(13, true);
                                    imageView.setImageResource(R.drawable.ic_hanger);  //back to placeholder state
                                    Toast.makeText(v.getContext(), "Zapisano", Toast.LENGTH_SHORT).show();
                                }
                            }
                            SaveToDB save = new SaveToDB();
                            save.execute();

                        }catch (Exception e){
                            Toast.makeText(v.getContext(), "Należy dodać zdjęcie", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getContext());

        //filename as a random alphanumeric string
        File directory = cw.getDir(randomString(20), Context.MODE_PRIVATE);
        //create file
        File mypath = new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(mypath);
            //use the compress method on the bitmap object to write image to the outputstream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        }
        catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                assert fos != null;
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    boolean isEmpty(EditText text){
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    String randomString(int len){
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++)
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        return sb.toString();
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
        //handles permission result
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS){
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext().getApplicationContext(),
                          "Wymagany jest dostęp do kamery", Toast.LENGTH_SHORT).show();
            }
            else if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getContext().getApplicationContext(),
                          "Wymagany jest dostęp do plików", Toast.LENGTH_SHORT).show();
            }
            else{
                System.out.println("Permission granted");
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED){
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
        }
    }
}