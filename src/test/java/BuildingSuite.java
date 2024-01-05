import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@SelectPackages("nl.duckstudios.pintandpillage")
@IncludeTags({"Building", "ProductionBuilding", "ResourceBuilding", "ResourceVault"})
@Suite
public class BuildingSuite {

}
