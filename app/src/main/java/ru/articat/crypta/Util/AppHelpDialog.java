package ru.articat.crypta.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFlat;

import ru.articat.crypta.Fragments.ThemeFragment;
import ru.articat.crypta.R;

/**
 * Created by Юрий on 13.10.2017.
 */

public class AppHelpDialog {

    private Context cnt;
    private Activity act;
    private SharedPreferences sharedPrefs;
    Vibrator vb;
    Resources res;


    public AppHelpDialog(Activity a){
//        this.cnt=c;
        this.act=a;
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(act);
        vb = (Vibrator)a.getSystemService(Context.VIBRATOR_SERVICE);
        res=a.getResources();
    }

    public void showDialog(){
        RelativeLayout dialoglayout;
        dialoglayout = (RelativeLayout)act.getLayoutInflater().inflate(R.layout.app_help_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setView(dialoglayout);
        builder.setCancelable(true);
        final AlertDialog alert;
        alert=builder.show();
        ButtonFlat btnBeer = (ButtonFlat)dialoglayout.findViewById(R.id.dialogButtonOk); // угостить пивком

        btnBeer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (sharedPrefs.getBoolean("key_taktil", false)) {
                    vb.vibrate(res.getInteger(R.integer.taktil));
                }
                alert.dismiss();
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://money.yandex.ru/to/410015323448809"));
                    act.startActivity(browserIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(act, "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }
        });
        ButtonFlat btnComment = (ButtonFlat)dialoglayout.findViewById(R.id.dialogButtonComment); // оставить комментарий
        /**     тема приложения на сайте
         *
         */
        btnComment.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (sharedPrefs.getBoolean("key_taktil", false)) {
                    vb.vibrate(res.getInteger(R.integer.taktil));
                }
                alert.dismiss();
                ThemeFragment frt = new ThemeFragment();
                Bundle bundle = new Bundle();
//                bundle.putString("link", "?p=bbs&id=6&cmd=read&ms=1070428&page=1");

                frt.setArguments(bundle);

//                ((MainActivity)act).FragmentChanger(frt);
            }
        });
    }
}
