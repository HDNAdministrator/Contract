package pt.hdn.contract.annotations;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

@Retention(SOURCE)
@StringDef({Contract.ALGORITHM, Contract.BUYER, Contract.SELLER, Contract.SELLER_TIMESTAMP, Contract.WITNESS, Contract.WITNESS_TIMESTAMP, Contract.BUYER_DEPUTY_TIMESTAMP, Contract.SELLER_DEPUTY_TIMESTAMP})
public @interface Contract {
    String ALGORITHM = "SHA512withRSA";
    String BUYER  = "buyer";
    String SELLER  = "seller";
    String WITNESS  = "witness";
    String BUYER_DEPUTY_TIMESTAMP = "buyerDeputyTimestamp";
    String BUYER_TIMESTAMP = "buyerTimestamp";
    String SELLER_DEPUTY_TIMESTAMP = "sellerDeputyTimestamp";
    String SELLER_TIMESTAMP = "sellerTimestamp";
    String WITNESS_TIMESTAMP = "witnessTimestamp";
}