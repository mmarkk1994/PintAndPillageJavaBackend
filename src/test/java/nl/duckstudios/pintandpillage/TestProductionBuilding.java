package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.BuildingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.production.Spear;
import nl.duckstudios.pintandpillage.entity.production.UnitProductionItem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@Tag("ProductionBuilding")
public class TestProductionBuilding {
    
    // TODO: testen of ik een battleship en een defenceship kan bouwen
    // TODO: requirement 10 testen

    private MockProductionBuilding productionBuildingUnderTesting;

    @BeforeEach
    void initMockProductionBuilding(){
        this.productionBuildingUnderTesting = new MockProductionBuilding();
    }

    @Test
    void should_throwBuildingConditionsNotMetException_when_productionQueueIsFull() {
        this.productionBuildingUnderTesting.setQueueLimit(0);
        this.productionBuildingUnderTesting.addToQueue(new UnitProductionItem(new Spear(), 1));

        BuildingConditionsNotMetException thrown = assertThrows( BuildingConditionsNotMetException.class,
                () -> this.productionBuildingUnderTesting.addToQueue(new UnitProductionItem(new Spear(), 1)));

        assertThat(thrown.getMessage(), is("The production queue is full"));
    }

    @Test
    void should_upgradeHarbor_when_levelUpBuildingIsCalled() {
        // Zie levelUpBuilding in BuildingController


    }

}
