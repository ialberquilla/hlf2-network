import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;


@DataType
public final class Agreement {

    @Property()
    private final String party1;

    @Property()
    private final String party2;

    @Property
    private final String status;

    public String getParty1() {
        return party1;
    }

    public String getParty2() {
        return party2;
    }

    public String getStatus() {
        return status;
    }

    public Agreement(@JsonProperty("party1") final String party1, @JsonProperty("party2") final String party2,
                     @JsonProperty("status") final String status) {
        this.party1 = party1;
        this.party2 = party2;
        this.status = status;
    }
}
