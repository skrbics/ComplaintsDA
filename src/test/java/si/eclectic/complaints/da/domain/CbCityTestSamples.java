package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbCityTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbCity getCbCitySample1() {
        return new CbCity().id(1L).name("name1").zip("zip1");
    }

    public static CbCity getCbCitySample2() {
        return new CbCity().id(2L).name("name2").zip("zip2");
    }

    public static CbCity getCbCityRandomSampleGenerator() {
        return new CbCity().id(longCount.incrementAndGet()).name(UUID.randomUUID().toString()).zip(UUID.randomUUID().toString());
    }
}
