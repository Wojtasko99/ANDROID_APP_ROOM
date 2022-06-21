package com.example.apka2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int ADD_PHONE_REQ = 1;
    public static final int EDIT_PHONE_REQ = 2;
    private ElementViewModel mElementViewModel;
    private ElementListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAdd = findViewById(R.id.button_add_phone);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEditPhoneActivity.class);
                startActivityForResult(intent, ADD_PHONE_REQ);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new ElementListAdapter(this);
        recyclerView.setAdapter(mAdapter);

        mElementViewModel = ViewModelProviders.of(this).get(ElementViewModel.class);
        mElementViewModel.getmAllElements().observe(this, new Observer<List<Element>>() {
            @Override
            public void onChanged(List<Element> elements) {
                mAdapter.setElementList(elements);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mElementViewModel.delete(mAdapter.getElementAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Phone deleted", Toast.LENGTH_SHORT).show();
            }

        }).attachToRecyclerView(recyclerView);
        mAdapter.setOnItemClickListener(new ElementListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Element element) {
                Intent intent = new Intent(MainActivity.this, AddEditPhoneActivity.class);

                intent.putExtra(AddEditPhoneActivity.EXTRA_ID, element.getId());
                intent.putExtra(AddEditPhoneActivity.EXTRA_PROD, element.getProducent());
                intent.putExtra(AddEditPhoneActivity.EXTRA_MODEL, element.getModel());
                intent.putExtra(AddEditPhoneActivity.EXTRA_WERSJA, element.getWersja());
                intent.putExtra(AddEditPhoneActivity.EXTRA_STRONA, element.getStrona_www());
                startActivityForResult(intent, EDIT_PHONE_REQ);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.pierwsza_opcja) {
            mElementViewModel.deleteAllElements();
            Toast.makeText(this, "All phones deleted!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_PHONE_REQ && resultCode == RESULT_OK) {
            String producent = data.getStringExtra(AddEditPhoneActivity.EXTRA_PROD);
            String model = data.getStringExtra(AddEditPhoneActivity.EXTRA_MODEL);
            String wersja = data.getStringExtra(AddEditPhoneActivity.EXTRA_WERSJA);
            String strona = data.getStringExtra(AddEditPhoneActivity.EXTRA_STRONA);

            Element element = new Element(producent, model, wersja, strona);
            mElementViewModel.insert(element);
            Toast.makeText(this, "Phone saved!", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_PHONE_REQ && resultCode == RESULT_OK) {
            long id = data.getLongExtra(AddEditPhoneActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String producent = data.getStringExtra(AddEditPhoneActivity.EXTRA_PROD);
            String model = data.getStringExtra(AddEditPhoneActivity.EXTRA_MODEL);
            String wersja = data.getStringExtra(AddEditPhoneActivity.EXTRA_WERSJA);
            String strona = data.getStringExtra(AddEditPhoneActivity.EXTRA_STRONA);
            Element element = new Element(producent, model, wersja, strona);
            element.setId(id);
            mElementViewModel.update(element);
            Toast.makeText(this, "Phone edited!", Toast.LENGTH_SHORT).show();
        }
    }
}