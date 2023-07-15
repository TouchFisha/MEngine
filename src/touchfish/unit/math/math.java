package touchfish.unit.math;

import java.util.Collection;

public final class math {

    public static final float Rad2Deg = 57.29578F;
    public static final float Deg2Rad = 0.017453292F;

    public static boolean3 boolean3(){
        return boolean3.zero();
    }

    public static boolean2 boolean2(){
        return boolean2.zero();
    }

    public static float2 float2(){
        return float2.zero();
    }

    public static float3 float3(){
        return float3.zero();
    }

    public static float4 float4(){
        return float4.zero();
    }

    public static quaternion quaternion(){
        return quaternion.identity();
    }

    public static float4x4 float4x4(){
        return float4x4.zero();
    }

    public static int2 int2(){
        return int2.zero();
    }

    public static int3 int3(){
        return int3.zero();
    }

    public static int4 int4(){
        return int4.zero();
    }

    public static boolean2 boolean2(boolean x, boolean y) {
        return new boolean2(x,y);
    }

    public static boolean3 boolean3(boolean x, boolean y, boolean z) {
        return new boolean3(x,y,z);
    }

    public static float2 float2(float x, float y) {
        return new float2(x,y);
    }

    public static float3 float3(float x, float y, float z) {
        return new float3(x,y,z);
    }

    public static float3x3 float3x3(float3 c0, float3 c1, float3 c2) {
        return new float3x3(c0, c1, c2);
    }


    public static float4 float4(float x, float y, float z, float w) {
        return new float4(x,y,z,w);
    }

    public static quaternion quaternion(float x,float y,float z,float w) {
        return new quaternion(x,y,z,w);
    }

    public static float4x4 float4x4(float4 c0, float4 c1, float4 c2, float4 c3) {
        return new float4x4(c0, c1, c2, c3);
    }

    public static int2 int2(int x, int y) {
        return new int2(x,y);
    }

    public static int3 int3(int x, int y, int z) {
        return new int3(x,y,z);
    }

    public static int4 int4(int x, int y, int z, int w) {
        return new int4(x,y,z,w);
    }

    public static boolean2 boolean2(boolean v) {
        return new boolean2(v,v);
    }

    public static boolean3 boolean3(boolean v) {
        return new boolean3(v,v,v);
    }

    public static float2 float2(float v) {
        return new float2(v,v);
    }

    public static float3 float3(float v) {
        return new float3(v,v,v);
    }

    public static float4 float4(float v) {
        return new float4(v,v,v,v);
    }

    public static float4x4 float4x4(float v) {
        return new float4x4(float4(v), float4(v), float4(v), float4(v));
    }

    public static int2 int2(int v) {
        return new int2(v,v);
    }

    public static int3 int3(int v) {
        return new int3(v,v,v);
    }

    public static int4 int4(int v) {
        return new int4(v,v,v,v);
    }

    public static quaternion quaternion(float3 euler) {
        return quaternion(euler.x,euler.y,euler.z);
    }

    public static quaternion quaternion(float4 v) {
        return quaternion(v.x,v.y,v.z,v.w);
    }

    public static quaternion quaternion(float x, float y, float z) {
        return angle2quaternion(x,y,z);
    }

    public static quaternion quaternion(float3 axis, float angle) {
        return angleAxis(axis, angle);
    }

    public static quaternion angleAxis(float3 axis, float angle) {
        float halfAngle = 0.5f * radians(angle);
        float sina = sin(halfAngle);
        float cosa = cos(halfAngle);
        float3 v = mul(axis, sina);
        return quaternion(v.x,v.y,v.z,cosa);
    }

    public static float3 euler(quaternion quaternion) {
        return euler(quaternion.x,quaternion.y,quaternion.z,quaternion.w);
    }

    public static float3 euler(float x, float y, float z, float w) {
        float3 res = float3.zero();

        // roll (x-axis rotation)
        float sinr_cosp = 2 * (w * x + y * z);
        float cosr_cosp = 1 - 2 * (x * x + y * y);
        res.x = (float) Math.atan2(sinr_cosp, cosr_cosp);

        // pitch (y-axis rotation)
        float sinp = 2 * (w * y - z * x);
        if (Math.abs(sinp) >= 1)
            res.y = (float) Math.copySign(Math.PI / 2, sinp); // use 90 degrees if out of range
        else
            res.y = (float) Math.asin(sinp);

        // yaw (z-axis rotation)
        float siny_cosp = 2 * (w * z + x * y);
        float cosy_cosp = 1 - 2 * (y * y + z * z);
        res.z = (float) Math.atan2(siny_cosp, cosy_cosp);

        return res;
    }

    public static float3x3 matrix3x3(quaternion quaternion) {
        return matrix3x3(quaternion.x,quaternion.y,quaternion.z,quaternion.w);
    }

    public static float3x3 matrix3x3(float x, float y, float z, float w) {
        float3x3 rotationMatrix = float3x3.zero();
        // First row
        rotationMatrix.c0.x = 1 - 2 * (y * y + z * z);
        rotationMatrix.c0.y = 2 * (x * y - w * z);
        rotationMatrix.c0.z = 2 * (x * z + w * y);
        // Second row
        rotationMatrix.c1.x = 2 * (x * y + w * z);
        rotationMatrix.c1.y = 1 - 2 * (x * x + z * z);
        rotationMatrix.c1.z = 2 * (y * z - w * x);
        // Third row
        rotationMatrix.c2.x = 2 * (x * z - w * y);
        rotationMatrix.c2.y = 2 * (y * z + w * x);
        rotationMatrix.c2.z = 1 - 2 * (x * x + y * y);
        return rotationMatrix;
    }

    public static float2 project(float2 a, float2 b) {
        return mul(b, div(dot(a, b), dot(b, b)));
    }

    public static float3 project(float3 a, float3 b) {
        return mul(b, div(dot(a, b), dot(b, b)));
    }

    public static float4 project(float4 a, float4 b) {
        return mul(b, div(dot(a, b), dot(b, b)));
    }

    public static int max(int x, int y) {
        return Math.max(x, y);
    }

    public static int2 max(int2 x, int2 y)
    {
        return int2(max(x.x, y.x), max(x.y, y.y));
    }

    public static int3 max(int3 x, int3 y)
    {
        return int3(max(x.x, y.x), max(x.y, y.y), max(x.z, y.z));
    }

    public static int4 max(int4 x, int4 y)
    {
        return int4(max(x.x, y.x), max(x.y, y.y), max(x.z, y.z), max(x.w, y.w));
    }

    public static float max(float x, float y)
    {
        if (!Float.isNaN(y) && x <= y)
        {
            return y;
        }
        return x;
    }
    public static float max(float ... values)
    {
        float res = Float.MIN_VALUE;
        for (float value : values) {
            if (res < value) {
                res = value;
            }
        }
        return res;
    }
    public static float max(Collection<Float> values)
    {
        float res = Float.MIN_VALUE;
        for (float value : values) {
            if (res < value) {
                res = value;
            }
        }
        return res;
    }
    public static float2 max(float2 x, float2 y)
    {
        return float2(max(x.x, y.x), max(x.y, y.y));
    }

    public static float3 max(float3 x, float3 y)
    {
        return float3(max(x.x, y.x), max(x.y, y.y), max(x.z, y.z));
    }

    public static float4 max(float4 x, float4 y)
    {
        return float4(max(x.x, y.x), max(x.y, y.y), max(x.z, y.z), max(x.w, y.w));
    }

    public static int min(int x, int y) {
        return Math.min(x, y);
    }

    public static int2 min(int2 x, int2 y)
    {
        return int2(min(x.x, y.x), min(x.y, y.y));
    }

    public static int3 min(int3 x, int3 y)
    {
        return int3(min(x.x, y.x), min(x.y, y.y), min(x.z, y.z));
    }

    public static int4 min(int4 x, int4 y)
    {
        return int4(min(x.x, y.x), min(x.y, y.y), min(x.z, y.z), min(x.w, y.w));
    }

    public static float min(float x, float y)
    {
        if (!Float.isNaN(y) && x >= y)
        {
            return y;
        }
        return x;
    }

    public static float2 min(float2 x, float2 y)
    {
        return float2(min(x.x, y.x), min(x.y, y.y));
    }

    public static float3 min(float3 x, float3 y)
    {
        return float3(min(x.x, y.x), min(x.y, y.y), min(x.z, y.z));
    }

    public static float4 min(float4 x, float4 y)
    {
        return float4(min(x.x, y.x), min(x.y, y.y), min(x.z, y.z), min(x.w, y.w));
    }

    public static boolean2 between(float2 value, float2 min, float2 max)
    {
        return new boolean2(value.x >= min.x && value.x <= max.x,
                value.y >= min.y && value.y <= max.y);
    }

    public static int abs(int x)
    {
        return max(-x, x);
    }

    public static int2 abs(int2 x)
    {
        return max(inverse(x), x);
    }

    public static int3 abs(int3 x)
    {
        return max(inverse(x), x);
    }

    public static int4 abs(int4 x)
    {
        return max(inverse(x), x);
    }

    public static float abs(float x)
    {
        return Math.abs(x);
    }

    public static float2 abs(float2 x)
    {
        return float2(abs(x.x),abs(x.y));
    }

    public static float3 abs(float3 x)
    {
        return float3(abs(x.x),abs(x.y),abs(x.z));
    }

    public static float4 abs(float4 x)
    {
        return float4(abs(x.x),abs(x.y),abs(x.z),abs(x.w));
    }

    public static int2 inverse(int2 x)
    {
        return int2(-x.x,-x.y);
    }

    public static int3 inverse(int3 x)
    {
        return int3(-x.x,-x.y,-x.z);
    }

    public static int4 inverse(int4 x)
    {
        return int4(-x.x,-x.y,-x.z,-x.w);
    }

    public static float2 inverse(float2 x)
    {
        return float2(-x.x,-x.y);
    }

    public static float3 inverse(float3 x)
    {
        return float3(-x.x,-x.y,-x.z);
    }

    public static float4 inverse(float4 x)
    {
        return float4(-x.x,-x.y,-x.z,-x.w);
    }

    public static quaternion inverse(quaternion x) {
        float norm = x.w * x.w + x.x * x.x + x.y * x.y + x.z * x.z;
        if (norm == 0) {
            return null;
        }
        float invNorm = 1.0f / norm;
        return quaternion(-x.x * invNorm, -x.y * invNorm, -x.z * invNorm, x.w * invNorm);
    }

    public static float distance(float3 x, float3 y) {
        return length(sub(y,x));
    }

    public static float distance(float2 x, float2 y) {
        return length(sub(y,x));
    }

    public static float distance(float x, float y) {
        return abs(y - x);
    }

    public static float2 add(float2 x,float2 y)
    {
        return float2(x.x+y.x,x.y+y.y);
    }

    public static float3 add(float3 x,float3 y)
    {
        return float3(x.x+y.x,x.y+y.y,x.z+y.z);
    }

    public static float4 add(float4 x,float4 y)
    {
        return float4(x.x+y.x,x.y+y.y,x.z+y.z,x.w+y.w);
    }

    public static int2 add(int2 x,int2 y)
    {
        return int2(x.x+y.x,x.y+y.y);
    }

    public static int3 add(int3 x,int3 y)
    {
        return int3(x.x+y.x,x.y+y.y,x.z+y.z);
    }

    public static int4 add(int4 x,int4 y)
    {
        return int4(x.x+y.x,x.y+y.y,x.z+y.z,x.w+y.w);
    }

    public static float2 add(float2 x,float y)
    {
        return float2(x.x+y,x.y+y);
    }

    public static float3 add(float3 x,float y)
    {
        return float3(x.x+y,x.y+y,x.z+y);
    }

    public static float4 add(float4 x,float y)
    {
        return float4(x.x+y,x.y+y,x.z+y,x.w+y);
    }

    public static float2 sub(float2 x,float2 y)
    {
        return float2(x.x-y.x,x.y-y.y);
    }

    public static float3 sub(float3 x,float3 y)
    {
        return float3(x.x-y.x,x.y-y.y,x.z-y.z);
    }

    public static float4 sub(float4 x,float4 y)
    {
        return float4(x.x-y.x,x.y-y.y,x.z-y.z,x.w-y.w);
    }

    public static int2 sub(int2 x,int2 y)
    {
        return int2(x.x-y.x,x.y-y.y);
    }

    public static int3 sub(int3 x,int3 y) {
        return int3(x.x-y.x,x.y-y.y,x.z-y.z);
    }

    public static int4 sub(int4 x,int4 y)
    {
        return int4(x.x-y.x,x.y-y.y,x.z-y.z,x.w-y.w);
    }

    public static float2 sub(float2 x,float y)
    {
        return float2(x.x-y,x.y-y);
    }

    public static float3 sub(float3 x,float y)
    {
        return float3(x.x-y,x.y-y,x.z-y);
    }

    public static float4 sub(float4 x,float y)
    {
        return float4(x.x-y,x.y-y,x.z-y,x.w-y);
    }

    public static float2 mul(float2 x,float2 y)
    {
        return float2(x.x*y.x,x.y*y.y);
    }

    public static float3 mul(float3 x,float3 y)
    {
        return float3(x.x*y.x,x.y*y.y,x.z*y.z);
    }

    public static float4 mul(float4 x,float4 y)
    {
        return float4(x.x*y.x,x.y*y.y,x.z*y.z,x.w*y.w);
    }

    public static int2 mul(int2 x,int2 y)
    {
        return int2(x.x*y.x,x.y*y.y);
    }

    public static int3 mul(int3 x,int3 y)
    {
        return int3(x.x*y.x,x.y*y.y,x.z*y.z);
    }

    public static int4 mul(int4 x,int4 y)
    {
        return int4(x.x*y.x,x.y*y.y,x.z*y.z,x.w*y.w);
    }

    public static float2 mul(float2 x,float y)
    {
        return float2(x.x*y,x.y*y);
    }

    public static float3 mul(float3 x,float y)
    {
        return float3(x.x*y,x.y*y,x.z*y);
    }

    public static float4 mul(float4 x,float y)
    {
        return float4(x.x*y,x.y*y,x.z*y,x.w*y);
    }

    public static quaternion mul(quaternion x, quaternion y) {
        float nw = x.w * y.w - x.x * y.x - x.y * y.y - x.z * y.z;
        float nx = x.w * y.x + x.x * y.w + x.y * y.z - x.z * y.y;
        float ny = x.w * y.y - x.x * y.z + x.y * y.w + x.z * y.x;
        float nz = x.w * y.z + x.x * y.y - x.y * y.x + x.z * y.w;
        return new quaternion(nx, ny, nz, nw);
    }

    public static float3 mul(quaternion q, float3 v)
    {
        return rotate(q, v);
    }

    public static float3 rotate(quaternion q, float3 v)
    {
        float3 q_xyz = float3(q.x,q.y,q.z);
        float3 t = mul(cross(q_xyz, v), 2f);
        return add(add(v , mul(t, q.w)) , cross(q_xyz, t));
    }
    public static float3 cross(float3 x, float3 y) {
        float3 m = sub(mul(x, float3(y.y,y.z,y.x)), mul(float3(x.y,x.z,x.x), y));
        return float3(m.y,m.z,m.x);
    }
    public static float2 div(float2 x,float2 y)
    {
        return float2(x.x/y.x,x.y/y.y);
    }
    public static float div(float x,float y)
    {
        return x/y;
    }
    public static float3 div(float3 x,float3 y)
    {
        return float3(x.x/y.x,x.y/y.y,x.z/y.z);
    }

    public static float4 div(float4 x,float4 y)
    {
        return float4(x.x/y.x,x.y/y.y,x.z/y.z,x.w/y.w);
    }

    public static int2 div(int2 x,int2 y)
    {
        return int2(x.x/y.x,x.y/y.y);
    }

    public static int3 div(int3 x,int3 y)
    {
        return int3(x.x/y.x,x.y/y.y,x.z/y.z);
    }

    public static int4 div(int4 x,int4 y)
    {
        return int4(x.x/y.x,x.y/y.y,x.z/y.z,x.w/y.w);
    }

    public static float2 div(float2 x,float y)
    {
        return float2(x.x/y,x.y/y);
    }

    public static float3 div(float3 x,float y)
    {
        return float3(x.x/y,x.y/y,x.z/y);
    }

    public static float4 div(float4 x,float y)
    {
        return float4(x.x/y,x.y/y,x.z/y,x.w/y);
    }

    public static float sqrt(float x){
        return (float)(Math.sqrt(x));
    }

    public static float rsqrt(float x)
    {
        return 1f / sqrt(x);
    }

    public static int dot(int x, int y)
    {
        return x * y;
    }

    public static int dot(int2 x, int2 y)
    {
        return x.x * y.x + x.y * y.y;
    }

    public static int dot(int3 x, int3 y)
    {
        return x.x * y.x + x.y * y.y + x.z * y.z;
    }

    public static float dot(float x, float y)
    {
        return x * y;
    }

    public static float dot(float2 x, float2 y)
    {
        return x.x * y.x + x.y * y.y;
    }

    public static float dot(float3 x, float3 y)
    {
        return x.x * y.x + x.y * y.y + x.z * y.z;
    }

    public static float dot(float4 x, float4 y)
    {
        return x.x * y.x + x.y * y.y + x.z * y.z + x.w * y.w;
    }

    public static float dot(quaternion x, quaternion y) {
        return x.w * y.w + x.x * y.x + x.y * y.y + x.z * y.z;
    }

    public static float2 normalize(float2 x)
    {
        return mul(x,rsqrt(dot(x, x)));
    }

    public static float3 normalize(float3 x)
    {
        return mul(x,rsqrt(dot(x, x)));
    }

    public static float4 normalize(float4 x)
    {
        return mul(x,rsqrt(dot(x, x)));
    }

    public static quaternion normalize(quaternion x) {
        double norm = Math.sqrt(x.w * x.w + x.x * x.x + x.y * x.y + x.z * x.z);
        if (norm > 0) {
            float invNorm = (float) (1.0f / norm);
            return quaternion(x.x * invNorm, x.y * invNorm, x.z * invNorm, x.w * invNorm);
        } else {
            return quaternion(0, 0, 0, 1);
        }
    }

    public static float length(float2 x)
    {
        return sqrt(dot(x, x));
    }

    public static float length(float3 x)
    {
        return sqrt(dot(x, x));
    }

    public static float length(float4 x)
    {
        return sqrt(dot(x, x));
    }

    public static float lengthsq(float2 x)
    {
        return dot(x, x);
    }

    public static float lengthsq(float3 x)
    {
        return dot(x, x);
    }

    public static float lengthsq(float4 x)
    {
        return dot(x, x);
    }

    public static float lerp(float a,float b, float t){
        //return (1.0f - t) * a + t * b;
        return a + (b - a) * t;
    }

    public static float2 lerp(float2 a, float2 b, float t)
    {
        return float2(lerp(a.x,b.x,t),lerp(a.y,b.y,t));
    }

    public static float3 lerp(float3 a, float3 b, float t) {
        return float3(lerp(a.x,b.x,t),lerp(a.y,b.y,t),lerp(a.z,b.z,t));
    }

    public static float4 lerp(float4 a, float4 b, float t) {
        return float4(lerp(a.x,b.x,t),lerp(a.y,b.y,t),lerp(a.z,b.z,t),lerp(a.w,b.w,t));
    }

    public static quaternion lerp(quaternion a, quaternion b, float t) {
        float dot = dot(a,b);
        float blendI = 1.0f - t;
        if (dot < 0) {
            blendI = -blendI;
        }
        float w = blendI * a.w + t * b.w;
        float x = blendI * a.x + t * b.x;
        float y = blendI * a.y + t * b.y;
        float z = blendI * a.z + t * b.z;
        return normalize(quaternion(x, y, z, w));
    }

    public static float atan2(float y, float x) {
        return (float) Math.atan2(y,x);
    }

    public static quaternion conjugate(quaternion q) {
        return quaternion(mul(float4(q.x,q.y,q.z,0), float4(-1f, -1f, -1f, 1f)));
    }

    public static float sin(float v) {
        return (float)Math.sin(v);
    }

    public static float cos(float v) {
        return (float)Math.cos(v);
    }

    public static float3 sin(float3 x) {
        return float3(sin(x.x), sin(x.y), sin(x.z));
    }

    public static float3 cos(float3 x) {
        return float3(cos(x.x), cos(x.y), cos(x.z));
    }

    public static quaternion euler2quaternionXYZ(float3 xyz)
    {
        return euler2quaternion(xyz, float4(-1f, 1f, -1f, 1f));
    }

    public static quaternion euler2quaternionXZY(float3 xyz)
    {
        return euler2quaternion(xyz, float4(1f, 1f, -1f, -1f));
    }

    public static quaternion euler2quaternionYXZ(float3 xyz)
    {
        return euler2quaternion(xyz, float4(-1f, 1f, 1f, -1f));
    }

    public static quaternion euler2quaternionYZX(float3 xyz)
    {
        return euler2quaternion(xyz, float4(-1f, -1f, 1f, 1f));
    }

    public static quaternion euler2quaternionZXY(float3 xyz)
    {
        return euler2quaternion(xyz, float4(1f, -1f, -1f, 1f));
    }

    public static quaternion euler2quaternionZYX(float3 xyz)
    {
        return euler2quaternion(xyz, float4(1f, -1f, 1f, -1f));
    }

    public static quaternion euler2quaternionXYZ(float x, float y, float z)
    {
        return euler2quaternionXYZ(float3(x, y, z));
    }

    public static quaternion euler2quaternionXZY(float x, float y, float z)
    {
        return euler2quaternionXZY(float3(x, y, z));
    }

    public static quaternion euler2quaternionYXZ(float x, float y, float z)
    {
        return euler2quaternionYXZ(float3(x, y, z));
    }

    public static quaternion euler2quaternionYZX(float x, float y, float z)
    {
        return euler2quaternionYZX(float3(x, y, z));
    }

    public static quaternion euler2quaternionZXY(float x, float y, float z)
    {
        return euler2quaternionZXY(float3(x, y, z));
    }

    public static quaternion euler2quaternionZYX(float x, float y, float z)
    {
        return euler2quaternionZYX(float3(x, y, z));
    }

    public static quaternion euler2quaternion(float3 xyz, float4 order) {
        float3 a = mul(xyz, 0.5f);
        float3 s = sin(a);
        float3 c = cos(a);
        return
                quaternion(
                        add(
                                mul(mul(float4(s.x,s.y,s.z,c.x), float4(c.y,c.x,c.x,c.y)), float4(c.z,c.z,c.y,c.z)),
                                mul(mul(mul(float4(s.y,s.x,s.x,s.y), float4(s.z,s.z,s.y,s.z)), float4(c.x,c.y,c.z,s.x)), order)
                        )
                );
    }

    public static quaternion angle2quaternion(float3 xyz)
    {
        return euler2quaternionZXY(xyz);
    }

    public static quaternion angle2quaternion(float x, float y, float z)
    {
        return angle2quaternion(float3(radians(x), radians(y), radians(z)));
    }

    public static float radians(float x)
    {
        return x * 0.017453292f;
    }

    public static float2 radians(float2 x)
    {
        return mul(x,0.017453292f);
    }

    public static float3 radians(float3 x)
    {
        return mul(x,0.017453292f);
    }

    public static float4 radians(float4 x)
    {
        return mul(x,0.017453292f);
    }

    public static quaternion rotateX(float angle)
    {
        float halfAngle = 0.5f * radians(angle);
        float sina = sin(halfAngle);
        float cosa = cos(halfAngle);
        return quaternion(sina, 0f, 0f, cosa);
    }

    public static quaternion rotateY(float angle)
    {
        float halfAngle = 0.5f * radians(angle);
        float sina = sin(halfAngle);
        float cosa = cos(halfAngle);
        return quaternion(0f, sina, 0f, cosa);
    }

    public static quaternion rotateZ(float angle)
    {
        float halfAngle = 0.5f * radians(angle);
        float sina = sin(halfAngle);
        float cosa = cos(halfAngle);
        return quaternion(0f, 0f, sina, cosa);
    }


    public static boolean2 greater(float2 x, float2 y) {
        return boolean2(x.x > y.x, x.y > y.y);
    }

    public static boolean3 greater(float3 x, float3 y) {
        return boolean3(x.x > y.x, x.y > y.y, x.z > y.z);
    }

    public static boolean3 equal(float3 x, float3 y) {
        return boolean3(x.x == y.x, x.y == y.y, x.z == y.z);
    }

    public static boolean2 equal(float2 x, float2 y) {
        return boolean2(x.x == y.x, x.y == y.y);
    }

    public static boolean3 less(float3 x, float3 y) {
        return boolean3(x.x < y.x, x.y < y.y, x.z < y.z);
    }

    public static boolean2 less(float2 x, float2 y) {
        return boolean2(x.x < y.x, x.y < y.y);
    }

    public static boolean all(boolean2 x) {
        return x.x && x.y;
    }

    public static boolean all(boolean3 x) {
        return x.x && x.y && x.z;
    }

    public static boolean any(boolean2 x) {
        return x.x || x.y;
    }

    public static boolean any(boolean3 x) {
        return x.x || x.y || x.z;
    }

    public static int clamp(int x, int a, int b)
    {
        return math.max(a, math.min(b, x));
    }

    public static int2 clamp(int2 x, int2 a, int2 b)
    {
        return math.max(a, math.min(b, x));
    }

    public static int3 clamp(int3 x, int3 a, int3 b)
    {
        return math.max(a, math.min(b, x));
    }

    public static int4 clamp(int4 x, int4 a, int4 b)
    {
        return math.max(a, math.min(b, x));
    }

    public static float clamp(float x, float a, float b)
    {
        return math.max(a, math.min(b, x));
    }

    public static float2 clamp(float2 x, float2 a, float2 b)
    {
        return math.max(a, math.min(b, x));
    }

    public static float3 clamp(float3 x, float3 a, float3 b)
    {
        return math.max(a, math.min(b, x));
    }

    public static float4 clamp(float4 x, float4 a, float4 b)
    {
        return math.max(a, math.min(b, x));
    }
}
