package touchfish.unit.action;

import touchfish.unit.inter.Allocator;
import touchfish.unit.inter.IDelegateInOut;
import touchfish.unit.inter.IDelegateOut;
import touchfish.unit.system.thread.RenderThread;

import java.util.ArrayList;
import java.util.List;

public class ActionInOut implements IDelegateInOut<Object[],Boolean> {
    public IDelegateInOut<Object[], Boolean> delegate;
    public float lastInvokeTime;
    private float coolTime;
    private boolean isZeroCoolTime;
    public boolean isOneShot;
    public boolean isStaticAction;
    public boolean isUseInvokeCheckAct;
    public boolean isIgnoreError;
    public boolean isAlwaysCool;
    private List<ActionInOut> bindCoolTimeActions = new ArrayList<>();
    public IDelegateOut<Boolean> invokeCheckAct;
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
        isZeroCoolTime = (coolTime = value) == 0;
    }
    public boolean invokeCheck()
    {
        return (isZeroCoolTime || RenderThread.ctime - lastInvokeTime > getCoolTime()) && (!isUseInvokeCheckAct || invokeCheckAct.invoke());
    }
    public void reloadCoolTime()
    {
        lastInvokeTime = RenderThread.ctime;
        for (ActionInOut action : bindCoolTimeActions) {
            action.setCurrentUnScaleCoolTime(getCoolTime());
        }
    }
    public boolean resultCheck(boolean res)
    {
        if (res || isIgnoreError) {
            if (!isZeroCoolTime) reloadCoolTime();
            return true;
        }
        else if (isAlwaysCool)
            reloadCoolTime();
        return false;
    }
    public void bindActionCoolTime(ActionInOut action, boolean bidirectional)
    {
        bindCoolTimeActions.add(action);
        if (bidirectional) action.bindCoolTimeActions.add(this);
    }
    public void unbindActionCoolTime(ActionInOut action){
        bindCoolTimeActions.remove(action);
    }

    @Override
    public Boolean invoke(Object... args) {
        return invokeCheck() && resultCheck(delegate.invoke(args));
    }

    public ActionInOut(IDelegateInOut<Object[], Boolean> delegate, float coolTime, boolean isOneShot, boolean isStaticAction, boolean isIgnoreError, boolean isAlwaysCool) {
        this.delegate = delegate;
        this.coolTime = coolTime;
        this.isZeroCoolTime = coolTime == 0;
        this.isOneShot = isOneShot;
        this.isStaticAction = isStaticAction;
        this.isUseInvokeCheckAct = false;
        this.isIgnoreError = isIgnoreError;
        this.isAlwaysCool = isAlwaysCool;
    }
    public ActionInOut(IDelegateInOut<Object[], Boolean> delegate, float coolTime, boolean isOneShot, boolean isStaticAction, boolean isIgnoreError, boolean isAlwaysCool, IDelegateOut<Boolean> check) {
        this.delegate = delegate;
        this.coolTime = coolTime;
        this.isZeroCoolTime = coolTime == 0;
        this.isOneShot = isOneShot;
        this.isStaticAction = isStaticAction;
        this.isUseInvokeCheckAct = true;
        this.invokeCheckAct = check;
        this.isIgnoreError = isIgnoreError;
        this.isAlwaysCool = isAlwaysCool;
    }
    public static ActionInOut createInvoke(IDelegateInOut<Object[], Boolean> delegate, float coolTime){
        ActionInOut res = create(delegate,coolTime,Allocator.TempJob);
        res.lastInvokeTime = RenderThread.ctime;
        return res;
    }
    public static ActionInOut create(IDelegateInOut<Object[], Boolean> delegate, float coolTime, Allocator allocator){
        ActionInOut res;
        switch (allocator) {
            case None:
                res = new ActionInOut(delegate,coolTime,false,false,false,false);
                break;
            case Temp:
                res = new ActionInOut(delegate,coolTime,true,false,true,true);
                break;
            case TempJob:
                res = new ActionInOut(delegate,coolTime,true,false,false,false);
                break;
            case Persistent:
                res = new ActionInOut(delegate,coolTime,false,true,true,true);
                break;
            default:
                res = null;
                break;
        }
        return res;
    }
}
