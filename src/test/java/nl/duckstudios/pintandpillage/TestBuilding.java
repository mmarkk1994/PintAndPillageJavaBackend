package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.BuildingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("Building")
public class TestBuilding {
    private MockBuilding buildingUnderTesting;
    private final ResourceHelper resourceHelper = new ResourceHelper();
    @Spy
    private ResourceManager spyResourceManager;
    @Mock
    private Village villageMock;

    @BeforeEach
    void initMockBuilding(){
        this.buildingUnderTesting = new MockBuilding();
    }

    void setupStubVillage(){
        this.buildingUnderTesting.setResourcesRequiredLevelUp(this.resourceHelper.generateResource(ResourceType.Stone, 1));
        this.buildingUnderTesting.setVillage(villageMock);
        this.buildingUnderTesting.setResourceManager(spyResourceManager);

        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));
        when(this.villageMock.hasEnoughPopulation(anyInt())).thenReturn(true);
    }

    @Test
    void should_throwNotEnoughResourcesException_when_notEnoughResourcesInVillage(){
        this.buildingUnderTesting.setResourcesRequiredLevelUp(this.resourceHelper.generateResource(ResourceType.Stone, 100));
        this.buildingUnderTesting.setVillage(villageMock);

        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));

        BuildingConditionsNotMetException thrown = assertThrows( BuildingConditionsNotMetException.class,
                () -> this.buildingUnderTesting.levelUp());

        assertThat(thrown.getMessage(), is("Not enough resources available"));
    }

    @Test
    void should_callSubtractResourcesOnlyOnce_when_levelUpIsCalled(){
        this.setupStubVillage();

        this.buildingUnderTesting.levelUp();

        Mockito.verify(spyResourceManager, times(1)).subtractResources(any(), any());
    }

    @Test
    void should_throwNotEnoughPopulationException_when_notEnoughPopulationInVillage(){
        this.buildingUnderTesting.setResourcesRequiredLevelUp(this.resourceHelper.generateResource(ResourceType.Stone, 1));
        this.buildingUnderTesting.setVillage(villageMock);

        when(this.villageMock.getVillageResources()).thenReturn(this.resourceHelper.generateResource(ResourceType.Stone, 10));
        when(this.villageMock.hasEnoughPopulation(anyInt())).thenReturn(false);

        BuildingConditionsNotMetException thrown = assertThrows( BuildingConditionsNotMetException.class,
                () -> this.buildingUnderTesting.levelUp());

        assertThat(thrown.getMessage(), is("Not enough population available"));
    }

    @Test
    void should_setConstructionFinishedTime_when_levelUpIsCalled(){
        LocalDateTime timeNow = LocalDateTime.now();
        LocalDateTime expectedFinishTime = timeNow.plusSeconds(10);

        this.setupStubVillage();
        this.buildingUnderTesting.setConstructionTimeSeconds(10);

        this.buildingUnderTesting.levelUp();

        LocalDateTime actualFinishTime = this.buildingUnderTesting.getLevelupFinishedTime();

        assertThat(actualFinishTime.withNano(0), is(expectedFinishTime.withNano(0)));

    }

    @Test
    void should_underConstructionToTrue_when_levelUpIsCalled(){
        this.setupStubVillage();
        boolean expectedUnderConstruction = true;

        this.buildingUnderTesting.levelUp();
        boolean actualUnderConstruction = this.buildingUnderTesting.isUnderConstruction();

        assertThat(actualUnderConstruction, is(expectedUnderConstruction));
    }
}
