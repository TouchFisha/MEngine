package touchfish.unit.object.component.render.anim;


import touchfish.unit.math.math;
import touchfish.unit.object.assets.Material;
import touchfish.unit.system.event.Input;
import touchfish.unit.system.event.KeyPressType;
import touchfish.unit.system.thread.RenderThread;

//public class SkinnedAnimationRenderer2D extends AnimationRenderer2D {
//
//    public int downAnimId = 0;
//    public int leftAnimId = 1;
//    public int rightAnimId = 2;
//    public int upAnimId = 3;
//    public KeyCode downAnimKeyCode = KeyCode.S;
//    public KeyCode leftAnimKeyCode = KeyCode.A;
//    public KeyCode rightAnimKeyCode = KeyCode.D;
//    public KeyCode upAnimKeyCode = KeyCode.W;
//
//    @Override
//    public Material setMaterial(Material material) {
//        super.setMaterial(material);
//        addAnimData(
//                new AnimationGroupData(downAnimKeyCode, 4, 0.8f),
//                new AnimationGroupData(leftAnimKeyCode, 4, 0.8f),
//                new AnimationGroupData(rightAnimKeyCode, 4, 0.8f),
//                new AnimationGroupData(upAnimKeyCode, 4, 0.8f)
//        );
//        return material;
//    }
//
//    public void Auto(KeyCode keyCode, float speed, boolean moveBack) {
//
//
//        nextIndexer(speed > 0 ? moveBack ? leftAnimId : rightAnimId : moveBack ? rightAnimId : leftAnimId, math.abs(speed * RenderThread.utime) * (moveBack ? -1 : 1));
//    }
//
//}

/*

    public void HorizontalControlStep(int dir) {

        currentAnimId =
                dir == 0 ?
                        currentAnimId :
                        moveBack ?
                                dir > 0 ?
                                        0 :
                                        1 :
                                dir > 0 ?
                                        1 :
                                        0;

        AnimationGroupData data = groupDataList.get(currentAnimId);

        int indexer = currentAnimation.indexer;
        currentAnimIndexer =
                dir == 0 ?
                        0 :
                        !moveBack || dir > 0 ?
                                indexer + 1 < data.count ?
                                        indexer + 1 :
                                        0 :
                                indexer > 0 ?
                                        indexer - 1 :
                                        data.count - 1;
    }

 */