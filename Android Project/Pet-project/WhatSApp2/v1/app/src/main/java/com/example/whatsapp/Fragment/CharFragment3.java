package com.example.whatsapp.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.whatsapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class CharFragment3 extends Fragment {

    private View groupFragment;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfGroups = new ArrayList<>();
    private DatabaseReference refDB;
    private String currentId;
    private FirebaseAuth firebaseAuth;


    // ТОЛЬКО С ЭТИМ ФРАГМЕНТОМ РАБОТАЛ, остальные ждут своей очереди)

    public CharFragment3() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        groupFragment = inflater.inflate(R.layout.fragment_char3, container, false);

        // инициализация firebaseAuth и взятие через него currentId (уникальный индификатор)
        firebaseAuth = FirebaseAuth.getInstance();
        currentId = firebaseAuth.getCurrentUser().getUid();

        // указание куда отправлять и откуда забирать информацию о группах в firebase
        refDB = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentId);

        // обычный адаптер с listView
        listView = groupFragment.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, listOfGroups);
        listView.setAdapter(arrayAdapter);

        // проверка какие группы есть у данного пользователя
        AutificationGroups();

        return groupFragment;
    }

    // вывод групп в фрагмент
    private void AutificationGroups() {

        refDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Set<String> set = new HashSet<>();

                Iterator iterator = snapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

                listOfGroups.clear();
                listOfGroups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}