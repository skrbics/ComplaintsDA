package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbComplaintChannelTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbComplaintChannel getCbComplaintChannelSample1() {
        return new CbComplaintChannel().id(1L).complaintChannelName("complaintChannelName1");
    }

    public static CbComplaintChannel getCbComplaintChannelSample2() {
        return new CbComplaintChannel().id(2L).complaintChannelName("complaintChannelName2");
    }

    public static CbComplaintChannel getCbComplaintChannelRandomSampleGenerator() {
        return new CbComplaintChannel().id(longCount.incrementAndGet()).complaintChannelName(UUID.randomUUID().toString());
    }
}
