package si.eclectic.complaints.da.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static si.eclectic.complaints.da.domain.OperatorTestSamples.*;

import org.junit.jupiter.api.Test;
import si.eclectic.complaints.da.web.rest.TestUtil;

class OperatorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Operator.class);
        Operator operator1 = getOperatorSample1();
        Operator operator2 = new Operator();
        assertThat(operator1).isNotEqualTo(operator2);

        operator2.setId(operator1.getId());
        assertThat(operator1).isEqualTo(operator2);

        operator2 = getOperatorSample2();
        assertThat(operator1).isNotEqualTo(operator2);
    }
}
