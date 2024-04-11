package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbCountryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbCountry getCbCountrySample1() {
        return new CbCountry().id(1L).name("name1").abbreviation("abbreviation1");
    }

    public static CbCountry getCbCountrySample2() {
        return new CbCountry().id(2L).name("name2").abbreviation("abbreviation2");
    }

    public static CbCountry getCbCountryRandomSampleGenerator() {
        return new CbCountry()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .abbreviation(UUID.randomUUID().toString());
    }
}
