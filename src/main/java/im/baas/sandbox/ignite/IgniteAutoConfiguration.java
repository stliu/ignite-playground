package im.baas.sandbox.ignite;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.logger.slf4j.Slf4jLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author stliu @ apache.org
 */
@Configuration
public class IgniteAutoConfiguration {
    @Bean
    public IgniteConfiguration igniteConfiguration() {
        IgniteConfiguration configuration = new IgniteConfiguration();
        configuration.setGridLogger(new Slf4jLogger());

        return configuration;
//        new IgniteConfiguration(
//                discoSpi: new TcpDiscoverySpi(
//                ipFinder: new TcpDiscoveryMulticastIpFinder(
//                addresses: [
//        "127.0.0.1:47500",
//                "127.0.0.1:47501",
//                "127.0.0.1:47502",
//                "127.0.0.1:47503",
//                                ]
//                        )
//                ),
//        lifecycleBeans: new CacheLoader(),
//                peerClassLoadingEnabled: true,
//                cacheCfg: [
//        new CacheConfiguration(
//                name: "person",
//                backups: 0,
//                cacheMode: CacheMode.PARTITIONED
////                                atomicityMode: CacheAtomicityMode.TRANSACTIONAL,
////                                cacheStoreFactory: FactoryBuilder.factoryOf(PersonCacheAdapter),
////                                writeThrough: true,
////                                readThrough: true
//                        )
//                ]
//        )
    }
    @Bean(destroyMethod = "close")
    public IgniteSpringBean ignite() {
        IgniteSpringBean springBean = new IgniteSpringBean();
        springBean.setConfiguration(igniteConfiguration());
        return springBean;
    }
}
