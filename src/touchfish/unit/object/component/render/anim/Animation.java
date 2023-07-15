package touchfish.unit.object.component.render.anim;


import touchfish.unit.object.component.render.TextureRenderer;
import touchfish.unit.system.event.Input;
import touchfish.unit.system.thread.RenderThread;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Animation extends TextureRenderer {

    private final Map<Integer, AnimationGroupData> groupDataMap;
    private final List<AnimationGroupData> groupDataList;
    public Image subImage;
    /**
     * 当前Y方向区块索引
     */
    private int currentAnimId = -1;
    public AnimationGroupData currentAnimation;
    
    public Animation() {
        super();
        groupDataMap = new HashMap<>();
        groupDataList = new ArrayList<>();
    }

    public void ReloadAnimById(int animId) {
        if (animId < groupDataList.size() && currentAnimId != animId) {
            currentAnimId = animId;
            currentAnimation = groupDataList.get(animId);
            currentAnimation.reload();
            ReloadSubImage(0);
        }
    }

    public void ReloadAnimByKeyCode(int keyCode) {
        ReloadAnimById(GetAnimId(keyCode));
    }

    public void ReloadSubImage(int indexer) {
        if (indexer < currentAnimation.count && indexer != currentAnimation.indexer) {
            currentAnimation.indexer = indexer;
            subImage = currentAnimation.subImage(material.mainTexture.getBufferedImage());
        }
    }

    public void ReloadSubImage(int animId, int indexer) {
        ReloadAnimById(animId);
        ReloadSubImage(indexer);
    }

    public void Next(int animId, int dir) {
        ReloadAnimById(animId);
        ReloadSubImage(currentAnimation.getNextIndexer(dir));
    }

    public void Next(int animId, float time) {
        ReloadAnimById(animId);
        ReloadSubImage(currentAnimation.getNextIndexer(time));
    }

    public void Auto(float speed) {
        for (AnimationGroupData animationGroupData : groupDataList) {
            if (Input.getKey(animationGroupData.keyCode)){
                Next(animationGroupData.index, speed * RenderThread.ftime);
                break;
            }
        }
    }

    public int GetAnimId(int keyCode) {
        return groupDataList.indexOf(groupDataMap.get(keyCode));
    }

    public void ClearAnimData() {
        groupDataMap.clear();
        groupDataList.clear();
    }

    private void AddAnimData(AnimationGroupData data) {
        if (!groupDataMap.containsKey(data.keyCode)) {
            groupDataMap.put(data.keyCode,data);
            groupDataList.add(data);
        } else {
            System.err.println("Add Same KeyCode AnimationGroupData In Animation Renderer.");
        }
    }

    public void AddAnimData(AnimationGroupData ... initDatas) {
        for (AnimationGroupData data : AnimationGroupData.createDatas(material, initDatas)) {
            AddAnimData(data);
        }
        if (initDatas.length > 0) {
            ReloadAnimById(0);
        }
    }

    public void RemoveAnimData(int keyCode) {
        if (groupDataMap.containsKey(keyCode)) {
            groupDataList.remove(groupDataMap.remove(keyCode));
        } else {
            System.err.println("Remove Failed. Not Found KeyCode " + keyCode + " In Animation Renderer.");
        }
    }

    @Override
    public void Render() {
        graphics.drawImage(
                subImage, positionPixel.x, positionPixel.y, scalePixel.x, scalePixel.y,null);
    }

}

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