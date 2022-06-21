package com.example.apka2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditPhoneActivity extends AppCompatActivity {
    public static final String EXTRA_PROD = "com.example.apka2.EXTRA_PROD",
            EXTRA_MODEL = "com.example.apka2.EXTRA_MODEL",
            EXTRA_WERSJA = "com.example.apka2.EXTRA_WERSJA",
            EXTRA_STRONA = "com.example.apka2.EXTRA_STRONA",
            EXTRA_ID = "com.example.apka2.EXTRA_ID";

    private EditText producent, model, wersja, strona;
    Button web_site, cancel, save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        producent = findViewById(R.id.producent);
        model = findViewById(R.id.model);
        wersja = findViewById(R.id.wersja);
        strona = findViewById(R.id.strona);


        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit phone!");
            producent.setText(intent.getStringExtra(EXTRA_PROD));
            model.setText(intent.getStringExtra(EXTRA_MODEL));
            wersja.setText(intent.getStringExtra(EXTRA_WERSJA));
            strona.setText(intent.getStringExtra(EXTRA_STRONA));

        } else {
            setTitle("Add phone!");
        }

        web_site = findViewById(R.id.web_site);
        cancel = findViewById(R.id.cancel);
        save = findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zapiszTelefon();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        web_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otworzStrone(view);
            }
        });
    }

    private void otworzStrone(View view) {
        String url = strona.getText().toString();
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "http://" + url;
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse(url));
        startActivity(browserIntent);
    }

    private void zapiszTelefon() {
        String prod_val = producent.getText().toString();
        String model_val = model.getText().toString();
        String wersja_val = wersja.getText().toString();
        String strona_val = strona.getText().toString();

        if (prod_val.trim().isEmpty() || model_val.trim().isEmpty() || wersja_val.trim().isEmpty() || strona_val.trim().isEmpty()) {
            Toast.makeText(this, "Prosze uzupelnic wszystkie pola", Toast.LENGTH_LONG).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_PROD, prod_val);
        data.putExtra(EXTRA_MODEL, model_val);
        data.putExtra(EXTRA_WERSJA, wersja_val);
        data.putExtra(EXTRA_STRONA, strona_val);

        long id = getIntent().getLongExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}