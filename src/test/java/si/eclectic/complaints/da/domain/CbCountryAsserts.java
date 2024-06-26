package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;

public class CbCountryAsserts {

    /**
     * Asserts that the entity has all properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbCountryAllPropertiesEquals(CbCountry expected, CbCountry actual) {
        assertCbCountryAutoGeneratedPropertiesEquals(expected, actual);
        assertCbCountryAllUpdatablePropertiesEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all updatable properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbCountryAllUpdatablePropertiesEquals(CbCountry expected, CbCountry actual) {
        assertCbCountryUpdatableFieldsEquals(expected, actual);
        assertCbCountryUpdatableRelationshipsEquals(expected, actual);
    }

    /**
     * Asserts that the entity has all the auto generated properties (fields/relationships) set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbCountryAutoGeneratedPropertiesEquals(CbCountry expected, CbCountry actual) {
        assertThat(expected)
            .as("Verify CbCountry auto generated properties")
            .satisfies(e -> assertThat(e.getId()).as("check id").isEqualTo(actual.getId()));
    }

    /**
     * Asserts that the entity has all the updatable fields set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbCountryUpdatableFieldsEquals(CbCountry expected, CbCountry actual) {
        assertThat(expected)
            .as("Verify CbCountry relevant properties")
            .satisfies(e -> assertThat(e.getName()).as("check name").isEqualTo(actual.getName()))
            .satisfies(e -> assertThat(e.getAbbreviation()).as("check abbreviation").isEqualTo(actual.getAbbreviation()));
    }

    /**
     * Asserts that the entity has all the updatable relationships set.
     *
     * @param expected the expected entity
     * @param actual the actual entity
     */
    public static void assertCbCountryUpdatableRelationshipsEquals(CbCountry expected, CbCountry actual) {}
}
