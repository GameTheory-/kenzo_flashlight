package com.umang96.flashlight;

import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class TorchTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        updateTile(2);
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        updateTile(1);
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile(1);
    }

    private void updateTile(int x) {
        boolean isActive = false;

        if (x == 2) {
            isActive = TorchUtils.check(this);
        }

        Tile tile = getQsTile();
        Icon newIcon;
        int newState = Tile.STATE_ACTIVE;

        if (isActive) {
            newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_torch_on);
        } else {
            newIcon = Icon.createWithResource(getApplicationContext(), R.drawable.ic_torch_off);
        }

        tile.setLabel(getTileName());
        if (x == 2) {
            tile.setIcon(newIcon);
        }
        tile.setState(newState);
        tile.updateTile();
    }

    private String getTileName() {
        SharedPreferences prefs = getSharedPreferences("tile_preferences", MODE_PRIVATE);
        String restoredText = prefs.getString("tile_name", null);
        String temp="Flashlight";
        if (restoredText != null) {
            temp = prefs.getString("tile_name", "Flashlight");
        }
        return temp;
    }

}
