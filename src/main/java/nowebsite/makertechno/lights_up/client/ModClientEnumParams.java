package nowebsite.makertechno.lights_up.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;
import net.neoforged.fml.common.asm.enumextension.EnumProxy;
import net.neoforged.neoforge.client.IArmPoseTransformer;
import nowebsite.makertechno.lights_up.common.item.LightStickItem;

public class ModClientEnumParams {

    public static final IArmPoseTransformer TRANSFORMER = (model, entity, arm) ->  {
        if (arm.equals(HumanoidArm.RIGHT)) {
            animateHoldingItemSwayRightArm(model.rightArm, entity.ticksUsingItem);
            if (entity.getUseItemStackForArm(HumanoidArm.LEFT).getItem() instanceof LightStickItem) animateHoldingItemSwayLeftArm(model.leftArm, entity.ticksUsingItem);
        }
        if (arm.equals(HumanoidArm.LEFT)) animateHoldingItemSwayLeftArm(model.leftArm, entity.ticksUsingItem);
    };

    public static final EnumProxy<HumanoidModel.ArmPose> SHAKING_STICK = new EnumProxy<>(
        HumanoidModel.ArmPose.class, true, true, TRANSFORMER
    );

    private static final float SWAY_AMPLITUDE = 0.26F;
    private static final float SWAY_SPEED = 2.613F;
    private static final float ARM_ANGLE = 1.61F; // 高举角度（约92度）
    private static final float BASE_OFFSET = 0.3F;
    private static final float VERTICAL_AMPLITUDE = 0.5F; // 垂直摆动幅度

    public static void animateHoldingItemSwayRightArm(
        ModelPart rightArm,
        float ticksUsingItem
    ) {
        // 计算摆动相位
        float phase = ticksUsingItem * SWAY_SPEED * 0.1F;

        // 水平摆动（Y轴）：左右摇晃
        float horizontalSway = (float) Math.sin(phase) * SWAY_AMPLITUDE;
        rightArm.yRot = BASE_OFFSET + horizontalSway;

        // 垂直摆动（X轴）：倒U型轨迹
        // 使用 |cos(phase)| 使得在中间位置（水平居中时）手臂抬到最高
        // 在两端位置（水平摆到最左右时）手臂略微降低
        float verticalSway = (float) Math.abs(Math.cos(phase)) * VERTICAL_AMPLITUDE;
        rightArm.xRot = -(ARM_ANGLE + verticalSway); // 在举起的基础上再抬高一点

        rightArm.zRot = 0.0F;
    }

    public static void animateHoldingItemSwayLeftArm(
        ModelPart leftArm,
        float ticksUsingItem
    ) {
        // 计算摆动相位
        float phase = ticksUsingItem * SWAY_SPEED * 0.1F;

        // 水平摆动（Y轴）：左手方向相反
        float horizontalSway = (float) Math.sin(phase) * SWAY_AMPLITUDE;
        leftArm.yRot = -BASE_OFFSET + horizontalSway;

        // 垂直摆动（X轴）：倒U型轨迹
        float verticalSway = (float) Math.abs(Math.cos(phase)) * VERTICAL_AMPLITUDE;
        leftArm.xRot = -(ARM_ANGLE + verticalSway);

        leftArm.zRot = 0.0F;
    }
}
