package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class CbComplaintPhaseTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static CbComplaintPhase getCbComplaintPhaseSample1() {
        return new CbComplaintPhase().id(1L).ordinalNo(1).complaintPhaseName("complaintPhaseName1");
    }

    public static CbComplaintPhase getCbComplaintPhaseSample2() {
        return new CbComplaintPhase().id(2L).ordinalNo(2).complaintPhaseName("complaintPhaseName2");
    }

    public static CbComplaintPhase getCbComplaintPhaseRandomSampleGenerator() {
        return new CbComplaintPhase()
            .id(longCount.incrementAndGet())
            .ordinalNo(intCount.incrementAndGet())
            .complaintPhaseName(UUID.randomUUID().toString());
    }
}
