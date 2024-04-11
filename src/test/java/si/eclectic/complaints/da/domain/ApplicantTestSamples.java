package si.eclectic.complaints.da.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ApplicantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Applicant getApplicantSample1() {
        return new Applicant()
            .id(1L)
            .firstName("firstName1")
            .middleName("middleName1")
            .lastName("lastName1")
            .email("email1")
            .phone("phone1");
    }

    public static Applicant getApplicantSample2() {
        return new Applicant()
            .id(2L)
            .firstName("firstName2")
            .middleName("middleName2")
            .lastName("lastName2")
            .email("email2")
            .phone("phone2");
    }

    public static Applicant getApplicantRandomSampleGenerator() {
        return new Applicant()
            .id(longCount.incrementAndGet())
            .firstName(UUID.randomUUID().toString())
            .middleName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .phone(UUID.randomUUID().toString());
    }
}
