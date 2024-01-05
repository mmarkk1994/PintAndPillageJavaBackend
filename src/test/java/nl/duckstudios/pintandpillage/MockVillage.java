package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Building;

import java.util.Set;

public class MockVillage extends Village {
    public Set<Building> getBuildings() {
        return this.buildings;
    }
}
