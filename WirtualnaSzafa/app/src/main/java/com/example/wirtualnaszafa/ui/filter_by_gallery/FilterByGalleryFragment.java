package com.example.wirtualnaszafa.ui.filter_by_gallery;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
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

import androidx.fragment.app.Fragment;

import com.example.wirtualnaszafa.R;
import com.example.wirtualnaszafa.db.ClientDB;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDAO;
import com.example.wirtualnaszafa.db.all_elements.WardrobeDB;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FilterByGalleryFragment extends Fragment implements View.OnClickListener {
    Button button_filter, button_left, button_right;
    EditText tag_editT, color_editT;
    Spinner spinner_tag, spinner_color;
    ImageView iV_fromDB;
    List<WardrobeDB> found_clothes = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_filter_by_gallery, container, false);

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

        iV_fromDB = rootView.findViewById(R.id.imageView_fromDB);

        button_filter = rootView.findViewById(R.id.button_filter);
        button_filter.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_filter) {
            if (isEmpty(tag_editT) && isEmpty(color_editT)) {
                Toast.makeText(v.getContext(), "Należy wybrać przynajmniej jeden filtr", Toast.LENGTH_SHORT).show();
            } else if (!isEmpty(tag_editT) && isEmpty(color_editT)) {
                //tag filled, empty color

                class LoadFromDB extends AsyncTask<Void, Void, List<WardrobeDB>> {
                    @Override
                    protected List<WardrobeDB> doInBackground(Void... voids) {
                        //retrieve data from DB
                        WardrobeDAO dao = ClientDB.getInstance(getContext()).getAppDatabase().wardrobeDAO();
                        found_clothes = dao.getClothesByTag(tag_editT.getText().toString());

                        return found_clothes;
                    }

                    @Override
                    protected void onPostExecute(List<WardrobeDB> db) {
                        super.onPostExecute(db);

                        if (db.size() == 0) {
                            Toast.makeText(getContext(), "Brak wyników", Toast.LENGTH_SHORT).show();
                        } else {
                            loadImageFromInternalStorage(found_clothes.get(0).getPath(), iV_fromDB);
                        }
                    }
                }
                LoadFromDB load = new LoadFromDB();
                load.execute();
            } else if (isEmpty(tag_editT) && !isEmpty(color_editT)) {
                //tag empty, color filled

                class LoadFromDB extends AsyncTask<Void, Void, List<WardrobeDB>> {
                    @Override
                    protected List<WardrobeDB> doInBackground(Void... voids) {
                        //retrieve data from DB
                        WardrobeDAO dao = ClientDB.getInstance(getContext()).getAppDatabase().wardrobeDAO();
                        found_clothes = dao.getClothesByColor(color_editT.getText().toString());

                        return found_clothes;
                    }

                    @Override
                    protected void onPostExecute(List<WardrobeDB> db) {
                        super.onPostExecute(db);

                        if (db.size() == 0) {
                            Toast.makeText(getContext(), "Brak wyników", Toast.LENGTH_SHORT).show();
                        } else {
                            loadImageFromInternalStorage(found_clothes.get(0).getPath(), iV_fromDB);
                        }
                    }
                }
                LoadFromDB load = new LoadFromDB();
                load.execute();
            } else {
                //both filled
                class LoadFromDB extends AsyncTask<Void, Void, List<WardrobeDB>> {
                    @Override
                    protected List<WardrobeDB> doInBackground(Void... voids) {
                        //retrieve data from DB
                        WardrobeDAO dao = ClientDB.getInstance(getContext()).getAppDatabase().wardrobeDAO();
                        found_clothes = dao.getClothesByTagAndColor(tag_editT.getText().toString(),
                                color_editT.getText().toString());

                        return found_clothes;
                    }

                    @Override
                    protected void onPostExecute(List<WardrobeDB> db) {
                        super.onPostExecute(db);

                        if (db.size() == 0) {
                            Toast.makeText(getContext(), "Brak wyników", Toast.LENGTH_SHORT).show();
                        } else {
                            loadImageFromInternalStorage(found_clothes.get(0).getPath(), iV_fromDB);
                        }
                    }
                }
                LoadFromDB load = new LoadFromDB();
                load.execute();
            }
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    private void loadImageFromInternalStorage(String path, ImageView img) {
        try {
            File f = new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            img.setImageBitmap(b);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}