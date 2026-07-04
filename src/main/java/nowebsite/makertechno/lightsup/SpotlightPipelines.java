package nowebsite.makertechno.lightsup;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.ColorTargetState;
import com.mojang.blaze3d.pipeline.DepthStencilState;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.CompareOp;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;

public class SpotlightPipelines {
    
    // 注册聚光灯渲染管道
    public static final RenderPipeline SPOTLIGHT_BEAM = RenderPipeline.builder(RenderPipelines.BLOCK_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(SpotlightMod.MODID, "pipeline/spotlight_beam"))
            .withShaderDefine("SPOTLIGHT_BEAM")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
            .build();

    // 如果需要发光效果（不受光照影响）
    public static final RenderPipeline SPOTLIGHT_BEAM_UNLIT = RenderPipeline.builder(RenderPipelines.ENTITY_SNIPPET)
            .withLocation(Identifier.fromNamespaceAndPath(SpotlightMod.MODID, "pipeline/spotlight_beam_unlit"))
            .withShaderDefine("SPOTLIGHT_BEAM_UNLIT")
            .withShaderDefine("NO_OVERLAY")
            .withColorTargetState(new ColorTargetState(BlendFunction.TRANSLUCENT))
            .withDepthStencilState(new DepthStencilState(CompareOp.LESS_THAN_OR_EQUAL, false))
            .build();

    // 创建 RenderType 的工具方法
    public static RenderType createBeamRenderType() {
        RenderSetup setup = RenderSetup.builder(SPOTLIGHT_BEAM)
            // 绑定 Sampler0（如果需要自定义纹理）
            .withTexture("Sampler0", Identifier.fromNamespaceAndPath("minecraft", "textures/block/white_concrete.png"))
            // 绑定 Sampler2 - 光照贴图（重要！）
            .withTexture("Sampler2", Identifier.fromNamespaceAndPath("minecraft", "textures/misc/lightmap.png"))
            .createRenderSetup();
        return RenderType.create("spotlight_beam", setup);
    }

    // 如果不需要纹理，可以只绑定 Sampler2
    public static RenderType createBeamRenderTypeNoTexture() {
        RenderSetup setup = RenderSetup.builder(SPOTLIGHT_BEAM)
            .withTexture("Sampler2", Identifier.fromNamespaceAndPath("minecraft", "textures/misc/lightmap.png"))
            .createRenderSetup();
        return RenderType.create("spotlight_beam_no_texture", setup);
    }
}