package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CbApplicantCapacityTypeAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbApplicantCapacityTypeAllPropertiesEquals(CbApplicantCapacityType expected, CbApplicantCapacityType actual) {
        assertCbApplicantCapacityTypeAutoGeneratedPropertiesEquals(expected, actual);
        assertCbApplicantCapacityTypeAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbApplicantCapacityTypeAllUpdatablePropertiesEquals(
        CbApplicantCapacityType expected,
        CbApplicantCapacityType actual
    ) {
        assertCbApplicantCapacityTypeUpdatableFieldsEquals(expected, actual);
        assertCbApplicantCapacityTypeUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbApplicantCapacityTypeAutoGeneratedPropertiesEquals(
        CbApplicantCapacityType expected,
        CbApplicantCapacityType actual
    ) {
        assertThat(expected)
            .as("Verify CbApplicantCapacityType auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbApplicantCapacityTypeUpdatableFieldsEquals(
        CbApplicantCapacityType expected,
        CbApplicantCapacityType actual
    ) {
        assertThat(expected)
            .as("Verify CbApplicantCapacityType relevant properties")
            .satisfies(
                e ->
                    assertThat(e.getApplicantCapacityTypeName())
                        .as("check applicantCapacityTypeName")
                        .isEqualTo(actual.getApplicantCapacityTypeName())
            );
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbApplicantCapacityTypeUpdatableRelationshipsEquals(
        CbApplicantCapacityType expected,
        CbApplicantCapacityType actual
    ) {}
}
