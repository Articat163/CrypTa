package ru.articat.crypta.Parsing;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

import ru.articat.crypta.Interfaces.ThemeInterface;
import ru.articat.crypta.Models.ThemeHeader;
import ru.articat.crypta.Models.ThemeModel;
import ru.articat.crypta.R;

public class ThemeParsing {

	private final Context context;
	private ProgressDialog pd;
	private final Resources res;
//	DoPreferences doPref;
	private Elements comments, nick, avatar, headAva, warnings, addTime;
	public Element text;
	private Element headNick, headerText, headTitle, pgs, headDate, headProfile;
	private String userId, headUserId;
//	Map<String, String> loginCookies=new HashMap<String, String>();
	private final ArrayList<ThemeModel> tema = new ArrayList<>();
	private ThemeHeader themeHeader = new ThemeHeader();
	private String headerVideo;
	private int pages;
	private final ThemeInterface ti;
	private final VolleyResponce vr;
	
	public ThemeParsing(Context context, ThemeInterface ti){
		this.context=context;
		this.res=context.getResources();
		this.ti=ti;
		vr=new VolleyResponce(context);
	//	this.url=url;
		
	//	doPref=new DoPreferences(context);
	//	String value=doPref.loadData(Constants.COOKIESVALUE, null);
	//	loginCookies.put("PHPSESSID", value);
		
	}
	
//	@Override
//	protected void onPreExecute() {
//		LoadDialog(res.getString(R.string.loading));
//	}
	

	public void doParse(final String url) {

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
				comments = doc.select("article.ipsComment_parent");
				tema.clear();

				// в цикле захватываем все данные какие есть на странице
				for (Element comment : comments) {

					// записываем в аррей лист
//					warnings = comment.select("span.comment.select");
					userId = comment.select("h3.cAuthorPane_author").select("a").attr("href");
					nick = comment.select("div.cAuthorPane > h3.cAuthorPane_author").select("a");
//                    Log.d(TAG, "requestResult: nick " + nick.text());
					avatar = comment.select("div.cAuthorPane_photo").select("img");
					text = comment.select("div[data-role=commentContent]").first();
					addTime = comment.select("time[datetime]");

					tema.add(new ThemeModel(url, text, userId, nick.text(), avatar.attr("src"), "", addTime.text()));
				}

				// аватар, ник автора темы, заголовок и текст темы
				headTitle = doc.select("div.ipsPageHeader").select("h1.ipsType_pageTitle").first();
				headAva = doc.select("div.ipsPhotoPanel_small").select("img");
				headNick = doc.select("div.ipsPageHeader").select("a.ipsType_break").first();
//				headProfile = doc.select("div.forum_bl").select("a[href]").first();
				headUserId = "";
//				headerText = doc.select("div.forum_bl").select("div[align=justify]").removeAttr("align").first();
				headDate = doc.select("time[datetime]").first();

//				pgs = doc.select("h3").first();

				themeHeader = new ThemeHeader(headUserId, headTitle.text(), headAva.attr("src"), headNick.text(), null, headDate.text());

				if (pd.isShowing()) pd.dismiss();

				String ePage = doc.select("div.ipsButtonBar > ul.ipsPagination").attr("data-pages");
				if(ePage.equals("")){// всего одна страница с ответами
                    pages = 1;
                }
				else{ // более одной страницы с ответами
                    pages = Integer.parseInt(doc.select("div.ipsButtonBar > ul.ipsPagination").attr("data-pages"));
                }

				ti.themeDataCallback(tema, themeHeader, pages);

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


//    public void parseFrameContent(final String url) {
//
//        LoadDialog(res.getString(R.string.loading));
//        vr.getSimpleResponce(url, new VolleyResponce.VolleyCallback(){
//
//            @Override
//            public void requestComplete() {
//                // TODO: Implement this method
//            }
//
//            @Override
//            public String requestResult(String res) {
//
//                Document doc;
//                doc = Jsoup.parse(res);
//                iframeContent = doc.select("div.ipsPad > h3");
//                // в цикле захватываем все данные какие есть на странице
//                for (Element comment : comments) {
//
//
//                }
//                return null;
//            }
//
//            @Override
//            public int requestError(int res) {
//                // TODO: Implement this method
//                return 0;
//            }
//
//        });
//    }


}
