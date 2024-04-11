package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbAttachmentTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbAttachmentType getCbAttachmentTypeSample1() {
        return new CbAttachmentType().id(1L).name("name1").extension("extension1");
    }

    public static CbAttachmentType getCbAttachmentTypeSample2() {
        return new CbAttachmentType().id(2L).name("name2").extension("extension2");
    }

    public static CbAttachmentType getCbAttachmentTypeRandomSampleGenerator() {
        return new CbAttachmentType()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .extension(UUID.randomUUID().toString());
    }
}
