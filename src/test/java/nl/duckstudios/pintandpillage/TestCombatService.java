package nl.duckstudios.pintandpillage;

import nl.duckstudios.pintandpillage.Exceptions.AttackingConditionsNotMetException;
import nl.duckstudios.pintandpillage.entity.Village;
import nl.duckstudios.pintandpillage.entity.VillageUnit;
import nl.duckstudios.pintandpillage.entity.production.Bow;
import nl.duckstudios.pintandpillage.entity.production.DefenceShip;
import nl.duckstudios.pintandpillage.entity.production.Spear;
import nl.duckstudios.pintandpillage.entity.production.TransportShip;
import nl.duckstudios.pintandpillage.service.CombatService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TestCombatService {

    private CombatService combatServiceUnderTesting;

    // TODO: meer testen voor userstory 25 maken
    @BeforeEach
    void initCombatService() {
        this.combatServiceUnderTesting = new CombatService();
    }

    @Test
    void should_throwAttackingConditionsNotMetException_when_notEnoughUnitsToAttack() {
        List<VillageUnit> attackingUnits = new ArrayList<>();
        attackingUnits.add(new VillageUnit(new Bow(), 10));
        Village attackingVillage = new Village();

        AttackingConditionsNotMetException thrown = assertThrows( AttackingConditionsNotMetException.class,
                () -> this.combatServiceUnderTesting.checkHasEnoughUnitsToAttack(attackingUnits, attackingVillage));

        assertThat(thrown.getMessage(), is("Not enough " + new VillageUnit(new Bow(), 10)
                .getUnit().getUnitName() + " to attack this village"));
    }

    @Test
    void should_notThrowAttackingConditionsNotMetException_when_enoughUnitsToAttack() {
        List<VillageUnit> attackingUnits = new ArrayList<>();
        attackingUnits.add(new VillageUnit(new Bow(), 9));
        Village attackingVillage = new Village();
        attackingVillage.addUnit(new Bow(), 10);

        assertDoesNotThrow(() -> this.combatServiceUnderTesting.checkHasEnoughUnitsToAttack(attackingUnits, attackingVillage));
    }
}
