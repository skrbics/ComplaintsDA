package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ComplaintAttachmentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static ComplaintAttachment getComplaintAttachmentSample1() {
        return new ComplaintAttachment().id(1L).ordinalNo(1).name("name1").path("path1");
    }

    public static ComplaintAttachment getComplaintAttachmentSample2() {
        return new ComplaintAttachment().id(2L).ordinalNo(2).name("name2").path("path2");
    }

    public static ComplaintAttachment getComplaintAttachmentRandomSampleGenerator() {
        return new ComplaintAttachment()
            .id(longCount.incrementAndGet())
            .ordinalNo(intCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .path(UUID.randomUUID().toString());
    }
}
