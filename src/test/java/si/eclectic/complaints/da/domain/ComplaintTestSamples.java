package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ComplaintTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Complaint getComplaintSample1() {
        return new Complaint().id(1L).complaintText("complaintText1");
    }

    public static Complaint getComplaintSample2() {
        return new Complaint().id(2L).complaintText("complaintText2");
    }

    public static Complaint getComplaintRandomSampleGenerator() {
        return new Complaint().id(longCount.incrementAndGet()).complaintText(UUID.randomUUID().toString());
    }
}
