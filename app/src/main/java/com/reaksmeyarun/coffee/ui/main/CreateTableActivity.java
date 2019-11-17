package com.reaksmeyarun.coffee.ui.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.reaksmeyarun.coffee.R;
import com.reaksmeyarun.coffee.model.Table;

import java.util.HashMap;
import java.util.Map;

public class CreateTableActivity extends AppCompatActivity {

    EditText etName, etStatus;
    Button btnCreateTb;

    private final String TABLENODE = "table";

    private FirebaseDatabase firebaseDatabase =  FirebaseDatabase.getInstance();
    private DatabaseReference tableReference = firebaseDatabase.getReference(TABLENODE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);

        etName = findViewById(R.id.etTbName);
        etStatus = findViewById(R.id.etTbStatus);
        btnCreateTb = findViewById(R.id.btnCreateTb);
        btnCreateTb.setOnClickListener(v->{
                createTable(tableReference, new Table(etName.getText().toString(),etStatus.getText().toString()));
        });
    }
    private void createTable(DatabaseReference databaseReference, Table table){
            Map<String, Object> updateValue = new HashMap<>();
            updateValue.put(table.getTableID(),table.toMap());
            databaseReference.updateChildren(updateValue);
    }
}
