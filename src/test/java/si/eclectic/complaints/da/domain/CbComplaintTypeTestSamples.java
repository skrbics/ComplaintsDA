package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbComplaintTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbComplaintType getCbComplaintTypeSample1() {
        return new CbComplaintType().id(1L).complaintTypeName("complaintTypeName1");
    }

    public static CbComplaintType getCbComplaintTypeSample2() {
        return new CbComplaintType().id(2L).complaintTypeName("complaintTypeName2");
    }

    public static CbComplaintType getCbComplaintTypeRandomSampleGenerator() {
        return new CbComplaintType().id(longCount.incrementAndGet()).complaintTypeName(UUID.randomUUID().toString());
    }
}
