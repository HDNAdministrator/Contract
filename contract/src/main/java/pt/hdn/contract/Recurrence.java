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
        this.monthType = builder.monthType;
        this.monthPeriod = builder.monthPeriod;
        this.months = builder.months != null ? Collections.unmodifiableList(builder.months) : null;
        this.daysType = builder.daysType;
        this.daysPeriod = builder.daysPeriod;
        this.days = builder.days != null ? Collections.unmodifiableList(builder.days) : null;
        this.dow = builder.dow != null ? Collections.unmodifiableList(builder.dow) : null;
        this.start = builder.start;
        this.finish = builder.finish;
    }

    private Recurrence(Parcel in) {
        this.monthType = in.readInt();
        this.monthPeriod = in.readByte() != 0 ? in.readInt() : null;
        this.months = in.readByte() != 0 ? Collections.<Integer>unmodifiableList(in.readArrayList(Integer.class.getClassLoader())) : null;
        this.daysType = in.readInt();
        this.daysPeriod = in.readByte() != 0 ? in.readInt() : null;
        this.days = in.readByte() != 0 ? Collections.<Integer>unmodifiableList(in.readArrayList(Integer.class.getClassLoader())) : null;
        this.dow = in.readByte() != 0 ? Collections.<Integer>unmodifiableList(in.readArrayList(Integer.class.getClassLoader())) : null;
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

        if (this.months == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeList(months);
        }

        dest.writeInt(daysType);

        if (this.daysPeriod == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(daysPeriod);
        }

        if (this.days == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeList(days);
        }

        if (this.dow == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeList(dow);
        }

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

            return this.monthType == recurrence.monthType &&
                    Objects.equals(this.monthPeriod, recurrence.monthPeriod) &&
                    Objects.equals(this.months, recurrence.months) &&
                    this.daysType == recurrence.daysType &&
                    Objects.equals(this.daysPeriod, recurrence.daysPeriod) &&
                    Objects.equals(this.days, recurrence.days) &&
                    Objects.equals(this.dow, recurrence.dow) &&
                    this.start.equals(recurrence.start) &&
                    Objects.equals(this.finish, recurrence.finish);
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

        builder.months = this.months != null ? new ArrayList<>(this.months) : null;
        builder.monthPeriod = this.monthPeriod;
        builder.monthType = this.monthType;
        builder.daysPeriod = this.daysPeriod;
        builder.daysType = this.daysType;
        builder.days = this.days != null ? new ArrayList<>(this.days) : null;
        builder.dow = this.dow != null ? new ArrayList<>(this.dow) : null;
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
                this.months = null;
                this.monthPeriod = getDefaultMonth();
            } else {
                this.months = new ArrayList<>();
                this.monthPeriod = null;
            }

            this.daysType = getDefaultDayType();

            switch (this.daysType) {
                case DaysType.DAYS: {
                    this.daysPeriod = null;
                    this.days = new ArrayList<>(getDefaultDay());
                    this.days.add(getDefaultDay());
                    this.dow = null;

                    break;
                }
                case DaysType.DOW: {
                    this.daysPeriod = null;
                    this.days = null;
                    this.dow = new ArrayList<>(getDefaultDow());
                    this.dow.add(getDefaultDay());

                    break;
                }
                case DaysType.PERIOD: {
                    this.daysPeriod = getDefaultDay();
                    this.days = null;
                    this.dow = null;

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
            this.monthType = MonthType.PERIOD;
            this.monthPeriod = monthPeriod;

            if (this.months != null) {
                this.months.clear();
                this.months = null;
            }

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
            this.monthType = MonthType.MONTHS;

            if (this.months == null) {
                this.months = new ArrayList<>(month);
            } else if (!this.months.contains(month)) {
                this.months.add(month);

                Collections.sort(this.months);
            }

            this.monthPeriod = null;

            return this;
        }

        public Integer getDaysPeriod() {
            return this.daysPeriod;
        }

        public final Builder setDaysPeriod(@DaysPeriod Integer daysPeriod) {
            this.daysType = DaysType.PERIOD;
            this.daysPeriod = daysPeriod;

            if (this.days != null) {
                this.days.clear();
                this.days = null;
            }

            if (this.dow != null) {
                this.dow.clear();
                this.dow = null;
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
            this.daysType = DaysType.DAYS;

            if (this.days == null) {
                this.days = new ArrayList<>(day);
            } else if (!this.days.contains(day)) {
                this.days.add(day);

                Collections.sort(this.days);
            }

            if (this.dow != null) {
                this.dow.clear();
                this.dow = null;
            }

            this.daysPeriod = null;

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
            this.daysType = DaysType.DOW;

            if (this.dow == null) {
                this.dow = new ArrayList<>(dow);
            } else if (!this.dow.contains(dow)) {
                this.dow.add(dow);

                Collections.sort(this.dow);
            }

            if (this.days != null) {
                this.days.clear();
                this.days = null;
            }

            this.daysPeriod = null;

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