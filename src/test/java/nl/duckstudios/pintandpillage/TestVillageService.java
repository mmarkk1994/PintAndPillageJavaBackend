package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.SettleConditionsNotMetException;
import nl.duckstudios.pintandpillage.dao.VillageDataMapper;
import nl.duckstudios.pintandpillage.entity.Coord;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.WorldMap;
import nl.duckstudios.pintandpillage.helper.ResourceManager;
import nl.duckstudios.pintandpillage.service.DistanceService;
import nl.duckstudios.pintandpillage.service.VillageService;
import nl.duckstudios.pintandpillage.service.WorldService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestVillageService {
    @Mock
    private Village villageMock;
    @Mock
    private VillageDataMapper villageDataMapperMock;
    @Mock
    private ResourceManager resourceManagerMock;
    @Mock
    private WorldService worldServiceMock;
    @Mock
    private DistanceService distanceServiceMock;
    private VillageService villageServiceUnderTesting;
    private VillageUnit villageUnit;

    @BeforeEach
    void initVillageService(){
        this.villageServiceUnderTesting = new VillageService(villageDataMapperMock, resourceManagerMock, worldServiceMock, distanceServiceMock);
        this.villageUnit = new VillageUnit();
    }

    @Test
    void should_throwSettleConditionsNotMetException_when_hasNoJarl() {
        villageUnit.setAmount(0);

        when(this.villageMock.getUnitInVillage(any())).thenReturn(villageUnit);

        SettleConditionsNotMetException thrown = assertThrows(SettleConditionsNotMetException.class,
                () -> this.villageServiceUnderTesting.checkIsValidCreatingSpot(villageMock, new Coord(0, 0)));

        assertThat(thrown.getMessage(), is("To create a new village you need a jarl"));
    }

    @Test
    void should_throwSettleConditionsNotMetException_when_distanceIsIntSix() {
        villageUnit.setAmount(1);

        when(this.villageMock.getUnitInVillage(any())).thenReturn(villageUnit);
        when(this.distanceServiceMock.calculateDistance(any(), any())).thenReturn(6);

        SettleConditionsNotMetException thrown = assertThrows(SettleConditionsNotMetException.class,
                () -> this.villageServiceUnderTesting.checkIsValidCreatingSpot(villageMock, new Coord(0, 0)));

        assertThat(thrown.getMessage(), is("Too much distance between your village and the new village"));
    }

    @Test
    void should_throwSettleConditionsNotMetException_when_coordIsInvalid() {
        villageUnit.setAmount(1);
        Coord invalidCoord = new Coord(0, 0);

        when(this.villageMock.getUnitInVillage(any())).thenReturn(villageUnit);
        when(this.distanceServiceMock.calculateDistance(any(), any())).thenReturn(5);
        when(this.worldServiceMock.getWorldMap()).thenReturn(new WorldMap(34843, 50, 50, 25));

        SettleConditionsNotMetException thrown = assertThrows(SettleConditionsNotMetException.class,
                () -> this.villageServiceUnderTesting.checkIsValidCreatingSpot(villageMock, invalidCoord));

        assertThat(thrown.getMessage(), is("Invalid build spot for a new village"));
    }

    // TODO: Zorgen dat aan alle condities voldaan wordt door een validCoord aan te maken
//    @Test
//    void should_notThrowSettleConditionsNotMetException_when_allConditionsAreMet() {
//        villageUnit.setAmount(1);
//        Coord validCoord = new Coord(0, 0);
//
//        when(this.villageMock.getUnitInVillage(any())).thenReturn(villageUnit);
//        when(this.distanceServiceMock.calculateDistance(any(), any())).thenReturn(5);
//        when(this.worldServiceMock.getWorldMap()).thenReturn(new WorldMap(34843, 50, 50, 25));
//
//        assertDoesNotThrow(() -> this.villageServiceUnderTesting.checkIsValidCreatingSpot(villageMock, validCoord));
//    }
}
