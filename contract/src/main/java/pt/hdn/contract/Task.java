package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import pt.hdn.contract.schema.Schema;
import pt.hdn.contract.schema.SchemaException;

public final class Task implements Parcelable {

    //region vars
    private static final String TAG = "Task";
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

    private final Type specialityType;
    private final List<Schema> schemas;
    private final List<Type> responsibilities;
    private final boolean exclusivity;
    //endregion vars

    private Task(Task.Builder builder) {
        this.specialityType = builder.type;
        this.schemas = Collections.unmodifiableList(builder.schemas);
        this.responsibilities = Collections.unmodifiableList(builder.responsibilities);
        this.exclusivity = builder.exclusivity;
    }

    private Task(Parcel in) {
        this.specialityType = in.readParcelable(Type.class.getClassLoader());
        this.schemas = in.readArrayList(Schema.class.getClassLoader());
        this.responsibilities = in.readArrayList(Type.class.getClassLoader());
        this.exclusivity = in.readByte() != 0;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.specialityType, flags);
        dest.writeList(this.schemas);
        dest.writeList(this.responsibilities);
        dest.writeByte((byte) (this.exclusivity ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null || getClass() != obj.getClass()) {
            return false;
        } else {
            Task task = (Task) obj;

            return this.specialityType.equals(task.specialityType) && this.schemas.equals(task.schemas) && this.responsibilities.equals(task.responsibilities);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.specialityType, this.schemas, this.responsibilities);
    }

    public final Type getSpecialityType() {
        return this.specialityType;
    }

    public final List<Schema> getSchemas() {
        return this.schemas;
    }

    public final List<Type> getResponsibilities() {
        return this.responsibilities;
    }

    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.specialityTypeBuilder = this.specialityType.rebuild();

        for (Schema schema : this.schemas) {
            builder.schemaBuilders.add(schema.rebuild());
        }

        for (Type responsibility : this.responsibilities) {
            builder.responsibilityBuilders.add(responsibility.rebuild());
        }

        return builder;
    }

    public final static class Builder {

        //region vars
        private List<Schema> schemas;
        private List<Schema.Builder> schemaBuilders;
        private List<Type> responsibilities;
        private List<Type.Builder> responsibilityBuilders;
        private Type type;
        private Type.Builder specialityTypeBuilder;
        private Boolean exclusivity;
        //endregion vars

        public Builder() {
            this.specialityTypeBuilder = null;
            this.schemaBuilders = new ArrayList<>();
            this.schemas = new ArrayList<>();
            this.responsibilityBuilders = new ArrayList<>();
            this.responsibilities = new ArrayList<>();
            this.exclusivity = null;
        }

        public final Builder clearSchemaBuilders() {
            this.schemaBuilders.clear();

            return this;
        }

        public final Schema.Builder removeSchemaBuilder(int index) {
            return this.schemaBuilders.remove(index);
        }

        public final List<Schema.Builder> getSchemaBuilders() {
            return this.schemaBuilders;
        }

        public final Builder addSchemaBuilder(Schema.Builder builder) {
            this.schemaBuilders.add(builder);

            return this;
        }

        public final Builder addSchemaBuilders(Schema.Builder... builders) {
            return addSchemaBuilders(Arrays.asList(builders));
        }

        public final Builder addSchemaBuilders(List<Schema.Builder> builders) {
            this.schemaBuilders.addAll(builders);

            return this;
        }

        public final Schema.Builder setSchemaBuilder(int index, Schema.Builder builder) {
            return schemaBuilders.set(index, builder);
        }

        public final Builder clearResponsibilityBuilders() {
            this.responsibilityBuilders.clear();

            return this;
        }

        public final Type.Builder removeResponsibilityBuilder(int index) {
            return this.responsibilityBuilders.remove(index);
        }

        public final List<Type.Builder> getResponsibilityBuilders() {
            return this.responsibilityBuilders;
        }

        public final Builder addResponsibilityBuilder(Type.Builder builder) {
            this.responsibilityBuilders.add(builder);

            return this;
        }

        public final Builder addResponsibilityBuilders(Type.Builder... builders) {
            return addResponsibilityBuilders(Arrays.asList(builders));
        }

        public final Builder addResponsibilityBuilders(List<Type.Builder> builders) {
            this.responsibilityBuilders.addAll(builders);

            return this;
        }

        public final Type.Builder setResponsibilityBuilder(int index, Type.Builder builder) {
            return this.responsibilityBuilders.set(index, builder);
        }

        public final Type.Builder getSpecialityTypeBuilder() {
            return this.specialityTypeBuilder;
        }

        public final Builder setSpecialityTypeBuilder(Type.Builder builder) {
            this.specialityTypeBuilder = builder;

            return this;
        }

        public final Boolean getExclusivity() {
            return this.exclusivity;
        }

        public Builder setExclusivity(boolean exclusivity) {
            this.exclusivity = exclusivity;

            return this;
        }

        public final boolean validate() {
            if (this.schemaBuilders == null) {
                return false;
            } else {
                for (Schema.Builder builder : this.schemaBuilders) {
                    if (!builder.validate()) {
                        return false;
                    }
                }
            }

            for (Type.Builder builder : this.responsibilityBuilders) {
                if (!builder.validate()) {
                    return false;
                }
            }

            return this.specialityTypeBuilder != null && this.specialityTypeBuilder.validate() && this.exclusivity != null;
        }

        public final Task create() throws TaskException {
            try {
                if (this.specialityTypeBuilder == null) {
                    throw new TaskException("The ServiceType.Builder is missing.");
                } else {
                    this.type = this.specialityTypeBuilder.create();
                }

                if (this.schemaBuilders.isEmpty()) {
                    throw new TaskException("There are no Schema.Builder added.");
                } else {
                    for (Schema.Builder builder : this.schemaBuilders) {
                        this.schemas.add(builder.create());
                    }
                }

                for (Type.Builder builder : this.responsibilityBuilders) {
                    this.responsibilities.add(builder.create());
                }
            } catch (ServiceTypeException | SchemaException e) {
                throw new TaskException(e.getMessage());
            }

            return new Task(this);
        }
    }
}
