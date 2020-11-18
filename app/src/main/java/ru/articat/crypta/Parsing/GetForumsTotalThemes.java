package ru.articat.crypta.Parsing;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import ru.articat.crypta.DataBases.DBHelperForumList;
import ru.articat.crypta.Interfaces.GetTotalThemesInterface;
import ru.articat.crypta.Settings.DoPreferences;

import static ru.articat.crypta.Settings.Constants.TAG;


public class GetForumsTotalThemes extends AsyncTask<Void, Integer, ArrayList>{

	Context context;
	//	Map<String, String> loginCookies=new HashMap<String, String>();
	DoPreferences doPref;
	DBHelperForumList dbHelper;
	Elements elements;
	Element floodElement;
	ArrayList al;
	GetTotalThemesInterface gtti;


	public GetForumsTotalThemes(Context c, GetTotalThemesInterface gtti) {
		this.context=c;
		this.gtti=gtti;
		doPref=new DoPreferences(context);
		dbHelper=new DBHelperForumList(context);
		al= new ArrayList();
		Log.i(TAG, "GetForumsTotalThemes");
	}


	@Override
	protected void onPreExecute() {
		al.clear();
	}


	@Override
	protected ArrayList doInBackground(Void[] p1){
		// TODO: Implement this method
		//	String value=doPref.loadData(Constants.COOKIESVALUE, null);
		//	loginCookies.put("PHPSESSID", value);

		try
		{

			Document doc = Jsoup.connect("https://cryptotalk.org/")
					.get();

			// div class="round2" список форумов
			elements=doc.select("div.ipsfocusBox");
//			Log.i(TAG, "elements" + elements.toString());
			Elements e=elements.select("dt.ipsDataItem_stats_number");
//			int i=1;
			for(Element elm:e){
				try{
//					if(i%2!=0){
					al.add(Integer.parseInt(elm.text().trim()));
					Log.i(TAG, "i" + elm.text().trim());
//				}
					//	elm.select("td.zeb1[align=center]");
					//	Log.i(TAG, "td.zeb1"+elm.text());
					//	else Log.d(TAG, "i= "+i+": "+elm.text());
				}
				catch (Exception ex){
					Log.e(TAG, "Exeption: "+ ex);
				}
//				i++;
				//writeTotalToBase();
			}


		}
		catch (IOException e){
			Log.e(TAG, "GetAllMessages Exeption: "+ e);
		}
		return al;
	}



	@Override
	protected void onPostExecute(ArrayList list) {
//		Log.e(TAG, "onPostExecute");
//		for(int i=0; i<=list.size()-1; i++){
//			Log.i(TAG, "i= "+i+": "+list.get(i));
//		}
		gtti.totalThemesCallback(list);
	}

	public void writeTotalToBase(){

	}

}