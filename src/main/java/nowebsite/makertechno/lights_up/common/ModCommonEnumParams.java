package nowebsite.makertechno.lights_up.common;

import net.minecraft.world.item.ItemUseAnimation;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;

public class ModCommonEnumParams {
    public static final EnumProxy<ItemUseAnimation> CUSTOM_SWINGING_USE_ANIM_ENUM_PROXY = new EnumProxy<>(
            ItemUseAnimation.class, -1, "lights_up:shaking", true
    );

}