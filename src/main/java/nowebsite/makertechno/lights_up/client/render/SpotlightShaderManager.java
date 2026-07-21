package nowebsite.makertechno.lights_up.client.render;

import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

public class SpotlightShaderManager {

    public static final RenderType SPOTLIGHT_BEAM = SpotlightPipelines.createBeamRenderType();

    // 如果不需要纹理，使用这个
    public static final RenderType SPOTLIGHT_BEAM_NO_TEXTURE = SpotlightPipelines.createBeamRenderTypeNoTexture();

    // 如果需要动态纹理（使用 Function 缓存）
    private static final java.util.function.Function<Identifier, RenderType> BEAM_WITH_TEXTURE = 
        Util.memoize(texture -> {
            RenderSetup setup = RenderSetup.builder(SpotlightPipelines.SPOTLIGHT_BEAM)
                .withTexture("Sampler0", texture)
                .createRenderSetup();
            return RenderType.create("spotlight_beam_" + texture.getPath(), setup);
        });

    public static RenderType getBeamWithTexture(Identifier texture) {
        return BEAM_WITH_TEXTURE.apply(texture);
    }
}