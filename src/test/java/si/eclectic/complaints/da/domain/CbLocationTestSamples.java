package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbLocationTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbLocation getCbLocationSample1() {
        return new CbLocation().id(1L).locationName("locationName1");
    }

    public static CbLocation getCbLocationSample2() {
        return new CbLocation().id(2L).locationName("locationName2");
    }

    public static CbLocation getCbLocationRandomSampleGenerator() {
        return new CbLocation().id(longCount.incrementAndGet()).locationName(UUID.randomUUID().toString());
    }
}
