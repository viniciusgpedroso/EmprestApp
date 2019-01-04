package comviniciusgpedroso.github.borrowit;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;
import java.util.UUID;

/**
 * Created by endsieg on 04/01/19.
 */

// Converts complex data for Room Database
public class Converters {
    @TypeConverter
    public static Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static String UUIDtoString(UUID uuid) {
        return uuid == null ? null : uuid.toString();
    }

    @TypeConverter
    public static UUID stringToUUID(String string) {
        return string == null ? null : UUID.fromString(string);
    }
}
