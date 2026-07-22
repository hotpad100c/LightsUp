package nowebsite.makertechno.lights_up.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import nowebsite.makertechno.lights_up.LightsUp;
import org.jetbrains.annotations.NotNull;

public class LUCreativeTabs {
    public static void touch() {

    }

    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, LightsUp.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, @NotNull CreativeModeTab> CALL = TABS.register(
        "call",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.lights_up.call"))
            .icon(() -> new ItemStack(LUItemReg.LIGHT_STICK_RED.get()))
            .build()
    );

    public static void registerCreativeTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab().equals(CALL.get())) {
            LUItemReg.ITEMS.getEntries().forEach(item -> event.accept(item.get()));
        }
    }
}
