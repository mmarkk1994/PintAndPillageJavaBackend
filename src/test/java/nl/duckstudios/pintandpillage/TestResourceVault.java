package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.buildings.Building;
import nl.duckstudios.pintandpillage.entity.buildings.ResourceVault;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;

public class TestResourceVault {
    ResourceVault resourceVaultUnderTesting;

    @BeforeEach
    void init() {
        this.resourceVaultUnderTesting = new ResourceVault();
    }

    @Test
    void should_instantiateInstanceOfClassBuilding_when_instantiatingResourceVault() {
        assertThat(resourceVaultUnderTesting, instanceOf(Building.class));
    }

    @Test
    void should_getNameVault_when_getNameIsCalled() {
        String actualName = resourceVaultUnderTesting.getName();

        assertThat(actualName, is("Vault"));
    }

    @Test
    void should_setBaseTimeTo10Seconds_when_instantiated() {
        LocalTime actualBaseTime = resourceVaultUnderTesting.getConstructionTime();

        assertThat(actualBaseTime, is(LocalTime.of(0, 0, 10)));
    }

    @Test
    void should_setBaseCostTo25_when_instantiated() {
        Map<String, Integer> actualResourcesRequired = resourceVaultUnderTesting.getResourcesRequiredLevelUp();

        assertThat(actualResourcesRequired, is(Map.of("Wood", 25, "Stone", 25)));
    }

    @Test
    void should_setPillagableResourcesTo60Procent_when_calculatePillagableResourcesIsCalled() {
        HashMap<String, Integer> resources = new HashMap<>();
        resources.put("Wood", 100);
        Map<String, Integer> expected = Map.of("Wood", 60);

        Map<String, Integer> actual = resourceVaultUnderTesting.calculatePillagableResources(resources);

        assertThat(actual, is(expected));
    }

    @Test
    void should_setConstructionTimeTo30_when_updateBuildingIsCalledAndLevelIs1() {
        LocalTime expectedConstructionTime = LocalTime.of(0, 0, 30);
        resourceVaultUnderTesting.setLevel(1);

        resourceVaultUnderTesting.updateBuilding();

        assertThat(resourceVaultUnderTesting.getConstructionTime(), is(expectedConstructionTime));
    }

    @Test
    void should_setResourceRequiredTo35_when_updateBuildingIsCalledAndLevelIs1() {
        Map<String, Integer> expectedResourceRequired = Map.of("Wood", 35, "Stone", 35);
        resourceVaultUnderTesting.setLevel(1);

        resourceVaultUnderTesting.updateBuilding();

        assertThat(resourceVaultUnderTesting.getResourcesRequiredLevelUp(), is(expectedResourceRequired));
    }
}
