package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.AssertUtils.zonedDataTimeSameInstant;

public class ComplaintAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertComplaintAllPropertiesEquals(Complaint expected, Complaint actual) {
        assertComplaintAutoGeneratedPropertiesEquals(expected, actual);
        assertComplaintAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertComplaintAllUpdatablePropertiesEquals(Complaint expected, Complaint actual) {
        assertComplaintUpdatableFieldsEquals(expected, actual);
        assertComplaintUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertComplaintAutoGeneratedPropertiesEquals(Complaint expected, Complaint actual) {
        assertThat(expected)
            .as("Verify Complaint auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertComplaintUpdatableFieldsEquals(Complaint expected, Complaint actual) {
        assertThat(expected)
            .as("Verify Complaint relevant properties")
            .satisfies(e -> assertThat(e.getComplaintText()).as("check complaintText").isEqualTo(actual.getComplaintText()))
            .satisfies(
                e ->
                    assertThat(e.getDateAndTime())
                        .as("check dateAndTime")
                        .usingComparator(zonedDataTimeSameInstant)
                        .isEqualTo(actual.getDateAndTime())
            )
            .satisfies(e -> assertThat(e.getWrittenReplyBySMS()).as("check writtenReplyBySMS").isEqualTo(actual.getWrittenReplyBySMS()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertComplaintUpdatableRelationshipsEquals(Complaint expected, Complaint actual) {
        assertThat(expected)
            .as("Verify Complaint relationships")
            .satisfies(e -> assertThat(e.getApplicant()).as("check applicant").isEqualTo(actual.getApplicant()))
            .satisfies(e -> assertThat(e.getCbComplaintField()).as("check cbComplaintField").isEqualTo(actual.getCbComplaintField()))
            .satisfies(
                e -> assertThat(e.getCbComplaintSubField()).as("check cbComplaintSubField").isEqualTo(actual.getCbComplaintSubField())
            )
            .satisfies(e -> assertThat(e.getCbComplaintType()).as("check cbComplaintType").isEqualTo(actual.getCbComplaintType()))
            .satisfies(e -> assertThat(e.getCbComplaintChannel()).as("check cbComplaintChannel").isEqualTo(actual.getCbComplaintChannel()))
            .satisfies(
                e ->
                    assertThat(e.getCbApplicantCapacityType())
                        .as("check cbApplicantCapacityType")
                        .isEqualTo(actual.getCbApplicantCapacityType())
            )
            .satisfies(e -> assertThat(e.getCbComplaintPhase()).as("check cbComplaintPhase").isEqualTo(actual.getCbComplaintPhase()));
    }
}
