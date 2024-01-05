package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.buildings.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestVillage {
    private MockVillage villageUnderTesting;

    @BeforeEach
    void initMockVillage(){
        this.villageUnderTesting = new MockVillage();

    }

    @Test
    void should_buildStorageBuilding_when_createBuildingIsCalledWithStorage() {
        boolean expectedIsBuild = true;
        Storage storage = new Storage();

        this.villageUnderTesting.createBuilding(storage);
        boolean actualIsBuild = this.villageUnderTesting.getBuildings().contains(storage);

        assertThat(actualIsBuild, is(expectedIsBuild));
    }

    @Test
    void should_buildMineBuilding_when_createBuildingIsCalledWithMine() {
        boolean expectedIsBuild = true;
        Mine mine = new Mine();
        mine.setLastCollected(LocalDateTime.now());

        this.villageUnderTesting.createBuilding(mine);
        boolean actualIsBuild = this.villageUnderTesting.getBuildings().contains(mine);

        assertThat(actualIsBuild, is(expectedIsBuild));
    }

}
