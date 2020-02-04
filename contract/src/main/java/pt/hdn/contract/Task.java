package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.arch.core.util.Function;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.DoubleFunction;

import pt.hdn.contract.annotations.SchemaType;
import pt.hdn.contract.schema.CommissionSchema;
import pt.hdn.contract.schema.FixSchema;
import pt.hdn.contract.schema.RateSchema;
import pt.hdn.contract.schema.Schema;
import pt.hdn.contract.schema.SchemaException;
import pt.hdn.contract.schema.SchemaImp;

import static pt.hdn.contract.annotations.SchemaType.COMMISSION;
import static pt.hdn.contract.annotations.SchemaType.FIX;
import static pt.hdn.contract.annotations.SchemaType.RATE;

public final class Task implements Parcelable {

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    private final ServiceType serviceType;
    private final List<Schema> schemas;

    private Task(Task.Builder builder) {
        this.serviceType = builder.serviceType;
        this.schemas = Collections.unmodifiableList(builder.schemas);
    }

    private Task(Parcel in) {
        this.serviceType = in.readParcelable(ServiceType.class.getClassLoader());
        this.schemas = in.readArrayList(Schema.class.getClassLoader());
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(serviceType, flags);
        dest.writeList(schemas);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public final ServiceType getServiceType() {
        return serviceType;
    }

    public final List<Schema> getSchemas(){
        return schemas;
    }

    public Builder rebuild() {
        Builder builder = new Builder();

        builder.serviceTypeBuilder = serviceType.rebuild();

        for (Schema schema: schemas){
            builder.schemaBuilders.add(schema.rebuild());
        }

        return null;
    }

    public static class Builder{

        private List<Schema> schemas;
        private List<Schema.Builder> schemaBuilders;
        private ServiceType serviceType;
        private ServiceType.Builder serviceTypeBuilder;

        public Builder(){
            this.serviceTypeBuilder = null;
            this.schemaBuilders = new ArrayList<>();
            this.schemas = new ArrayList<>();
        }

        public Builder clearSchemaBuilders() {
            this.schemaBuilders.clear();

            return this;
        }

        public Schema.Builder removeSchemaBuilder(int index){
            return schemaBuilders.remove(index);
        }

        public List<Schema.Builder> getSchemaBuilders(){
            return schemaBuilders;
        }

        public Builder addSchemaBuilder(Schema.Builder builder){
            this.schemaBuilders.add(builder);

            return this;
        }

        public Schema.Builder setSchemaBuilder(int index, Schema.Builder builder){
            return schemaBuilders.set(index, builder);
        }

        public ServiceType.Builder getServiceTypeBuilder(){
            return serviceTypeBuilder;
        }

        public Builder setServiceTypeBuilder(ServiceType.Builder builder){
            this.serviceTypeBuilder = builder;

            return this;
        }

        public Task create() throws TaskException {
            try {
                if(serviceTypeBuilder == null){
                    throw new TaskException("The ServiceType.Builder is missing.");
                } else {
                    this.serviceType = serviceTypeBuilder.create();
                }

                if(schemaBuilders.isEmpty()){
                    throw new TaskException("There are no Schema.Builder added.");
                } else {
                    for (Schema.Builder builder: schemaBuilders){
                        schemas.add(builder.create());
                    }
                }
            } catch (ServiceTypeException | SchemaException e) {
                throw new TaskException(e.getMessage());
            }

            return new Task(this);
        }
    }
}
