package im.baas.sandbox.ignite;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteException;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;


/**
 * 监听Ignite的生命周期, 可以在一个节点启动前或者停止前做一些事情
 */
@Slf4j
public class IgniteLifecycleListener implements LifecycleBean {
    @Override
    public void onLifecycleEvent(LifecycleEventType evt) throws IgniteException {
        switch (evt){
            case AFTER_NODE_START:
                log.info("ignite node started");
                break;
            case AFTER_NODE_STOP:
                log.info("ignite node stopped");
                break;
            case BEFORE_NODE_START:
                log.info("ignite node is about to start");
                break;
            case BEFORE_NODE_STOP:
                log.info("ignite node is about to stop");
                break;
        }
    }
}
