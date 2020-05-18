
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public final class AgreementRepositoryTest {

    @Nested
    class InvokeQueryAgreementTransaction {

        @Test
        public void whenAgreementExists() {
            AgreementRepository contract = new AgreementRepository();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("ARG000"))
                    .thenReturn("{\"party1\":\"MyCompany\",\"party2\":\"OtherCompany\",\"status\":\"issued\"}");

            Agreement agreement = contract.getAgreement(ctx, "ARG000");

            assertThat(agreement.getParty1())
                    .isEqualTo("MyCompany");
            assertThat(agreement.getParty2())
                    .isEqualTo("OtherCompany");
            assertThat(agreement.getStatus())
                    .isEqualTo("issued");
        }

        @Test
        public void whenCarDoesNotExist() {
            AgreementRepository contract = new AgreementRepository();
            Context ctx = mock(Context.class);
            ChaincodeStub stub = mock(ChaincodeStub.class);
            when(ctx.getStub()).thenReturn(stub);
            when(stub.getStringState("ARG000")).thenReturn("");

            Throwable thrown = catchThrowable(() -> {
                contract.getAgreement(ctx, "ARG000");
            });

            assertThat(thrown).isInstanceOf(ChaincodeException.class).hasNoCause()
                    .hasMessage("Agreement ARG000 does not exist");
        }

    }

}
