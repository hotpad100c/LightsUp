package nowebsite.makertechno.lights_up.common.event;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import nowebsite.makertechno.lights_up.client.ModClientEnumParams;
import nowebsite.makertechno.lights_up.common.ModCommonEnumParams;
import nowebsite.makertechno.lights_up.common.init.LUItemReg;
import org.jspecify.annotations.Nullable;

@EventBusSubscriber
public class ItemExtensionEvent {
    @SubscribeEvent
    public static void regItemExtension(RegisterClientExtensionsEvent event) {
        event.registerItem(new IClientItemExtensions() {
                               // 记录上一帧的动画时间（用于 smoothstep 插值）
                               private float prevAnimTime = 0.0F;

                               @Override
                               public HumanoidModel.@Nullable ArmPose getArmPose(LivingEntity entityLiving, InteractionHand hand, ItemStack itemStack) {
                                   return ModClientEnumParams.SHAKING_STICK.getValue();
                               }

                               @Override
                               public boolean applyForgeHandTransform(PoseStack poseStack, LocalPlayer player, HumanoidArm arm, ItemStack itemInHand, float partialTick, float equipProcess, float swingProcess) {
                                   if (player.isUsingItem() && player.getUseItemRemainingTicks() > 0) {
                                       ItemUseAnimation useAnimation = itemInHand.getUseAnimation();
                                       if (!useAnimation.equals(ModCommonEnumParams.CUSTOM_SWINGING_USE_ANIM_ENUM_PROXY.getValue())) return false;

                                       int invert = arm == HumanoidArm.RIGHT ? 1 : -1;
                                       poseStack.translate(invert * 0.3F, -0.52F + equipProcess * -0.6F, -0.42F);
                                       poseStack.mulPose(Axis.YP.rotationDegrees(invert * 30.0F));
                                       poseStack.mulPose(Axis.ZP.rotationDegrees(invert * -85.0F));

                                       // 计算当前帧的动画时间（考虑 partialTick 进行平滑）
                                       float usingTime = player.getTicksUsingItem();
                                       float cycleTime = 20.0F;
                                       float rawTime = usingTime % cycleTime;

                                       // 用 partialTick 在整数 tick 之间进行 smooth 插值
                                       float animTime = rawTime + partialTick;
                                       if (animTime >= cycleTime) animTime -= cycleTime;

                                       // 如果检测到时间回退（说明进入了新的循环），需要同步 prevAnimTime
                                       if (animTime < prevAnimTime - 1.0F) {
                                           // 循环重置，将 prevAnimTime 调整到上一周期
                                           prevAnimTime = animTime - (1.0F / 20.0F); // 假设上一帧是 1 tick 前
                                       }

                                       // 计算当前平滑后的角度
                                       float currentSwingZ = Mth.sin(animTime / cycleTime * (float)Math.PI * 2) * 30.0F;
                                       float currentSwingX = (1.0F - Mth.abs(Mth.sin(animTime / cycleTime * (float)Math.PI))) * 25.0F + 5.0F;

                                       // 使用 smoothstep 在 prevAnimTime 和 animTime 之间过渡（可选额外平滑）
                                       float smoothSwingZ = currentSwingZ;
                                       float smoothSwingX = currentSwingX;

                                       // 如果有上一帧数据，做轻微的低通滤波以避免突变
                                       if (prevAnimTime > 0) {
                                           float filterStrength = 0.3F; // 滤波强度，越大越平滑但响应慢
                                           float prevSwingZ = Mth.sin(prevAnimTime / cycleTime * (float)Math.PI * 2) * 30.0F;
                                           float prevSwingX = (1.0F - Mth.abs(Mth.sin(prevAnimTime / cycleTime * (float)Math.PI))) * 25.0F + 5.0F;

                                           smoothSwingZ = prevSwingZ + (smoothSwingZ - prevSwingZ) * filterStrength;
                                           smoothSwingX = prevSwingX + (smoothSwingX - prevSwingX) * filterStrength;
                                       }

                                       poseStack.mulPose(Axis.ZP.rotationDegrees(invert * smoothSwingZ));
                                       poseStack.mulPose(Axis.XP.rotationDegrees(-smoothSwingX));

                                       poseStack.mulPose(Axis.XP.rotationDegrees(-10.0F));

                                       // 更新上一帧的动画时间
                                       prevAnimTime = animTime;

                                       return true;
                                   }

                                   // 重置状态
                                   prevAnimTime = 0.0F;
                                   return false;
                               }
                           }, LUItemReg.LIGHT_STICK_BLUE,
            LUItemReg.LIGHT_STICK_CYAN,
            LUItemReg.LIGHT_STICK_GREEN,
            LUItemReg.LIGHT_STICK_LIGHT_BLUE,
            LUItemReg.LIGHT_STICK_LIME,
            LUItemReg.LIGHT_STICK_MAGENTA,
            LUItemReg.LIGHT_STICK_ORANGE,
            LUItemReg.LIGHT_STICK_PINK,
            LUItemReg.LIGHT_STICK_PURPLE,
            LUItemReg.LIGHT_STICK_RED,
            LUItemReg.LIGHT_STICK_WHITE,
            LUItemReg.LIGHT_STICK_YELLOW
        );
    }

}
