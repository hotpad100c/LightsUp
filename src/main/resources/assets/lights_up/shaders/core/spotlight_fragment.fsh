#version 150

in vec4 vertexColor;
in vec2 texCoord0;
in vec2 lightCoord;

uniform sampler2D Sampler0; // 可选纹理
uniform sampler2D Sampler2; // 光照贴图

out vec4 fragColor;

void main() {
    // 从光照贴图采样
    vec4 lightColor = texture(Sampler2, lightCoord);

    // 基础颜色 - 使用顶点颜色
    vec4 baseColor = vertexColor;

    // 边缘衰减 - 基于UV坐标，让光束中心更亮
    // 使用texCoord0的x作为径向距离 (因为UV映射到三角条带)
    float radialDist = abs(texCoord0.x - 0.5) * 2.0;
    float edgeFade = 1.0 - smoothstep(0.0, 0.7, radialDist);
    edgeFade = max(0.3, edgeFade);

    // 光束整体衰减 - 从底部到顶部
    float lengthFade = 1.0 - texCoord0.y * 0.7;

    // 最终颜色
    vec4 finalColor = baseColor * edgeFade * lengthFade * 1.5;

    // 应用光照 (让光束看起来自发光)
    // 忽略光照贴图的亮度，使用最大亮度
    float brightness = 1.0;
    finalColor.rgb *= brightness;

    // 透明度
    finalColor.a = baseColor.a * edgeFade * lengthFade;

    fragColor = finalColor;
}