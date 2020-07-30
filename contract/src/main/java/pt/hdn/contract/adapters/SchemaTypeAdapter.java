package pt.hdn.contract.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.schema.CommissionSchema;
import pt.hdn.contract.schema.FixSchema;
import pt.hdn.contract.schema.ObjectiveSchema;
import pt.hdn.contract.schema.RateSchema;
import pt.hdn.contract.schema.Schema;
import pt.hdn.contract.schema.ThresholdSchema;

public final class SchemaTypeAdapter implements JsonDeserializer<Schema> {

    //region vars
    private static final  String TYPE = "type";
    //endregion vars

    @Override
    public final Schema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject schemaObject = json.getAsJsonObject();

        @SchemaType int type = schemaObject.get(TYPE).getAsInt();

        switch (type){
            case SchemaType.FIX:
                return FixSchema.deserialize(schemaObject);
            case SchemaType.RATE:
                return RateSchema.deserialize(schemaObject);
            case SchemaType.COMMISSION:
                return CommissionSchema.deserialize(schemaObject);
            case SchemaType.OBJECTIVE:
                return ObjectiveSchema.deserialize(schemaObject);
            case SchemaType.THRESHOLD:
                return ThresholdSchema.deserialize(schemaObject);
            default:
                throw new JsonParseException("Unknown schema.");
        }
    }
}
