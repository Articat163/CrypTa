package ru.articat.crypta.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;

import ru.articat.crypta.BuildConfig;
import ru.articat.crypta.R;

/**
 * Created by Юрий on 13.10.2017.
 */

public class AppVersionDialog {

    private Context cnt;
    private Activity act;
    private SharedPreferences sharedPrefs;
    Vibrator vb;
    Resources res;


    public AppVersionDialog(Activity a){
//        this.cnt=c;
        this.act=a;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(act);
        vb = (Vibrator)a.getSystemService(Context.VIBRATOR_SERVICE);
        res = a.getResources();
    }

    public void showDialog(){
        RelativeLayout dialoglayout;
        dialoglayout = (RelativeLayout)act.getLayoutInflater().inflate(R.layout.app_version_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setView(dialoglayout);
        TextView tvVersion=(TextView)dialoglayout.findViewById(R.id.textViewVersion);
        tvVersion.setText(BuildConfig.VERSION_NAME);
        builder.setCancelable(true);
        final AlertDialog alert;
        alert=builder.show();
        ButtonFlat btnDisk= (ButtonFlat)dialoglayout.findViewById(R.id.dialogButtonDisk); // Яндекс.Диск

        btnDisk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (sharedPrefs.getBoolean("key_taktil", false)) {
                    vb.vibrate(res.getInteger(R.integer.taktil));
                }
                alert.dismiss();

                try {
//                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yadi.sk/d/M0kt6sWqwcFHh"));
//                    act.startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(act, "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });

        ButtonFlat btnClose= (ButtonFlat)dialoglayout.findViewById(R.id.dialogButtonClose); // закрыть

        btnClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (sharedPrefs.getBoolean("key_taktil", false)) {
                    vb.vibrate(res.getInteger(R.integer.taktil));
                }
                alert.dismiss();

            }
        });
    }
}
