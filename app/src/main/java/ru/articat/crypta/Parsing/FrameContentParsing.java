package ru.articat.crypta.Parsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ru.articat.crypta.Interfaces.iFrameContentInterface;
import ru.articat.crypta.R;


public class FrameContentParsing {

    private final Context context;
    private final iFrameContentInterface ifi;
    private ProgressDialog pd;
    private final Resources res;
    private final VolleyResponce vr;
    private Elements iframeContent;

    public FrameContentParsing(Context context, iFrameContentInterface ifi){
        this.context = context;
        this.ifi = ifi;
        this.res=context.getResources();
        vr=new VolleyResponce(context);
    }

    public void doParseContent(final String url) {

        LoadDialog(res.getString(R.string.loading));
        vr.getSimpleResponce(url, new VolleyResponce.VolleyCallback(){

            @Override
            public void requestComplete() {
                // TODO: Implement this method
            }

            @Override
            public String requestResult(String res) {

                Document doc;
                doc = Jsoup.parse(res);
                iframeContent = doc.select("div.ipsPad > h3");

                ifi.iframeDataCallback(iframeContent.text());
                return null;
            }

            @Override
            public int requestError(int res) {
                // TODO: Implement this method
                return 0;
            }

        });
    }


    private void LoadDialog(String caption) {
        pd = new ProgressDialog(context);
        pd.setMessage(caption);
        pd.show();
    }

}
