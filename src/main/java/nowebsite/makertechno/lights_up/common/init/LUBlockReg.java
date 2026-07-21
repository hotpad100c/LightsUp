package nowebsite.makertechno.lights_up.common.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import nowebsite.makertechno.lights_up.LightsUp;
import nowebsite.makertechno.lights_up.common.block.SpotlightBlock;
import nowebsite.makertechno.lights_up.common.block.entity.SpotlightBlockEntity;
import org.jetbrains.annotations.NotNull;

public class LUBlockReg {
    public static void touch() {}

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(LightsUp.MOD_ID);
    public static final DeferredRegister.Items ITEMS = LUItemReg.ITEMS;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, LightsUp.MOD_ID);


    public static final DeferredBlock<@NotNull SpotlightBlock> SPOTLIGHT_BLOCK = BLOCKS.register(
        "spotlight_block",
        name -> new SpotlightBlock(BlockBehaviour.Properties.of()
            .strength(1.0f).noOcclusion().isViewBlocking((s, g, p) -> false)
            .isSuffocating((s, g, p) -> false).setId(getId(name)))
    );
    public static final DeferredItem<@NotNull BlockItem> SPOTLIGHT_ITEM = ITEMS.registerSimpleBlockItem("spotlight_block", SPOTLIGHT_BLOCK);
    public static final DeferredHolder<BlockEntityType<?>, @NotNull BlockEntityType<@NotNull SpotlightBlockEntity>> SPOTLIGHT_BE = BLOCK_ENTITY_TYPES.register(
        "spotlight_block_entity",
        () -> new BlockEntityType<>(SpotlightBlockEntity::new, SPOTLIGHT_BLOCK.get())
    );

    private static @NotNull ResourceKey<Block> getId(Identifier name) {
        return ResourceKey.create(Registries.BLOCK, name);
    }
}
