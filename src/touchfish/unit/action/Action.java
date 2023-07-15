package touchfish.unit.action;

import touchfish.unit.inter.Allocator;
import touchfish.unit.inter.IDelegate;
import touchfish.unit.inter.IDelegateOut;
import touchfish.unit.system.thread.RenderThread;

import java.util.ArrayList;
import java.util.List;

public class Action {
    public IDelegate delegate;
    public float lastInvokeTime;
    public float coolTime;
    public boolean isOneShot;
    private List<Action> bindCoolTimeActions = new ArrayList<>();
    public void setTargetCoolTimePercent(float value){
        lastInvokeTime = RenderThread.ctime - getCoolTime() * value;
    }
    public float getTargetCoolTimePercent()
    {
        float t = (RenderThread.ctime - lastInvokeTime) / getCoolTime();
        return t < 0 ? 0 : t > 1 ? 1 : t;
    }
    public float getCurrentUnScaleCoolTime()
    {
        float t = getCoolTime() - RenderThread.ctime + lastInvokeTime;
        return t < 0 ? 0 : t;
    }
    public void setCurrentUnScaleCoolTime(float value){
        lastInvokeTime = RenderThread.ctime - getCoolTime() + value;
    }
    public float getCoolTime() {
        return coolTime;
    }
    public void setCoolTime(float value) {
        coolTime = value;
    }
    public boolean invokeCheck()
    {
        return RenderThread.ctime - lastInvokeTime > getCoolTime();
    }
    public void reloadCoolTime()
    {
        lastInvokeTime = RenderThread.ctime;
        for (Action action : bindCoolTimeActions) {
            action.setCurrentUnScaleCoolTime(getCoolTime());
        }
    }
    public void bindActionCoolTime(Action action, boolean bidirectional)
    {
        bindCoolTimeActions.add(action);
        if (bidirectional) action.bindCoolTimeActions.add(this);
    }
    public void unbindActionCoolTime(Action action){
        bindCoolTimeActions.remove(action);
    }

    public boolean invoke() {
        if (invokeCheck()) {
            reloadCoolTime();
            delegate.invoke();
            return true;
        }
        else return false;
    }

    public Action(IDelegate delegate, float coolTime, boolean isOneShot) {
        this.delegate = delegate;
        this.coolTime = coolTime;
        this.isOneShot = isOneShot;
    }

    public static Action createInvoke(IDelegate delegate, float time){
        Action res = create(delegate,time,Allocator.TempJob);
        res.lastInvokeTime = RenderThread.ctime;
        return res;
    }

    public static Action create(IDelegate delegate, float coolTime, Allocator allocator){
        Action res;
        switch (allocator) {
            case None:
                res = new Action(delegate,coolTime,false);
                break;
            case Temp:
                res = new Action(delegate,coolTime,true);
                break;
            case TempJob:
                res = new Action(delegate,coolTime,true);
                break;
            case Persistent:
                res = new Action(delegate,coolTime,false);
                break;
            default:
                res = null;
                break;
        }
        return res;
    }
}
