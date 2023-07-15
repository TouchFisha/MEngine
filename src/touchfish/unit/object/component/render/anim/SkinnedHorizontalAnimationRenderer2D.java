package touchfish.unit.object.component.render.anim;

import touchfish.unit.math.math;
import touchfish.unit.object.assets.Material;
import touchfish.unit.system.thread.RenderThread;

//public class SkinnedHorizontalAnimationRenderer2D extends AnimationRenderer2D {
//
//    public int leftAnimId = 0;
//    public int rightAnimId = 1;
//
//    @Override
//    public Material setMaterial(Material material) {
//        super.setMaterial(material);
//        addAnimData(
//                new AnimationGroupData("Left", 4, 0.8f),
//                new AnimationGroupData("Right", 4, 0.8f)
//        );
//        return material;
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