package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import pt.hdn.contract.annotations.Day;
import pt.hdn.contract.annotations.DayOfWeek;
import pt.hdn.contract.annotations.DaysPeriod;
import pt.hdn.contract.annotations.DaysType;
import pt.hdn.contract.annotations.Month;
import pt.hdn.contract.annotations.MonthType;
import pt.hdn.contract.annotations.MonthsPeriod;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

public final class Recurrence implements Parcelable {

    //region vars
    public static final Creator<Recurrence> CREATOR = new Creator<Recurrence>() {
        @Override
        public Recurrence createFromParcel(Parcel in) {
            return new Recurrence(in);
        }

        @Override
        public Recurrence[] newArray(int size) {
            return new Recurrence[size];
        }
    };

    private final List<Integer> months;
    private final List<Integer> days;
    private final List<Integer> dow;
    private final ZonedDateTime start;
    private final ZonedDateTime finish;
    private final @MonthsPeriod Integer monthPeriod;
    private final @DaysPeriod Integer daysPeriod;
    private final @DaysType int daysType;
    private final @MonthType int monthType;
    //endregion vars

    private Recurrence(Builder builder) {
        this.months = Collections.unmodifiableList(builder.months);
        this.monthPeriod = builder.monthPeriod;
        this.monthType = builder.monthType;
        this.days = Collections.unmodifiableList(builder.days);
        this.dow = Collections.unmodifiableList(builder.dow);
        this.daysPeriod = builder.daysPeriod;
        this.daysType = builder.daysType;
        this.start = builder.start;
        this.finish = builder.finish;
    }

    private Recurrence(Parcel in) {
        this.monthType = in.readInt();
        this.monthPeriod = in.readByte() != 0 ? in.readInt() : null;
        this.months = Collections.<Integer>unmodifiableList(in.readArrayList(Integer.class.getClassLoader()));
        this.daysType = in.readInt();
        this.daysPeriod = in.readByte() != 0 ? in.readInt() : null;
        this.days = Collections.<Integer>unmodifiableList(in.readArrayList(Integer.class.getClassLoader()));
        this.dow = Collections.<Integer>unmodifiableList(in.readArrayList(Integer.class.getClassLoader()));
        this.start = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
        this.finish = in.readByte() != 0 ? ZonedDateTime.parse(in.readString()) : null;
    }

    @Override
    public final void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(monthType);

        if (this.monthPeriod == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(monthPeriod);
        }

        dest.writeList(months);
        dest.writeInt(daysType);

        if (this.daysPeriod == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(daysPeriod);
        }

        dest.writeList(days);
        dest.writeList(dow);

        if (this.start == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(start.format(ISO_ZONED_DATE_TIME));
        }

        if (this.finish == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(finish.format(ISO_ZONED_DATE_TIME));
        }
    }

    @Override
    public final int describeContents() {
        return 0;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        } else if (object == null || getClass() != object.getClass()) {
            return false;
        } else {
            Recurrence recurrence = (Recurrence) object;

            return this.daysType == recurrence.daysType &&
                    this.monthType == recurrence.monthType &&
                    this.months.equals(recurrence.months) &&
                    this.days.equals(recurrence.days) &&
                    this.dow.equals(recurrence.dow) &&
                    this.start.equals(recurrence.start) &&
                    Objects.equals(this.finish, recurrence.finish) &&
                    Objects.equals(this.monthPeriod, recurrence.monthPeriod) &&
                    Objects.equals(this.daysPeriod, recurrence.daysPeriod);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(months, days, dow, start, finish, monthPeriod, daysPeriod, daysType, monthType);
    }

    public final boolean hasFinish() {
        return this.finish != null;
    }

    public final @DaysType int getDaysType() {
        return daysType;
    }

    public final @MonthType int getMonthType() {
        return monthType;
    }

    public final ZonedDateTime getStart() {
        return start;
    }

    public final ZonedDateTime getFinish() {
        return finish;
    }

    public final @MonthsPeriod Integer getMonthPeriod() {
        return monthPeriod;
    }

    public final Integer getDaysPeriod() {
        return daysPeriod;
    }

    public final List<Integer> getMonths() {
        return months;
    }

    public final List<Integer> getDaysOfWeek() {
        return dow;
    }

    public final List<Integer> getDays() {
        return days;
    }

    public final Builder rebuild() {
        Builder builder = new Builder();

        builder.months = new ArrayList<>(this.months);
        builder.monthPeriod = this.monthPeriod;
        builder.monthType = this.monthType;
        builder.daysPeriod = this.daysPeriod;
        builder.daysType = this.daysType;
        builder.days = new ArrayList<>(this.days);
        builder.dow = new ArrayList<>(this.dow);
        builder.start = this.start;
        builder.finish = this.finish;

        return builder;
    }

    public final static class Builder {

        //region vars
        private List<Integer> months;
        private List<Integer> days;
        private List<Integer> dow;
        private ZonedDateTime start;
        private ZonedDateTime finish;
        private @MonthsPeriod Integer monthPeriod;
        private @DaysPeriod Integer daysPeriod;
        private @DaysType int daysType;
        private @MonthType int monthType;
        //endregion vars

        public Builder() {
            this.monthType = getDefaultMonthType();

            if (this.monthType == MonthType.PERIOD) {
                this.months = new ArrayList<>();
                this.monthPeriod = getDefaultMonth();
            } else {
                this.months = new ArrayList<>(getDefaultMonth());
                this.monthPeriod = null;
            }

            this.daysType = getDefaultDayType();

            switch (this.daysType) {
                case DaysType.DAYS: {
                    this.days = new ArrayList<>();
                    this.dow = new ArrayList<>();
                    this.daysPeriod = null;

                    this.days.add(getDefaultDay());

                    break;
                }
                case DaysType.DOW: {
                    this.days = new ArrayList<>();
                    this.dow = new ArrayList<>();
                    this.daysPeriod = null;

                    this.dow.add(getDefaultDow());

                    break;
                }
                case DaysType.PERIOD: {
                    this.days = new ArrayList<>();
                    this.dow = new ArrayList<>();
                    this.daysPeriod = getDefaultDay();

                    break;
                }
            }

            this.start = null;
            this.finish = null;
        }

        public final boolean hasStart() {
            return this.start != null;
        }

        public final boolean hasFinish() {
            return this.finish != null;
        }

        public final int getDefaultDayType() {
            return DaysType.DAYS;
        }

        public final int getDefaultDay() {
            return Day.DAY_28;
        }

        public final int getDefaultDow() {
            return DayOfWeek.WEDNESDAY;
        }

        public final int getDefaultMonthType() {
            return MonthType.PERIOD;
        }

        public final int getDefaultMonth() {
            return MonthsPeriod.MONTHS_ALL;
        }

        public int getDaysType() {
            return this.daysType;
        }

        public int getMonthType() {
            return this.monthType;
        }

        public final ZonedDateTime getStart() {
            return this.start;
        }

        public final Builder setStart(ZonedDateTime start) {
            this.start = start;

            return this;
        }

        public final ZonedDateTime getFinish() {
            return this.finish;
        }

        public final Builder setFinish(ZonedDateTime finish) {
            this.finish = finish;

            return this;
        }

        public Integer getMonthPeriod() {
            return this.monthPeriod;
        }

        public final Builder setMonthPeriod(@MonthsPeriod int monthPeriod) {
            this.months.clear();
            this.monthType = MonthType.PERIOD;
            this.monthPeriod = monthPeriod;

            return this;
        }

        public Integer getDaysPeriod() {
            return this.daysPeriod;
        }

        public final Builder setDaysPeriod(@DaysPeriod Integer daysPeriod) {
            this.days.clear();
            this.dow.clear();
            this.daysType = DaysType.PERIOD;
            this.daysPeriod = daysPeriod;

            return this;
        }

        public final Builder removeMonth(@Month Integer month) {
            this.months.remove(month);

            return this;
        }

        public List<Integer> getMonths() {
            return this.months;
        }

        public final Builder addMonth(@Month int month) {
            this.monthPeriod = null;
            this.monthType = MonthType.MONTHS;

            if (!this.months.contains(month)) {
                this.months.add(month);

                Collections.sort(this.months);
            }

            return this;
        }

        public final Builder removeDay(@Day Integer day) {
            this.days.remove(day);

            return this;
        }

        public List<Integer> getDays() {
            return this.days;
        }

        public final Builder addDay(@Day int day) {
            this.daysPeriod = null;
            this.dow.clear();
            this.daysType = DaysType.DAYS;

            if (!this.days.contains(day)) {
                this.days.add(day);

                Collections.sort(this.days);
            }

            return this;
        }

        public final Builder removeDow(@DayOfWeek Integer dow) {
            this.dow.remove(dow);

            return this;
        }

        public List<Integer> getDow() {
            return this.dow;
        }

        public final Builder addDow(@DayOfWeek Integer dow) {
            this.days.clear();
            this.daysPeriod = null;
            this.daysType = DaysType.DOW;

            if (!this.dow.contains(dow)) {
                this.dow.add(dow);

                Collections.sort(this.dow);
            }

            return this;
        }

        public boolean validate() {
            switch (this.daysType) {
                case DaysType.DAYS: {
                    if (this.days.size() == 0) {
                        return false;
                    }

                    break;
                }
                case DaysType.DOW: {
                    if (this.dow.size() == 0) {
                        return false;
                    }

                    break;
                }
                case DaysType.PERIOD: {
                    if (this.daysPeriod == null) {
                        return false;
                    }

                    break;
                }
            }

            switch (this.monthType) {
                case MonthType.MONTHS: {
                    if (this.months.size() == 0) {
                        return false;
                    }

                    break;
                }
                case MonthType.PERIOD: {
                    if (this.monthPeriod == null) {
                        return false;
                    }

                    break;
                }
            }

            return this.start != null && (this.finish == null || this.finish.isAfter(this.start) || this.finish.equals(this.start));
        }

        public final Recurrence create() throws RecurrenceException {
            if (this.start == null) {
                throw new RecurrenceException("The start date is missing.");
            } else if (this.finish != null && this.finish.isBefore(this.start)) {
                throw new RecurrenceException("The finish date is before the starting date.");
            } else {
                return new Recurrence(this);
            }
        }
    }
}