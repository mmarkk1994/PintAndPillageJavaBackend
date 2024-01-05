package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.buildings.Building;
import nl.duckstudios.pintandpillage.entity.buildings.Farm;
import nl.duckstudios.pintandpillage.entity.buildings.ResourceBuilding;
import nl.duckstudios.pintandpillage.helper.ResourceManager;
import nl.duckstudios.pintandpillage.model.ResourceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("ResourceBuilding")
public class TestResourceBuilding {
    private final ResourceHelper resourceHelper = new ResourceHelper();
    private Farm farmUnderTesting;
    private MockResourceBuilding resourceBuildingUnderTesting;
    @Spy
    private ResourceManager spyResourceManager;
    @Mock
    private Village villageMock;

    @BeforeEach
    void initResourceBuilding(){
        this.farmUnderTesting = new Farm();
        this.resourceBuildingUnderTesting = new MockResourceBuilding();
    }

    void setupStubVillage() {
        resourceBuildingUnderTesting.setResourceManager(spyResourceManager);
        resourceBuildingUnderTesting.setGeneratesResource(ResourceType.Stone);
        resourceBuildingUnderTesting.setVillage(villageMock);
        resourceBuildingUnderTesting.setResourcesPerHour(1);
        resourceBuildingUnderTesting.setLastCollected(LocalDateTime.now().minusHours(1));

        when(this.villageMock.getResourceLimit()).thenReturn(1000);
        when(this.villageMock.getVillageResources()).thenReturn(new HashMap<>());
        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));
    }

    @Test
    void should_updateResourcesWithInt32PerHour_when_farmIsLevelInt1(){
        this.farmUnderTesting.setLevel(1);
        int expected = 32;

        int actual = this.farmUnderTesting.updateResourcesPerHour();

        assertThat(actual, is(expected));
    }

    @Test
    void should_produceMoreResources_when_resourceBuildingIsHigherLevel() {
        this.farmUnderTesting.setLevel(2);
        int expectedResourcesLevel1 = 32;

        int actual = this.farmUnderTesting.updateResourcesPerHour();

        assertThat(actual, greaterThan(expectedResourcesLevel1));
    }

    @Test
    void should_instantiateInstanceOfClassResourceBuilding_when_instantiatingFarm() {
        assertThat(this.farmUnderTesting, instanceOf(ResourceBuilding.class));
    }

    @Test
    void should_instantiateInstanceOfClassBuilding_when_instantiatingFarm() {
        assertThat(this.farmUnderTesting, instanceOf(Building.class));
    }

    @Test
    void should_callAddResourcesOnlyOnce_when_collectResourcesIsCalled() {
        this.setupStubVillage();

        resourceBuildingUnderTesting.collectResources();

        Mockito.verify(spyResourceManager, times(1)).addResources(any(), anyInt(), any());
    }
}
