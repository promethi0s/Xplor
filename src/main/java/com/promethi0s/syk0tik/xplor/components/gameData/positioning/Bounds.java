package com.promethi0s.syk0tik.xplor.components.gameData.positioning;

import com.promethi0s.syk0tik.xplor.components.graphics.Sprite;

public class Bounds {

    protected int x0, x1, y0, y1;
    private int x0Offset, x1Offset, y0Offset, y1Offset;

    public Bounds(Coordinates spawnPoint, Sprite sprite) {

        x0Offset = sprite.scale;
        x1Offset = 0;
        y0Offset = sprite.scale;
        y1Offset = 0;

        cyclePixels(sprite);

        // Update maxBounds.
        if (x1Offset - x0Offset > PositionHandler.maxBoundsX) PositionHandler.maxBoundsX = x1Offset - x0Offset;
        if (y1Offset - y0Offset > PositionHandler.maxBoundsY) PositionHandler.maxBoundsY = y1Offset - y0Offset;

        update(spawnPoint);

    }

    // Called by mapObjects with multiple sprites.
    public Bounds(Coordinates spawnPoint, int faceDir, Sprite[] sprites) {

        for (int i = 0; i < sprites.length; i++) {
            cyclePixels(sprites[i]);
        }

        update(spawnPoint);

    }

    public Bounds(Coordinates loc) {

        x0 = x1 = loc.x;
        y0 = y1 = loc.y;

    }

    private void cyclePixels(Sprite sprite) {
        for (int y = 0; y < sprite.scale; y++) {
            for (int x = 0; x < sprite.scale; x++) {
                if (sprite.getPixels()[x + y * sprite.scale] != sprite.transparentColor) setLimits(x, y);
            }
        }
    }

    // Called by creation method. Tests to see if x or y are at limits and sets if applicable.
    private void setLimits(int x, int y) {

        if (x < x0Offset) x0Offset = x;
        if (x > x1Offset) x1Offset = x;
        if (y < y0Offset) y0Offset = y;
        if (y > y1Offset) y1Offset = y;

    }

    // Called on creation, and any time an entity's position is updated. Sets new bounds.
    public void update(Coordinates loc) {

        x0 = loc.x + x0Offset;
        x1 = loc.x + x1Offset;
        y0 = loc.y + y0Offset;
        y1 = loc.y + y1Offset;

    }

    // Checks whether either Bounds are within the other.
    public boolean intersects(Bounds target) {

        if (this.isIn(target) || target.isIn(this)) return true;
        return false;

    }

    // Checks if any points in Bounds is contained within the target.
    private boolean isIn(Bounds target) {

        if ((x0 >= target.x0 && x0 <= target.x1 && y0 >= target.y0 && y0 <= target.y1) ||
                (x1 >= target.x0 && x1 <= target.x1 && y0 >= target.y0 && y0 <= target.y1) ||
                (x0 >= target.x0 && x0 <= target.x1 && y1 >= target.y0 && y1 <= target.y1) ||
                (x1 >= target.x0 && x1 <= target.x1 && y1 >= target.y0 && y1 <= target.y1)) return true;

        return false;

    }

}
