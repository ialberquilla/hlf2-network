import com.owlike.genson.Genson;
import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.*;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;


@Contract(
        name = "Agreements",
        info = @Info(
                title = "Agreements contract",
                description = "A java chaincode example",
                version = "0.0.1-SNAPSHOT"))

@Default
    public final class AgreementRepository implements ContractInterface{
    private final Genson genson = new Genson();

    @Transaction()
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        Agreement agreement = new Agreement("MyCompany", "OtherCompany", "open");

        String agreementState = genson.serialize(agreement);
        stub.putStringState("ARG001", agreementState);
    }


    @Transaction()
    public Agreement getAgreement(final Context ctx, final String key) {
        ChaincodeStub stub = ctx.getStub();
        String agreementState = stub.getStringState(key);

        if (agreementState.isEmpty()) {
            String errorMessage = String.format("Agreement %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement not found");
        }

        Agreement agreement = genson.deserialize(agreementState, Agreement.class);

        return agreement;
    }

    @Transaction()
    public Agreement createAgreement(final Context ctx, final String key, final String party1, final String party2,
                                    final String stats) {
        ChaincodeStub stub = ctx.getStub();

        String agreementState = stub.getStringState(key);
        if (!agreementState.isEmpty()) {
            String errorMessage = String.format("Agreement %s already exists", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement already exists");
        }

        Agreement agreement = new Agreement(party1, party2, stats);
        agreementState = genson.serialize(agreement);
        stub.putStringState(key, agreementState);

        return agreement;
    }


    @Transaction()
    public Agreement changeAgreementStatus(final Context ctx, final String key, final String newStatus) {
        ChaincodeStub stub = ctx.getStub();

        String agreementState = stub.getStringState(key);

        if (agreementState.isEmpty()) {
            String errorMessage = String.format("Agreement %s does not exist", key);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, "Agreement not found");
        }

        Agreement agreement = genson.deserialize(agreementState, Agreement.class);

        Agreement newAgreement = new Agreement(agreement.getParty1(), agreement.getParty2(),newStatus);
        String newAgreementState = genson.serialize(newAgreement);
        stub.putStringState(key, newAgreementState);

        return newAgreement;
    }

}
