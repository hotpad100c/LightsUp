package nowebsite.makertechno.lights_up.common.event;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import nowebsite.makertechno.lights_up.common.block.entity.renderer.SpotlightRenderer;
import nowebsite.makertechno.lights_up.common.init.LUBlockReg;

@EventBusSubscriber
public class RegBEREvent {
    @SubscribeEvent
    public static void onRegRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(LUBlockReg.SPOTLIGHT_BE.get(), SpotlightRenderer::new);
    }
}
