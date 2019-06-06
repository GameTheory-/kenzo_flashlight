package com.umang96.flashlight;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;


public class TorchTileService extends TileService {

    @Override
    public void onClick() {
        super.onClick();
        boolean hasCameraFlash = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (hasCameraFlash) {
            TorchUtils.toggleTorch(this);
            updateTile();
        } else {
            Toast.makeText(this, "Flash not available on your device!", Toast.LENGTH_LONG).show();
        }
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
        boolean isActive = getPrefs().getBoolean("tile_state", false);

        Icon newIcon;
        int newState;

        if (isActive) {
            newIcon = Icon.createWithResource(this, R.drawable.ic_torch_on);
            newState = Tile.STATE_ACTIVE;
            setTile(newIcon, newState);
        } else {
            newIcon = Icon.createWithResource(this, R.drawable.ic_torch_off);
            newState = Tile.STATE_INACTIVE;
            setTile(newIcon, newState);
        }
    }

    private void setTile(Icon icon, int state) {
        Tile tile = getQsTile();
        tile.setLabel(getTileName());
        tile.setIcon(icon);
        tile.setState(state);
        tile.updateTile();
    }

    private String getTileName() {
        return getPrefs().getString("tile_name", "Flashlight");
    }

    private SharedPreferences getPrefs() {
        return getSharedPreferences("tile_preferences", MODE_PRIVATE);
    }

}
