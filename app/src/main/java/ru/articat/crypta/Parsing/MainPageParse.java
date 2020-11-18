package ru.articat.crypta.Parsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ru.articat.crypta.Interfaces.MainPageParseInterface;
import ru.articat.crypta.R;
import ru.articat.crypta.Settings.Constants;

/**
 * Created by Юрий on 28.06.2017.
 */

public class MainPageParse {
    private final Context context;
    private ProgressDialog pd;
    private final Resources res;
    private Document doc;

    private final VolleyResponce vr;
    private final MainPageParseInterface mppi;

    public MainPageParse(Context context, MainPageParseInterface mppi){
        this.context=context;
        this.res=context.getResources();
        this.mppi=mppi;

        vr=new VolleyResponce(context);
    }


    public void doParse(final Boolean needDialog)
    {
        if (needDialog) LoadDialog(res.getString(R.string.updating));
        String url = "https://";
        vr.getSimpleResponce(url, new VolleyResponce.VolleyCallback(){

            @Override
            public void requestComplete()
            {
                // TODO: Implement this method
            }

            @Override
            public String requestResult(String res) {
                // TODO: Implement this method


                // определяем откуда будем брать данные
                doc = Jsoup.parse(res);

                if (needDialog) {
                    if (pd.isShowing()) pd.dismiss();
                }
                Constants.MAINPAGE = doc;
                mppi.mainPageParseCallback(); // callback
                return null;
            }

            @Override
            public int requestError(int res)
            {
                // TODO: Implement this method
                return 0;
            }
        });
    }


    private void LoadDialog(String caption)
    {
        pd = new ProgressDialog(context);
        pd.setMessage(caption);
        pd.show();
    }
}
