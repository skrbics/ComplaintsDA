package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbComplaintFieldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbComplaintField getCbComplaintFieldSample1() {
        return new CbComplaintField().id(1L).complaintFieldName("complaintFieldName1");
    }

    public static CbComplaintField getCbComplaintFieldSample2() {
        return new CbComplaintField().id(2L).complaintFieldName("complaintFieldName2");
    }

    public static CbComplaintField getCbComplaintFieldRandomSampleGenerator() {
        return new CbComplaintField().id(longCount.incrementAndGet()).complaintFieldName(UUID.randomUUID().toString());
    }
}
