package za.co.thamserios.basilread.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import za.co.thamserios.basilread.models.*;

import static nl.qbusict.cupboard.CupboardFactory.cupboard;

/**
 * Created by robson on 2017/03/07.
 */

public class BasilReadDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "basilreadprodv4.db";
    private static final int DATABASE_VERSION = 4;

    public BasilReadDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    static {
        // register our models
        cupboard().register(BreakdownLogs.class);
        cupboard().register(BreakdownTypes.class);
        cupboard().register(Crews.class);
        cupboard().register(EndOfShift.class);
        cupboard().register(OperationsUser.class);
        cupboard().register(StartOfShift.class);
        cupboard().register(ProductionRecord.class);
        cupboard().register(ProductionType.class);
        cupboard().register(Shift.class);
        cupboard().register(SiteConfig.class);
        cupboard().register(StandingLogs.class);
        cupboard().register(StandingTypes.class);
        cupboard().register(Autocomplete.class);
        cupboard().register(CachedIndex.class);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // this will ensure that all tables are created
        cupboard().withDatabase(sqLiteDatabase).createTables();
        // add indexes and other database tweaks in this method if you want
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // this will upgrade tables, adding columns and new tables.
        // Note that existing columns will not be converted
        cupboard().withDatabase(sqLiteDatabase).upgradeTables();
        // do migration work if you have an alteration to make to your schema here
    }
}
