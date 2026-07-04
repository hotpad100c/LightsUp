package nowebsite.makertechno.lightsup;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import org.joml.Vector3f;

public class SpotlightRenderState extends BlockEntityRenderState {
    // 光束参数
    public float red = 1.0f;
    public float green = 1.0f;
    public float blue = 1.0f;
    public float intensity = 1.0f;
    public float pitch = 30.0f;      // 俯仰角
    public float yaw = 0.0f;         // 偏航角
    public float coneAngle = 20.0f;  // 锥角
    public Direction facing = Direction.NORTH;
    
    // 光束长度和颜色缓存（用于渲染优化）
    public float beamLength = 16.0f;
    public int packedColor = 0xFFFFFFFF;
}