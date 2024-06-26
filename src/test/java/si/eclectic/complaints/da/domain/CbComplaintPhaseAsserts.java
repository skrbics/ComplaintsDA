package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CbComplaintPhaseAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbComplaintPhaseAllPropertiesEquals(CbComplaintPhase expected, CbComplaintPhase actual) {
        assertCbComplaintPhaseAutoGeneratedPropertiesEquals(expected, actual);
        assertCbComplaintPhaseAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbComplaintPhaseAllUpdatablePropertiesEquals(CbComplaintPhase expected, CbComplaintPhase actual) {
        assertCbComplaintPhaseUpdatableFieldsEquals(expected, actual);
        assertCbComplaintPhaseUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbComplaintPhaseAutoGeneratedPropertiesEquals(CbComplaintPhase expected, CbComplaintPhase actual) {
        assertThat(expected)
            .as("Verify CbComplaintPhase auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbComplaintPhaseUpdatableFieldsEquals(CbComplaintPhase expected, CbComplaintPhase actual) {
        assertThat(expected)
            .as("Verify CbComplaintPhase relevant properties")
            .satisfies(e -> assertThat(e.getOrdinalNo()).as("check ordinalNo").isEqualTo(actual.getOrdinalNo()))
            .satisfies(e -> assertThat(e.getComplaintPhaseName()).as("check complaintPhaseName").isEqualTo(actual.getComplaintPhaseName()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbComplaintPhaseUpdatableRelationshipsEquals(CbComplaintPhase expected, CbComplaintPhase actual) {}
}
