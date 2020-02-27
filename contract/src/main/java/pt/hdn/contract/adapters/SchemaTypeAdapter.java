package pt.hdn.contract.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.schema.CommissionSchema;
import pt.hdn.contract.schema.FixSchema;
import pt.hdn.contract.schema.ObjectiveSchema;
import pt.hdn.contract.schema.RateSchema;
import pt.hdn.contract.schema.Schema;
import pt.hdn.contract.schema.ThresholdSchema;

import static pt.hdn.contract.annotations.SchemaType.COMMISSION;
import static pt.hdn.contract.annotations.SchemaType.FIX;
import static pt.hdn.contract.annotations.SchemaType.OBJECTIVE;
import static pt.hdn.contract.annotations.SchemaType.RATE;
import static pt.hdn.contract.annotations.SchemaType.THRESHOLD;

public final class SchemaTypeAdapter implements JsonDeserializer<Schema> {

    private static final  String TYPE = "type";

    @Override
    public final Schema deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject schemaObject = json.getAsJsonObject();

        @SchemaType int type = schemaObject.get(TYPE).getAsInt();

        switch (type){
            case FIX:
                return FixSchema.deserialize(schemaObject);
            case RATE:
                return RateSchema.deserialize(schemaObject);
            case COMMISSION:
                return CommissionSchema.deserialize(schemaObject);
            case OBJECTIVE:
                return ObjectiveSchema.deserialize(schemaObject);
            case THRESHOLD:
                return ThresholdSchema.deserialize(schemaObject);
            default:
                throw new JsonParseException("Unknown schema.");
        }
    }
}
