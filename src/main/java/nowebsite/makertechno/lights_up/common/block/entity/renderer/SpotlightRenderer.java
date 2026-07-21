package nowebsite.makertechno.lights_up.common.block.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import nowebsite.makertechno.lights_up.client.render.SpotlightShaderManager;
import nowebsite.makertechno.lights_up.common.block.SpotlightBlock;
import nowebsite.makertechno.lights_up.common.block.entity.SpotlightBlockEntity;
import nowebsite.makertechno.lights_up.common.block.entity.state.SpotlightRenderState;
import org.joml.Matrix4f;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpotlightRenderer implements BlockEntityRenderer<@NotNull SpotlightBlockEntity, @NotNull SpotlightRenderState> {

    public SpotlightRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public @NotNull SpotlightRenderState createRenderState() {
        return new SpotlightRenderState();
    }

    @Override
    public void extractRenderState(SpotlightBlockEntity blockEntity, SpotlightRenderState state, float partialTicks, Vec3 cameraPosition,
                                   @Nullable ModelFeatureRenderer.CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        
        state.red = blockEntity.getRed();
        state.green = blockEntity.getGreen();
        state.blue = blockEntity.getBlue();
        state.intensity = blockEntity.getIntensity();
        state.pitch = blockEntity.getPitch();
        state.yaw = blockEntity.getYaw();
        state.coneAngle = blockEntity.getConeAngle();
        
        if (blockEntity.getBlockState().getBlock() instanceof SpotlightBlock) {
            state.facing = blockEntity.getBlockState().getValue(SpotlightBlock.FACING);
        }
        
        float alpha = 0.6f * state.intensity;
        int ir = (int) (Math.min(1.0f, state.red) * 255);
        int ig = (int) (Math.min(1.0f, state.green) * 255);
        int ib = (int) (Math.min(1.0f, state.blue) * 255);
        int ia = (int) (Math.min(1.0f, alpha) * 255);
        state.packedColor = (ia << 24) | (ir << 16) | (ig << 8) | ib;
    }

    @Override
    public void submit(SpotlightRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        
        poseStack.pushPose();
        applyFacingRotation(poseStack, state.facing);
        
        // 使用新的 RenderType
        submitNodeCollector.submitCustomGeometry(
            poseStack,
            SpotlightShaderManager.SPOTLIGHT_BEAM,
            (pose, consumer) -> renderBeamGeometry(pose, consumer, state)
        );
        
        poseStack.popPose();
    }

    private void renderBeamGeometry(PoseStack.Pose pose, VertexConsumer consumer, SpotlightRenderState state) {
        Matrix4f matrix = pose.pose();
        
        float beamLength = state.beamLength;
        float endRadius = (float) (beamLength * Math.tan(Math.toRadians(state.coneAngle)));
        int color = state.packedColor;
        int segments = 32;
        
        // 绘制锥体侧面
        for (int i = 0; i < segments; i++) {
            float angle1 = (float) (2 * Math.PI * i / segments);
            float angle2 = (float) (2 * Math.PI * (i + 1) / segments);
            
            float x1 = (float) (endRadius * Math.cos(angle1));
            float z1 = (float) (endRadius * Math.sin(angle1));
            float x2 = (float) (endRadius * Math.cos(angle2));
            float z2 = (float) (endRadius * Math.sin(angle2));
            
            addVertex(consumer, matrix, 0, 0, 0, color);
            addVertex(consumer, matrix, x1, beamLength, z1, color);
            addVertex(consumer, matrix, x2, beamLength, z2, color);
            
            addVertex(consumer, matrix, 0, 0, 0, color);
            addVertex(consumer, matrix, x2, beamLength, z2, color);
            addVertex(consumer, matrix, x1, beamLength, z1, color);
        }
        
        drawEndCap(consumer, matrix, beamLength, endRadius, color, segments);
    }

    private void addVertex(VertexConsumer consumer, Matrix4f matrix, 
                           float x, float y, float z, int color) {
        consumer.addVertex(matrix, x, y, z)
               .setColor(color)
               .setUv(0, 0)
               .setLight(0xF000F0);
    }

    private void drawEndCap(VertexConsumer consumer, Matrix4f matrix, 
                            float length, float radius, int color, int segments) {
        for (int i = 0; i < segments; i++) {
            float angle1 = (float) (2 * Math.PI * i / segments);
            float angle2 = (float) (2 * Math.PI * (i + 1) / segments);
            
            float x1 = (float) (radius * Math.cos(angle1));
            float z1 = (float) (radius * Math.sin(angle1));
            float x2 = (float) (radius * Math.cos(angle2));
            float z2 = (float) (radius * Math.sin(angle2));
            
            addVertex(consumer, matrix, 0, length, 0, color);
            addVertex(consumer, matrix, x1, length, z1, color);
            addVertex(consumer, matrix, x2, length, z2, color);
        }
    }

    private void applyFacingRotation(PoseStack poseStack, Direction facing) {
        switch (facing) {
            case UP -> {}
            case DOWN -> poseStack.mulPose(Axis.XP.rotationDegrees(180));
            case NORTH -> poseStack.mulPose(Axis.XP.rotationDegrees(90));
            case SOUTH -> poseStack.mulPose(Axis.XN.rotationDegrees(90));
            case EAST -> poseStack.mulPose(Axis.ZP.rotationDegrees(90));
            case WEST -> poseStack.mulPose(Axis.ZN.rotationDegrees(90));
        }
    }

    @Override
    public boolean shouldRenderOffScreen() {
        return true;
    }

    @Override
    public AABB getRenderBoundingBox(SpotlightBlockEntity blockEntity) {
        return new AABB(blockEntity.getBlockPos()).inflate(114);
    }

    @Override
    public boolean shouldRender(SpotlightBlockEntity blockEntity, Vec3 cameraPosition) {
        return true;
    }
}