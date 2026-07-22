package nowebsite.makertechno.lights_up.common.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import nowebsite.makertechno.lights_up.LightsUp;
import nowebsite.makertechno.lights_up.common.item.LightStickItem;
import org.jetbrains.annotations.NotNull;

public class LUItemReg {
    public static void touch() {}

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(LightsUp.MOD_ID);


    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_BLUE = ITEMS.register(
        "light_stick_blue",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_CYAN = ITEMS.register(
        "light_stick_cyan",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_GREEN = ITEMS.register(
        "light_stick_green",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_LIGHT_BLUE = ITEMS.register(
        "light_stick_light_blue",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_LIME = ITEMS.register(
        "light_stick_lime",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_MAGENTA = ITEMS.register(
        "light_stick_magenta",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_ORANGE = ITEMS.register(
        "light_stick_orange",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_PINK = ITEMS.register(
        "light_stick_pink",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_PURPLE = ITEMS.register(
        "light_stick_purple",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_RED = ITEMS.register(
        "light_stick_red",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_WHITE = ITEMS.register(
        "light_stick_white",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    public static final DeferredItem<@NotNull LightStickItem> LIGHT_STICK_YELLOW = ITEMS.register(
        "light_stick_yellow",
        identifier -> new LightStickItem(new Item.Properties().setId(getItemId(identifier)))
    );

    private static @NotNull ResourceKey<Item> getItemId(Identifier name) {
        return ResourceKey.create(Registries.ITEM, name);
    }
}
