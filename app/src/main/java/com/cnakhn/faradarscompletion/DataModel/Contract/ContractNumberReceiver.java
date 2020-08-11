package com.cnakhn.faradarscompletion.DataModel.Contract;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;

public class ContractNumberReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
        if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            ContractDBHelper dbHelper = new ContractDBHelper(context);
            SQLiteDatabase database = dbHelper.getWritableDatabase();
            dbHelper.saveNumber(number, database);
            dbHelper.close();
        }

        Intent intent1 = new Intent(Contract.UPDATE_UI_FILTER);
        context.sendBroadcast(intent1);
    }
}
