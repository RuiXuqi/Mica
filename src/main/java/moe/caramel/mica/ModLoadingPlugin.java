package moe.caramel.mica;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

@IFMLLoadingPlugin.Name(Reference.MOD_NAME)
@IFMLLoadingPlugin.MCVersion("1.12.2")
public class ModLoadingPlugin implements IFMLLoadingPlugin {
    @Nullable
    @Override
    public String[] getASMTransformerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getModContainerClass() {
        return null;
    }

    @Nullable
    @Override
    public String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String, Object> map) {
    }

    @Nullable
    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
