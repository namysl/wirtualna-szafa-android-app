package com.example.wirtualnaszafa.ui.add_element;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.wirtualnaszafa.R;

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
    private static final int CAMERA_REQUEST_CODE = 200;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button button_gallery;
    Button button_camera;
    Button button_save;

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

        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add_from_gallery:
                System.out.println("kurwa mać galeria!!!");

                //tutaj wyciaganie zdjecia z galerii

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);

                break;
            case R.id.button_add_from_camera:
                System.out.println("kamera");
                //tutaj logic dla robienia zdjecia aparatem
                break;
            case R.id.button_save_photo:
                System.out.println("zapisuj to");
                //zapisywanie do bazy zdjec
                break;
            //dodawanie tagów zrobić później, bo ocipieję
            //imo olać wyświetlanie miniaturki albo zrobić później
        }
    }

}