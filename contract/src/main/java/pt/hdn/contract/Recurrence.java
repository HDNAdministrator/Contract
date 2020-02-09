package pt.hdn.contract;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;
import androidx.core.util.Pair;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pt.hdn.contract.annotations.Day;
import pt.hdn.contract.annotations.DayOfWeek;
import pt.hdn.contract.annotations.DaysPeriod;
import pt.hdn.contract.annotations.DaysType;
import pt.hdn.contract.annotations.Month;
import pt.hdn.contract.annotations.MonthType;
import pt.hdn.contract.annotations.MonthsPeriod;

import static java.time.format.DateTimeFormatter.ISO_DATE;

public final class Recurrence implements Parcelable {

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

    private Recurrence(Builder builder){
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
        this.months = Collections.unmodifiableList(in.readArrayList(Integer.class.getClassLoader()));
        this.daysType = in.readInt();
        this.daysPeriod = in.readByte() != 0 ? in.readInt() : null;
        this.days = Collections.unmodifiableList(in.readArrayList(Integer.class.getClassLoader()));
        this.dow = Collections.unmodifiableList(in.readArrayList(Integer.class.getClassLoader()));
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

        if(this.start == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(start.format(ISO_DATE));
        }

        if(this.finish == null){
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeString(finish.format(ISO_DATE));
        }
    }

    @Override
    public final int describeContents() {
        return 0;
    }

    public final @DaysType int getDaysType(){
        return daysType;
    }

    public final @MonthType int getMonthType(){
        return monthType;
    }

    public final ZonedDateTime getStart(){
        return start;
    }

    public final ZonedDateTime getFinish() {
        return finish;
    }

    public final @MonthsPeriod Integer getMonthPeriod(){
        return monthPeriod;
    }

    public final Integer getDaysPeriod(){
        return daysPeriod;
    }

    public final List<Integer> getMonths(){
        return months;
    }

    public final List<Integer> getDaysOfWeek(){
        return dow;
    }

    public final List<Integer> getDays(){
        return days;
    }

    @Override
    public final boolean equals(@Nullable Object object) {
        if(!super.equals(object)){
            if(!(object instanceof Recurrence)){
                return false;
            }

            Recurrence recurrence = (Recurrence) object;

            if(!this.start.equals(recurrence.start)){
                return false;
            }

            if(this.finish != null ^ recurrence.finish != null || (this.finish != null && !this.finish.equals(recurrence.finish))){
                return false;
            }

            if(this.monthType != recurrence.monthType){
                return false;
            }

            if(this.daysType != recurrence.daysType){
                return false;
            }

            if(this.monthType == MonthType.PERIOD){
                if(!this.monthPeriod.equals(recurrence.monthPeriod)){
                    return false;
                }
            } else {
                int sizeMonth = this.months.size();

                if(sizeMonth != recurrence.months.size()){
                    return false;
                }

                for (int i = 0; i < sizeMonth; i++){
                    if(!this.months.get(i).equals(recurrence.months.get(i))){
                        return false;
                    }
                }
            }

            switch (daysType){
                case DaysType.DAYS:
                    int sizeDay = this.days.size();

                    if(sizeDay != recurrence.days.size()){
                        return false;
                    }

                    for (int i = 0; i < sizeDay; i++){
                        if(!this.days.get(i).equals(recurrence.days.get(i))){
                            return false;
                        }
                    }

                    break;
                case DaysType.DOW:
                    int sizeDow = this.dow.size();

                    if(sizeDow != recurrence.dow.size()){
                        return false;
                    }

                    for (int i = 0; i < sizeDow; i++){
                        if(!this.dow.get(i).equals(recurrence.dow.get(i))){
                            return false;
                        }
                    }

                    break;
                case DaysType.PERIOD:
                    if(!this.daysPeriod.equals(recurrence.daysPeriod)){
                        return false;
                    }

                    break;
            }
        }

        return true;
    }

    public final Builder rebuild(){
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

    public final static class Builder{

        private List<Integer> months;
        private List<Integer> days;
        private List<Integer> dow;
        private ZonedDateTime start;
        private ZonedDateTime finish;
        private @MonthsPeriod Integer monthPeriod;
        private @DaysPeriod Integer daysPeriod;
        private @DaysType int daysType;
        private @MonthType int monthType;

        public Builder(){
            this.monthType = getDefaultMonthType();

            if(monthType == MonthType.PERIOD){
                this.months = new ArrayList<>();
                this.monthPeriod = getDefaultMonth();
            } else {
                this.months = new ArrayList<>(getDefaultMonth());
                this.monthPeriod = null;
            }

            this.daysType = getDefaultDayType();

            switch (daysType){
                case DaysType.DAYS:
                    this.days = new ArrayList<>(getDefaultDay());
                    this.dow = new ArrayList<>();
                    this.daysPeriod = null;

                    break;
                case DaysType.DOW:
                    this.days = new ArrayList<>();
                    this.dow = new ArrayList<>(getDefaultDow());
                    this.daysPeriod = null;

                    break;
                case DaysType.PERIOD:
                    this.days = new ArrayList<>();
                    this.dow = new ArrayList<>();
                    this.daysPeriod = getDefaultDay();

                    break;
            }

            this.start = null;
            this.finish = null;
        }

        public final int getDefaultDayType(){
            return DaysType.DAYS;
        }

        public final int getDefaultDay(){
            return Day.DAY_28;
        }

        public final int getDefaultDow(){
            return DayOfWeek.WEDNESDAY;
        }

        public final int getDefaultMonthType(){
            return MonthType.PERIOD;
        }

        public final int getDefaultMonth(){
            return MonthsPeriod.MONTHS_ALL;
        }

        public final Builder removeMonth(@Month Integer month){
            this.months.remove(month);

            return this;
        }

        public final Builder addMonth(@Month int month){
            this.monthPeriod = null;
            this.monthType = MonthType.MONTHS;

            if(!months.contains(month)){
                months.add(month);

                Collections.sort(months);
            }

            return this;
        }

        public final Builder setMonthPeriod(@MonthsPeriod int monthPeriod){
            this.months.clear();
            this.monthType = MonthType.PERIOD;
            this.monthPeriod = monthPeriod;

            return this;
        }

        public final Builder addDay(@Day int day){
            this.daysPeriod = null;
            this.dow.clear();
            this.daysType = DaysType.DAYS;

            if(!days.contains(day)){
                days.add(day);

                Collections.sort(days);
            }

            return this;
        }

        public final Builder removeDay(@Day int day){
            this.days.remove(day);

            return this;
        }

        public final Builder addDow(@DayOfWeek Integer dow){
            this.days.clear();
            this.daysPeriod = null;
            this.daysType = DaysType.DOW;

            if(!this.dow.contains(dow)){
                this.dow.add(dow);

                Collections.sort(this.dow);
            }

            return this;
        }

        public final Builder removeDow(@DayOfWeek Integer dow){
            this.dow.remove(dow);

            return this;
        }

        public final Builder setDaysPeriod(@DaysPeriod Integer daysPeriod){
            this.days.clear();
            this.dow.clear();
            this.daysType = DaysType.PERIOD;
            this.daysPeriod = daysPeriod;

            return this;
        }

        public final Builder setStart(ZonedDateTime start) {
            this.start = start;

            return this;
        }

        public final Builder setFinish(ZonedDateTime finish) {
            this.finish = finish;

            return this;
        }

        public final Recurrence create() throws RecurrenceException{
            if(start == null){
                throw new RecurrenceException("The start date is missing.");
            } else if(finish != null && start.isAfter(finish)) {
                throw new RecurrenceException("The finish date is before the starting date.");
            } else {
                return  new Recurrence(this);
            }
        }

        public boolean validate(){
            return start != null;
        }
    }
}