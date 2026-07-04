package nowebsite.makertechno.lightsup;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

@Mod(SpotlightMod.MODID)
public class SpotlightMod {
    public static final String MODID = "lights_up";
    private static final Logger LOGGER = LogUtils.getLogger();

    // 方块注册
    public static final DeferredRegister.Blocks BLOCKS = 
        DeferredRegister.createBlocks(MODID);
    
    // 物品注册
    public static final DeferredRegister.Items ITEMS = 
        DeferredRegister.createItems(MODID);
    
    // 方块实体注册
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = 
        DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);

    // 聚光灯方块
    public static final DeferredHolder<Block, SpotlightBlock> SPOTLIGHT_BLOCK = 
        BLOCKS.register("spotlight_block", name -> new SpotlightBlock(BlockBehaviour.Properties.of()
            .strength(1.0f)
            .noOcclusion()
            .isViewBlocking((s, g, p) -> false)
            .isSuffocating((s, g, p) -> false)
            .setId(getId(name))));
    
    // 聚光灯方块物品
    public static final DeferredHolder<Item, BlockItem> SPOTLIGHT_ITEM = 
        ITEMS.registerSimpleBlockItem("spotlight_block", SPOTLIGHT_BLOCK);

    // 聚光灯方块实体类型
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<SpotlightBlockEntity>> SPOTLIGHT_BE = 
        BLOCK_ENTITY_TYPES.register("spotlight_block_entity", 
            () -> new BlockEntityType<>(SpotlightBlockEntity::new, SPOTLIGHT_BLOCK.get())
        );

    public SpotlightMod(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(SpotlightPipelines::register);
        modEventBus.addListener(this::clientSetup);
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            BlockEntityRenderers.register(SPOTLIGHT_BE.get(), SpotlightRenderer::new);
        });
    }

    private static ResourceKey<Block> getId(Identifier name) {
        return ResourceKey.create(Registries.BLOCK, name);
    }
}