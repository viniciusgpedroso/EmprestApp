package comviniciusgpedroso.github.borrowit;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
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
public class Item {
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
    private Long mAmount; // TODO Convert this float to Currency using system location
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

    public Item(@NonNull UUID id, @NonNull Long amount, String contact,
                @NonNull Date borrowDate, @NonNull Date dueDate,
                boolean isToReceive, boolean isObject, String
                        objectDescription, int status) {
        this.mId = id;
        this.mAmount = amount;
        this.mContact = contact;
        this.mBorrowDate = borrowDate;
        this.mDueDate = dueDate;
        this.mIsToReceive = isToReceive;
        this.mIsObject = isObject;
        this.mStatus = status;
        this.objectDescription = objectDescription;
        checkAndSetStatus();
    }

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

    public String getSimpleDueDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(mDueDate);
        int year = cal.get(Calendar.YEAR);
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                Locale.getDefault());
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day + " " + month + " " + year;
    };

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

    public int getImageCodeFromStatus() {
        checkAndSetStatus();
        switch (mStatus){
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
}
