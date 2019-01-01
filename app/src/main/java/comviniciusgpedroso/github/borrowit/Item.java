package comviniciusgpedroso.github.borrowit;

import java.util.Date;
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

    public Item(float amount, String contact, Date borrowDate, Date dueDate, boolean isToReceive) {
        mId = UUID.randomUUID();
        mAmount = amount;
        mContact = contact;
        mBorrowDate = borrowDate;
        mDueDate = dueDate;
        mStatus = DUE;
        mIsToReceive = isToReceive;
    }

    
}
