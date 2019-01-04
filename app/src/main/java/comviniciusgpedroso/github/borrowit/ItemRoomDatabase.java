package comviniciusgpedroso.github.borrowit;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by endsieg on 04/01/19.
 */

@Database(entities = {Item.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ItemRoomDatabase extends RoomDatabase {

    public abstract ItemDao itemDao();

    private static ItemRoomDatabase INSTANCE;
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };


    static ItemRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ItemRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ItemRoomDatabase.class, "item_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Populate the database in the background.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final ItemDao mItemDao;
        private final ArrayList<Item> placeholderData = createsPlaceHolderData();

        PopulateDbAsync(ItemRoomDatabase db) {
            mItemDao = db.itemDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mItemDao.deleteAll();

            // Populates with placeholder data
            for (int i = 0; i <= placeholderData.size() - 1; i++) {
                mItemDao.insert(placeholderData.get(i));
            }
            return null;
        }

        private ArrayList<Item> createsPlaceHolderData() {
            ArrayList<Item> itemList = new ArrayList<>();
            Date firstDate = new Date();
            Date secondDate = new Date();
            itemList.add(new Item(15f, "Joao Silva", firstDate, secondDate, true, false, R.drawable
                    .ic_pay_checked));
            itemList.add(new Item(5f, "Maria Souza", firstDate, secondDate, true, false, R.drawable
                    .ic_pay_after_due_date));
            itemList.add(new Item(55f, "Jose Oliveira", firstDate, secondDate, true, false, R
                    .drawable
                    .ic_pay_before_due_date));

            return itemList;
        }
    }


}


