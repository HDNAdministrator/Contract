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

public final class SchemaBuilderTypeAdapter implements JsonDeserializer<Schema.Builder> {

    private static final  String TYPE = "type";

    @Override
    public final Schema.Builder deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject schemaBuilderObject = json.getAsJsonObject();

        @SchemaType int type = schemaBuilderObject.get(TYPE).getAsInt();

        switch (type){
            case SchemaType.FIX:
                return FixSchema.Builder.deserialize(schemaBuilderObject);
            case SchemaType.RATE:
                return RateSchema.Builder.deserialize(schemaBuilderObject);
            case SchemaType.COMMISSION:
                return CommissionSchema.Builder.deserialize(schemaBuilderObject);
            case SchemaType.OBJECTIVE:
                return ObjectiveSchema.Builder.deserialize(schemaBuilderObject);
            case SchemaType.THRESHOLD:
                return ThresholdSchema.Builder.deserialize(schemaBuilderObject);
            default:
                throw new JsonParseException("Unknown schema builder.");
        }
    }
}
