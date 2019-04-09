package com.umang96.flashlight;

import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;

public class TorchTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        TorchUtils.checkState(this);
        updateTile();
    }

    @Override
    public void onTileAdded() {
        super.onTileAdded();
        updateTile();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();
        updateTile();
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    private void updateTile() {
        SharedPreferences prefs = getSharedPreferences("tile_preferences", MODE_PRIVATE);
        boolean isActive = prefs.getBoolean("tile_state", false);

        Tile tile = getQsTile();
        Icon newIcon;
        int newState;

        if (isActive) {
            newIcon = Icon.createWithResource(this, R.drawable.ic_torch_on);
            newState = Tile.STATE_ACTIVE;
        } else {
            newIcon = Icon.createWithResource(this, R.drawable.ic_torch_off);
            newState = Tile.STATE_INACTIVE;
        }

        tile.setLabel(getTileName());
        tile.setIcon(newIcon);
        tile.setState(newState);
        tile.updateTile();
    }

    private String getTileName() {
        SharedPreferences prefs = getSharedPreferences("tile_preferences", MODE_PRIVATE);
        return prefs.getString("tile_name", "Flashlight");
    }

}
