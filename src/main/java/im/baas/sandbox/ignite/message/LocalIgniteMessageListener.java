package im.baas.sandbox.ignite.message;

import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.lang.IgniteBiPredicate;

import java.util.UUID;


@Slf4j
public class LocalIgniteMessageListener implements IgniteBiPredicate<UUID, Object> {
    @Override
    public boolean apply(UUID uuid, Object o) {
        log.info("got message [{}] from node {}", o, uuid);
        return true;
    }
}
