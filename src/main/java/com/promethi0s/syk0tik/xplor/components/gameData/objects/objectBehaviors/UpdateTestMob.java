package com.promethi0s.syk0tik.xplor.components.gameData.objects.objectBehaviors;

import com.promethi0s.syk0tik.xplor.components.gameData.objects.mapObjects.MapObject;
import com.promethi0s.syk0tik.xplor.components.gameData.objects.mapObjects.TestMob;
import com.promethi0s.syk0tik.xplor.components.gameData.positioning.Node;

import java.util.ArrayList;
import java.util.Random;

public class UpdateTestMob implements UpdateBehavior {

    private TestMob testMob;
    private int updateCounter;
    private boolean hasUpdated;
    private ArrayList<Node> path;

    public UpdateTestMob(TestMob testMob) {

        this.testMob = testMob;
        hasUpdated = false;

    }

    @Override
    public void update() {

        if (hasUpdated) return;

        Random random = new Random();
        int faceDir = testMob.getFaceDir();

        if (updateCounter == 0) faceDir = random.nextInt(4);

        switch (faceDir) {
            case 0:
                testMob.moveUp();
                break;
            case 1:
                testMob.moveRight();
                break;
            case 2:
                testMob.moveDown();
                break;
            case 3:
                testMob.moveLeft();
                break;
        }

        /*
        Coordinates loc = testMob.getLoc();

        if (updateCounter == 0) path = Pathfinding.getPath(loc, Map.getClient().getLoc());

        testMob.followPath(path);
        */

        ArrayList<MapObject> attackables = testMob.getAttackableContacts();
        if (attackables != null) {
            for (MapObject attackable : attackables) {
                testMob.attack(attackable);
            }
        }

        updateCounter++;
        if (updateCounter == 120) updateCounter = 0;

        hasUpdated = true;

    }

    @Override
    public void resetUpdateStatus() {

        hasUpdated = false;

    }

}
