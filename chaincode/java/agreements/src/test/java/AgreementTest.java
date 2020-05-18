import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
public final class AgreementTest {

    @Test
    public void isEqual(){
        Agreement agreement = new Agreement("MyCompany", "OtherCompany", "open");
        assertThat(agreement).isEqualTo(agreement);
    }

    @Test
    public void nonEqual(){
        Agreement agreement = new Agreement("MyCompany", "OtherCompany", "open");
        Agreement otherAgreement = new Agreement("MyCompany", "OtherCompany", "close");
        assertThat(agreement).isNotEqualTo(otherAgreement);
    }
}
