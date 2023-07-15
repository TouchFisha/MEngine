package touchfish.unit.object.component.render.anim;


import touchfish.unit.math.math;
import touchfish.unit.object.BaseObject;
import touchfish.unit.object.assets.Material;

import java.awt.image.BufferedImage;
/**
 * 每一行是一组动画
 * 这个类是一组动画
 */
public class AnimationGroupData {
    /**
     * 这组动画在第几行
     */
    public int index;
    /**
     * 当前帧
     */
    public int indexer;
    /**
     * 这组动画有几帧
     */
    public int count;
    /**
     * 每帧动画多少像素宽度
     */
    public int width;
    /**
     * 每帧动画多少像素高度
     */
    public int height;
    /**
     * 这组动画的起始像素高度
     */
    public int startHeight;
    /**
     * 动画所需总时间
     */
    public float time;
    /**
     * 每帧所需时间
     */
    public float tickTime;
    /**
     * 动画已运行时间
     */
    public float runtime;
    /**
     * 动画启动按键
     */
    public int keyCode;

    private AnimationGroupData(int keyCode, int index, int count, int width, int height, float time) {
        this.index = index;
        this.count = count;
        this.width = width;
        this.height = height;
        this.time = time;
        this.keyCode = keyCode;
        tickTime = time / count;
        startHeight = height * index;
        reload();
    }
    /**
     * 仅用于临时初始化
     */
    public AnimationGroupData(int keyCode, int count, float time) {
        this.count = count;
        this.time = time;
        this.keyCode = keyCode;
    }
    /**
     * 唯一的创建函数
     */
    public static AnimationGroupData[] createDatas(Material material, AnimationGroupData ... initDatas) {

        BufferedImage bufferedImage = material.mainTexture.getBufferedImage();

        AnimationGroupData[] res = new AnimationGroupData[initDatas.length];

        for (int i = 0, datasLength = initDatas.length; i < datasLength; i++) {
            AnimationGroupData init = initDatas[i];
            AnimationGroupData data = new AnimationGroupData(
                    init.keyCode,
                    i,
                    init.count,
                    bufferedImage.getWidth() / init.count,
                    bufferedImage.getHeight() / initDatas.length,
                    init.time
            );
            res[i] = data;
        }

        return res;
    }

    public BufferedImage subImage(BufferedImage src) {
        return src.getSubimage(indexer * width, startHeight, width, height);
    }

    public int getNextIndexer(int dir) {
        int indexer = this.indexer + dir;
        if (indexer < 0) {
            indexer = count - 1;
        }
        if (indexer >= count) {
            indexer = indexer % count;
        }
        return indexer;
    }

    public int getNextIndexer(float time) {
        runtime += math.abs(time);
        if (time == 0) {
            runtime = 0;
            return getNextIndexer(0);
        } else if (runtime >= tickTime) {
            runtime = 0;
            return getNextIndexer(time > 0 ? 1 : -1);
        }
        return indexer;
    }

    public void reload() {
        indexer = -1;
        runtime = 0;
    }

}