package comviniciusgpedroso.github.borrowit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Item Class
 * Deals with the data model for the borrow items.
 */
@Entity(tableName = "item_table")
public class Item implements Parcelable {
    // Status constants
    public static final int OVERDUE = 0;
    public static final int DUE = 1;
    public static final int DONE = 2;
    // Currency Cents converters
    public static final double CURRENCY_0_DECIMALS_MULTIPLIER = 1;
    public static final double CURRENCY_2_DECIMALS_MULTIPLIER = 100.0;
    public static final double CURRENCY_3_DECIMALS_MULTIPLIER = 1000.0;


    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uuid")
    private UUID mId;
    @NonNull
    @ColumnInfo(name = "amount")
    private Long mAmount; // TODO Convert this long to Currency using system location
    @ColumnInfo(name = "contact")
    private String mContact;
    @NonNull
    @ColumnInfo(name = "borrowDate")
    private Date mBorrowDate;
    @NonNull
    @ColumnInfo(name = "dueDate")
    private Date mDueDate;
    @NonNull
    @ColumnInfo(name = "status")
    private Integer mStatus;
    @ColumnInfo(name = "isToReceive")
    private boolean mIsToReceive;
    @ColumnInfo(name = "isObject")
    private boolean mIsObject;
    @ColumnInfo(name = "objectDescription")
    private String objectDescription;
    @NonNull
    @ColumnInfo(name = "isArchived")
    private boolean isArchived;

    public Item(@NonNull UUID id, @NonNull Long amount, String contact,
                @NonNull Date borrowDate, @NonNull Date dueDate,
                boolean isToReceive, boolean isObject, String
                        objectDescription, int status, boolean isArchived) {
        this.mId = id;
        this.mAmount = amount;
        this.mContact = contact;
        this.mBorrowDate = borrowDate;
        this.mDueDate = dueDate;
        this.mIsToReceive = isToReceive;
        this.mIsObject = isObject;
        this.mStatus = status;
        this.objectDescription = objectDescription;
        this.isArchived = isArchived;
        checkAndSetStatus();
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    /**
     * Checks and sets the status considering the current date, due date and
     * the DONE status
     */
    private void checkAndSetStatus() {
        if (mStatus != DONE) {
            mStatus = mDueDate.before(new Date()) ? 0 : 1;
        }
    }

    /*
     * Sets the status to DONE;
     */
    public void setDone() {
        mStatus = DONE;
    }

    /**
     * Checks if the current date is after the date and sets the status
     */
    public void setUndone() {
        if (mDueDate.after(new Date())) {
            mStatus = OVERDUE;
        } else {
            mStatus = DUE;
        }
    }

    public UUID getId() {
        return mId;
    }

    public Long getAmount() {
        return mAmount;
    }

    public String getValue() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(mAmount);
    }

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public Date getBorrowDate() {
        return mBorrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        mBorrowDate = borrowDate;
    }

    public Date getDueDate() {
        return mDueDate;
    }

    public static String getSimpleDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                Locale.getDefault());
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day + " " + month + " " + year;
    }

    public static String getSimpleDate(String dateString) {
        Date date = new Date(dateString);
        return getSimpleDate(date);
    }

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public Integer getStatus() {
        return mStatus;
    }

    public boolean isToReceive() {
        return mIsToReceive;
    }

    public void setIsToReceive(boolean toReceive) {
        mIsToReceive = toReceive;
    }

    public boolean isObject() {
        return mIsObject;
    }

    public void setId(@NonNull UUID id) {
        mId = id;
    }

    public void setAmount(@NonNull Long amount) {
        mAmount = amount;
    }

    public void setStatus(@NonNull Integer status) {
        mStatus = status;
    }

    public void setIsObject(boolean object) {
        mIsObject = object;
    }

    public String getObjectDescription() {
        return objectDescription;
    }

    public void setObjectDescription(String objectDescription) {
        this.objectDescription = objectDescription;
    }

    @NonNull
    public boolean getIsArchived() {
        return isArchived;
    }

    public void setArchived(@NonNull boolean archived) {
        isArchived = archived;
    }

    public int getImageCodeFromStatus() {
        checkAndSetStatus();
        switch (mStatus) {
            case DONE:
                return mIsToReceive ? R.drawable.ic_checked :
                        R.drawable.ic_pay_checked;
            case DUE:
                return mIsToReceive ? R.drawable.ic_before_due_date :
                        R.drawable.ic_pay_before_due_date;
            default:
                return mIsToReceive ? R.drawable.ic_after_due_date :
                        R.drawable.ic_pay_after_due_date;
        }
    }

    //Methods required by the Parcelable Interface
    //Parcel implementation to change details and archiving in the ItemDetailActivity
    protected Item(Parcel in) {
        if (in.readByte() == 0) {
            mAmount = null;
        } else {
            mAmount = in.readLong();
        }
        mContact = in.readString();
        if (in.readByte() == 0) {
            mStatus = null;
        } else {
            mStatus = in.readInt();
        }
        mIsToReceive = in.readByte() != 0;
        mIsObject = in.readByte() != 0;
        objectDescription = in.readString();
        isArchived = in.readByte() != 0;
        mBorrowDate = new Date(in.readLong());
        mDueDate = new Date(in.readLong());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (mAmount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mAmount);
        }
        parcel.writeString(mContact);
        if (mStatus == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(mStatus);
        }
        parcel.writeByte((byte) (mIsToReceive ? 1 : 0));
        parcel.writeByte((byte) (mIsObject ? 1 : 0));
        parcel.writeString(objectDescription);
        parcel.writeByte((byte) (isArchived ? 1 : 0));
        parcel.writeLong(mBorrowDate != null ? mBorrowDate.getTime() : Long.MIN_VALUE);
        parcel.writeLong(mDueDate != null ? mDueDate.getTime() : Long.MIN_VALUE);
    }
}

