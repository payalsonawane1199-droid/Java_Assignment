import java.util.concurrent.atomic.AtomicInteger;

public class BankStatementBatchProcessor {

    // FIX: use AtomicInteger because processedCount is updated concurrently by multiple threads
    private AtomicInteger processedCount = new AtomicInteger(0);

    public void process(List<StatementRecord> records) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (StatementRecord record : records) {
            executor.submit(() -> {
                processRecord(record);
                // FIX: atomic increment avoids lost updates caused by processedCount++
                processedCount.incrementAndGet();
            });
        }
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.MINUTES);
    }

    public int getProcessedCount() {
        // FIX: return actual integer value from AtomicInteger
        return processedCount.get();
    }
}