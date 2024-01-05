package nl.duckstudios.pintandpillage.entity.buildings;

import lombok.Getter;
import nl.duckstudios.pintandpillage.model.ResourceType;

import java.util.HashMap;

public class ResourceVault extends Building {

    @Getter
    private String name = "Vault";

    public ResourceVault() {
        super.setConstructionTimeSeconds(10);
        super.setResourcesRequiredLevelUp(new HashMap<>() {
            {
                put(ResourceType.Wood.name(), 25);
                put(ResourceType.Stone.name(), 25);
            }
        });
    }

    @Override
    public void updateBuilding() {
        this.setConstructionTimeGivenLevel(super.getLevel());
        this.setResourceRequiredAtGivenLevel(super.getLevel());
    }

    private void setConstructionTimeGivenLevel(int level) {
        super.setConstructionTimeSeconds((level * 20) + 10);
    }

    private void setResourceRequiredAtGivenLevel(int level) {
        super.setResourcesRequiredLevelUp(new HashMap<>() {
            {
                put(ResourceType.Wood.name(), level * 10 + 25);
                put(ResourceType.Stone.name(), level * 10 + 25);
            }
        });
    }

    public HashMap<String, Integer> calculatePillagableResources(HashMap<String, Integer> resources) {
        for (String key : resources.keySet()) {
            resources.put(key, (resources.get(key) / 100) * 60);
        }

        return resources;
    }

}
