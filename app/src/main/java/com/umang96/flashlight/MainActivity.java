package com.umang96.flashlight;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private String tile_label;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("tile_preferences", MODE_PRIVATE);

        addButtonClickListener1();
        addButtonClickListener2();

        et1 = findViewById(R.id.editText1);

        pref_load();
    }

    private void addButtonClickListener1() {
        Button b1 = findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TorchUtils.checkState(getApplicationContext());
            }
        });
    }

    private void addButtonClickListener2() {
        Button b1 = findViewById(R.id.button2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              tile_label = et1.getText().toString();
              pref_edit(tile_label);
            }
        });
    }

    private void pref_edit(String st) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("tile_name", st);
        editor.apply();
        Toast.makeText(getApplicationContext(), "Tile name applied!", Toast.LENGTH_SHORT).show();
    }

    private void pref_load() {
        String restoredText = prefs.getString("tile_name", null);
        if (restoredText != null) {
            String temp = prefs.getString("tile_name", "Flashlight");
            et1.setText(temp);
        }
    }

}
