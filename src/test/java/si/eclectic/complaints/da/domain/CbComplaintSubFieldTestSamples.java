package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbComplaintSubFieldTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbComplaintSubField getCbComplaintSubFieldSample1() {
        return new CbComplaintSubField().id(1L).complaintSubFieldName("complaintSubFieldName1");
    }

    public static CbComplaintSubField getCbComplaintSubFieldSample2() {
        return new CbComplaintSubField().id(2L).complaintSubFieldName("complaintSubFieldName2");
    }

    public static CbComplaintSubField getCbComplaintSubFieldRandomSampleGenerator() {
        return new CbComplaintSubField().id(longCount.incrementAndGet()).complaintSubFieldName(UUID.randomUUID().toString());
    }
}
