package com.umang96.flashlight;

import android.content.pm.PackageManager;
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
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("tile_preferences", MODE_PRIVATE);

        torchButton();
        tileLabelButton();

        editText = findViewById(R.id.edit_text);

        pref_load();
    }

    private void torchButton() {
        Button b1 = findViewById(R.id.torch_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
                if (hasCameraFlash) {
                    TorchUtils.toggleTorch(getApplicationContext());
                } else {
                    Toast.makeText(getApplicationContext(), "Flash not available on your device!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void tileLabelButton() {
        Button b1 = findViewById(R.id.tile_label_button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              tile_label = editText.getText().toString();
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
            editText.setText(temp);
        }
    }

}
