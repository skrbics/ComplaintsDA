package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CbApplicantCapacityTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CbApplicantCapacityType getCbApplicantCapacityTypeSample1() {
        return new CbApplicantCapacityType().id(1L).applicantCapacityTypeName("applicantCapacityTypeName1");
    }

    public static CbApplicantCapacityType getCbApplicantCapacityTypeSample2() {
        return new CbApplicantCapacityType().id(2L).applicantCapacityTypeName("applicantCapacityTypeName2");
    }

    public static CbApplicantCapacityType getCbApplicantCapacityTypeRandomSampleGenerator() {
        return new CbApplicantCapacityType().id(longCount.incrementAndGet()).applicantCapacityTypeName(UUID.randomUUID().toString());
    }
}
