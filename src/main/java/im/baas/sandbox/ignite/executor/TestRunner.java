package im.baas.sandbox.ignite.executor;

import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCompute;
import org.apache.ignite.lang.IgniteCallable;
import org.apache.ignite.lang.IgniteFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by stliu on 8/19/16.
 */
@Component
public class TestRunner implements CommandLineRunner {
    private final Ignite ignite;

    @Autowired
    public TestRunner(Ignite ignite) {
        this.ignite = ignite;
    }

    @Override
    public void run(String... strings) throws Exception {
        Collection<IgniteCallable<Integer>> calls = new ArrayList<>();
        // Iterate through all the words in the sentence and create Callable jobs.
        for (final String word : "Count characters using callable".split(" "))
            calls.add(word::length);
        // Execute collection of Callables on the grid.
        Collection<Integer> res = ignite.compute().call(calls);
        // Add up all the results.
        int sum = res.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Total number of characters is '" + sum + "'.");


        // Enable asynchronous mode.
        IgniteCompute asyncCompute = ignite.compute().withAsync();
// Asynchronously execute a job.
        asyncCompute.call(() -> {
            // Print hello world on some cluster node and wait for completion.
            System.out.println("Hello World");
            return "Hello World";
        });
// Get the future for the above invocation.
        IgniteFuture<String> fut = asyncCompute.future();
// Asynchronously listen for completion and print out the result.
        fut.listen(f -> System.out.println("Job result: " + f.get()));
    }
}
