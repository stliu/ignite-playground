package im.baas.sandbox.ignite.query;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.query.ContinuousQuery;
import org.apache.ignite.cache.query.QueryCursor;
import org.apache.ignite.cache.query.ScanQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.cache.Cache;
import javax.cache.configuration.Factory;
import javax.cache.event.CacheEntryEvent;
import javax.cache.event.CacheEntryEventFilter;
import javax.cache.event.CacheEntryListenerException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

/**
 * Created by stliu on 8/19/16.
 */
@RestController
@RequestMapping(path = "/query")
public class QueryController {
    private final Ignite ignite;
    private final IgniteCache<Long, Person> cache;

    @Autowired
    public QueryController(Ignite ignite) {
        this.ignite = ignite;
        cache = ignite.getOrCreateCache("mycache");
//        Executors.newSingleThreadExecutor().submit(new Callable<Object>() {
//            @Override
//            public Object call() throws Exception {
                // Create new continuous query.
                ContinuousQuery<Long, Person> qry = new ContinuousQuery<>();

                // Initial iteration query will return all persons with salary above 1000.
                qry.setInitialQuery(new ScanQuery<>((id, p) -> p.getSalary() > 1000));


                // Callback that is called locally when update notifications are received.
                // It simply prints out information about all created persons.
                qry.setLocalListener((evts) -> {
                    for (CacheEntryEvent<? extends Long, ? extends Person> e : evts) {
                        Person p = e.getValue();

                        System.out.println(p.getFirstName() + " " + p.getLastName() + "'s salary is " + p.getSalary());
                    }
                });

                // Continuous listener will be notified for persons with salary above 1000.
                qry.setRemoteFilterFactory((Factory<CacheEntryEventFilter<Long, Person>>) () -> event -> event.getValue().getSalary() > 1000);

                // Execute query and get cursor that iterates through initial data.
                QueryCursor<Cache.Entry<Long, Person>> cur = cache.query(qry);
//                return null;
//            }
//        });
    }

    @RequestMapping(method = RequestMethod.POST)
    public void create(@RequestBody Person person) {
        cache.put(person.getId(), person);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Person getById(@PathVariable("id") long id) {
        return cache.get(id);
    }
}
