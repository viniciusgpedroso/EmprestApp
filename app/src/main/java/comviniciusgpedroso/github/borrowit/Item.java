package comviniciusgpedroso.github.borrowit;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Item Class
 * Deals with the data model for the borrow items.
 */
public class Item {
    // Status constants
    private static final int DUE = 0;
    private static final int OVERDUE = 1;
    private static final int DONE = 2;

    private UUID mId;
    private float mAmount; // TODO Convert this float to Currency using system location
    private String mContact;
    private Date mBorrowDate;
    private Date mDueDate;
    private int mStatus;
    private boolean mIsToReceive;

    public Item(float amount, String contact, Date borrowDate, Date dueDate, boolean isToReceive,
    int temporaryImage) {
        mId = UUID.randomUUID();
        mAmount = amount;
        mContact = contact;
        mBorrowDate = borrowDate;
        mDueDate = dueDate;
        mStatus = temporaryImage;
        mIsToReceive = isToReceive;
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

    public float getAmount() {
        return mAmount;
    }

    public String getValue() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        return df.format(mAmount);
    }

    public void setAmount(float amount) {
        mAmount = amount;
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
        String month = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
        int day = cal.get(Calendar.DAY_OF_MONTH);

        return day + " " + month + " " + year;
    };

    public void setDueDate(Date dueDate) {
        mDueDate = dueDate;
    }

    public int getStatus() {
        return mStatus;
    }

    public boolean isToReceive() {
        return mIsToReceive;
    }

    public void setToReceive(boolean toReceive) {
        mIsToReceive = toReceive;
    }
}
