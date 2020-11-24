package ru.articat.crypta.Models;

public class SecionModel {
	public String urlTema;
	public String title;
    public String pages;
	public String nick;
	public String date;
	public String user;
	public String userDate;
	public String userTime;
	public String city;
	public String answers;
	public String views;
	public Boolean video;
	//public String content;

    public SecionModel(){
        super();
    }
	
//	public SecionModel(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}
//
//	public SecionModel(Context context, AttributeSet attrs, int defStyle) {
//		super(context, attrs, defStyle);
//	}
//
    public SecionModel(String url, String title, String pages, String nick, String date, String user, String userDate, String userTime, String city, String answers, String views, Boolean video) {
        super();
		this.urlTema=url;
		this.title = title;
        this.pages = pages;
		this.nick = nick;
		this.date = date;
        this.user = user;
		this.userDate = userDate;
		this.userTime = userTime;
		this.city=city;
		this.answers=answers;
		this.views=views;
		this.video=video;
    }
}

