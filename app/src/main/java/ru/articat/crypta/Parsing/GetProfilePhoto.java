package ru.articat.crypta.Parsing;

import android.app.ProgressDialog;
import android.content.Context;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ru.articat.crypta.Settings.AppSettings;
import ru.articat.crypta.Settings.DoPreferences;

public class GetProfilePhoto //extends AsyncTask<String, Void, String> {
{
	private Context context;

    public GetProfilePhoto(Context c){
		this.context=c;
	}
	
//	public GetProfilePhoto(Context c, String l){
//		this.context=c;
	//	this.profileLink=l;
//	}
	
	ProgressDialog pd;
//	public String profileLink;
//	String url;
private Document doc;
	private Element foto;
	private String fotoUrl="";
	
	private final AppSettings appSettings= new AppSettings(context);
	
	public void init(String url)
	{
        DoPreferences doPref = new DoPreferences(context);
	//	String value=doPref.loadData(Constants.COOKIESVALUE, null);
	//	loginCookies.put("PHPSESSID", value);
	//	LoadDialog("Profile Photo");
//	}
	// Метод выполняющий запрос в фоне		
//	@Override
//	protected String doInBackground(String... arg) {

		// класс который захватывает страницу


		VolleyResponce vr= new VolleyResponce(context);
		vr.getSimpleResponce(url, new VolleyResponce.VolleyCallback(){

				@Override
				public void requestComplete()
				{
					// TODO: Implement this method
				}

				@Override
				public String requestResult(String res)
				{
					// TODO: Implement this method
					// определяем откуда будем брать данные
					//	Log.w("mplog", "Jsoup.connect(profileLink) "+profileLink);
					doc = Jsoup.parse(res);
					//	.cookies(loginCookies)
					//	.get();


					foto=doc.select("table.p4").select("img.con1").first();

					//	Log.d("mplog", "User foto= "+ foto.toString());
					if (foto!=null){
						fotoUrl="https://"+foto.attr("src");
					}
					else {
						fotoUrl="";
					} // нет фотки






//	}

//	@Override
//	protected void onPostExecute(String result) {

					// после запроса отключаем прогресс диалог 
					//	if (pd.isShowing()) {
					//		pd.dismiss();
					// }
					appSettings.profile_photo_url=fotoUrl;
					appSettings.getBitmapFromUrl(context, fotoUrl);
					//	Log.w("mplog", "GetProfilePhoto fotoUrl: "+fotoUrl);



//	} // end OnPost
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
//	protected void onPreExecute() {
		
//	@Override
//	public void LoadDialog(String caption)
//	{
//		pd = new ProgressDialog(context);
//		pd.setTitle(caption+" loading...");
//		pd.setMessage("Please wait...");
//		pd.show();
//	}
	
//	public void getUserPhotoUrl(String link){
//		
//		new GetProfilePhoto(context, link).execute();
//
//	}

}
