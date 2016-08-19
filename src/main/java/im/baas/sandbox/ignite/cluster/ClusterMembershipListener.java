package im.baas.sandbox.ignite.cluster;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.cluster.ClusterGroup;
import org.apache.ignite.cluster.ClusterNode;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 使用Ignite的cluster 功能来确定集群中最早启动的node, 让这个node作为leader
 * <p>
 *
 * @see <a href="https://apacheignite.readme.io/docs/leader-election#oldest-node">Ignite leader election</a>
 */
@Component
@Slf4j
public class ClusterMembershipListener implements InitializingBean {
    private final Ignite ignite;

    @Autowired
    public ClusterMembershipListener(Ignite ignite) {
        this.ignite = ignite;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //这个应该是只有一个最早启动的节点的group
        ClusterGroup singleOldestNodeClusterGroup = ignite.cluster().forOldest();

        ClusterNode oldestNode = singleOldestNodeClusterGroup.node();
        log.info("leader node is {}", oldestNode);
    }
}
