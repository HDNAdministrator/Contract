package pt.hdn.contract.annotations;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({Field.BUYER, Field.SELLER, Field.SELLER_TIMESTAMP, Field.WITNESS, Field.WITNESS_TIMESTAMP, Field.BUYER_DEPUTY_TIMESTAMP, Field.SELLER_DEPUTY_TIMESTAMP})
public @interface Field {
    String BUYER  = "buyer";
    String SELLER  = "seller";
    String WITNESS  = "witness";
    String BUYER_DEPUTY_TIMESTAMP = "buyerDeputyTimestamp";
    String BUYER_TIMESTAMP = "buyerTimestamp";
    String SELLER_DEPUTY_TIMESTAMP = "sellerDeputyTimestamp";
    String SELLER_TIMESTAMP = "sellerTimestamp";
    String WITNESS_TIMESTAMP = "witnessTimestamp";
}