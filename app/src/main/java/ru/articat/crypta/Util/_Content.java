//package ru.articat.crypta.Util;
//
//import android.content.Context;
//import android.text.Html;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;
//import com.bumptech.glide.load.resource.drawable.GlideDrawable;
//import com.bumptech.glide.request.RequestListener;
//import com.bumptech.glide.request.target.Target;
//
//import org.jsoup.nodes.Element;
//import org.jsoup.nodes.Node;
//
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//
//import javax.net.ssl.HttpsURLConnection;
//import javax.net.ssl.SSLSocketFactory;
//
//import ru.articat.crypta.ImageGetter;
//import ru.articat.crypta.Parsing.TLSSocketFactory;
//import ru.articat.crypta.R;
//
//import static ru.articat.crypta.Settings.Constants.TAG;
//
//public class _Content {
//
//    private final Context context;
//    private final LinearLayout main;
//    //	private ImageLoader imageLoader;
//    private final ImageGetter imgGetter;
//    //	private String url;
//    private String video;
//    private final ViewGroup parent;
//
//
//    public _Content(Context c, ViewGroup parent) {
//        this.context = c;
//        this.parent = parent;
//        imgGetter = new ImageGetter(context);
//        main = new LinearLayout(context);
////		imageLoader = ImageLoader.getInstance(); // Получили экземпляр
////		imageLoader.init(ImageLoaderConfiguration.createDefault(context)); // Проинициализировали конфигом по умолчанию
//
//        final SSLSocketFactory sslSocketFactory;
//        try {
//            sslSocketFactory = new TLSSocketFactory();
//            HttpsURLConnection.setDefaultSSLSocketFactory(sslSocketFactory);
//        } catch (KeyManagementException ignored) {
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public View getLayout(Element comment) {
//
//        this.video = video;
//        //		String block1 = comment;
//
//        main.setOrientation(LinearLayout.VERTICAL);
//
////			DisplayImageOptions picOptions = new DisplayImageOptions.Builder()
////					.showImageOnLoading(R.drawable.loading)
////					.showImageForEmptyUri(R.drawable.no_img)
////					.showImageOnFail(R.drawable.no_img)
////					.cacheInMemory(true)
////					.cacheOnDisk(true)
////					.considerExifParams(true)
////					.bitmapConfig(Bitmap.Config.RGB_565)
////					.build();
//
//
//        //*******************************************
//        //*									    	*
//        //* расставляем по местам цитаты и картинки *
//        //*									    	*
//        //*******************************************
//
////        if (comment.isEmpty()) return main;
////
////        while (comment.contains("img/smile")) { // есть смайлы, надо подменить
////            String imgNumber = comment.substring(comment.indexOf("img/smile") + 10, comment.indexOf("img/smile") + 12);
////            comment = comment.replace("/smile/" + imgNumber + ".gif", imgNumber);
////        }
////
////        while (comment.contains(">Цитата<")) { // удаляем слово Цитата
////            comment = comment.replace("<font size=\"1px\">Цитата</font>", "");
////        }
////
////        while (comment.contains("&nbsp")) { // удаляем неразрывные пробелы
////            comment = comment.replace("&nbsp;", "");
////        }
//
//        StringBuilder builder = new StringBuilder();
////        Document doc;
////        doc = Jsoup.parse(comment.text());
////        Element el_1 = doc.select("div").first();
//        if (comment != null) {
////            Log.d(TAG, comment.text());
//            try {
//                for (Node node : comment.childNodes()) {
////                    Log.d(TAG, "----------comment.childNodes(): " + node.toString());
//                    // есть цитаты?
//                    if (node.nodeName().contains("quote")) {
////                        main.addView(getTextView(builder.toString()));
////                        builder = new StringBuilder();
//                        View quoteView = LayoutInflater.from(context).inflate(R.layout.tema_quote, parent, false);
//                        TextView tvQuote = (TextView) quoteView.findViewById(R.id.txtQuote);
//                        TextView tvQuoteAuthor = (TextView) quoteView.findViewById(R.id.txtQuoteAuthor);
//                        Element quoteText = (Element) node;
//                        tvQuoteAuthor.setText((quoteText.select("div.ipsQuote_citation").text()));
//                        tvQuote.setText(quoteText.select("div.ipsQuote_contents").text());
////                        tvQuote.setText(Html.fromHtml(node.outerHtml()));
////                        Log.d(TAG, "****** QUOTE: " + quoteText.toString());
//                        main.addView(quoteView);
//
//                    }
//
//                    // есть фреймы?
//                    else if (node.nodeName().contains("iframe")){
//                        Log.d(TAG, "****** iFRAME ******* ");
////                        Log.i(TAG, "node.toString(): " + node.toString());
////                        Element fContent = ((Element)node).select("iframe").first();
//                        String relHref = node.attr("data-embed-src");
////                        Log.i(TAG, "relHref: " + relHref);
////                        Log.i(TAG, "CONVERTER: " + new Converter().convertCyrillic(relHref));
//                        View frameView = LayoutInflater.from(context).inflate(R.layout.tema_frame_content, parent, false);
//                        TextView tvFrame = (TextView) frameView.findViewById(R.id.txtFrameTitle);
////                        String title = relHref.substring();
//                        tvFrame.setText(new Converter().convertCyrillic(relHref));
//                        main.addView(frameView);
////                        main.addView(geFrameContentView(relHref));
////                        builder = new StringBuilder();
////                        builder.append(fTitle.text());
//
////                        getWebView();
//                    }
//
//                    else if (node.nodeName().equals("p")){
//                        Element pContent = ((Element)node).select("a.ipsAttachLink_image").first();
//                        if (pContent != null /*&& !pContent.attr("src").contains("emoticons")*/){
////                            Log.d(TAG, "****** img ******* " + pContent.attr("href"));
//                            main.addView(getTextView(builder.toString()));
////                            builder = new StringBuilder();
//
//                            ImageView imgView = new ImageView(context);
//                            final String imgUrl = pContent.attr("href");
//
//                            main.addView(imgView);
//                            final ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
//                            main.addView(progressBar);
//                            // URL картинки (н-р: "http://site.com/image.png", "file:///mnt/sdcard/img/image.jpg")
//                            // Запустили асинхронный показ картинки
////                        imageLoader.displayImage(node.absUrl("src"), imgView, picOptions);
//
////                        Log.i(TAG, "node.absUrl(src).trim()|" + node.absUrl("src").trim() + "|");
////						Glide.with(context).load(node.absUrl("src").trim()).into(imgView);
//                            Glide.with(context)
//                                    .load(imgUrl)
//                                    .thumbnail(.5f)
//                                    .listener(new RequestListener<String, GlideDrawable>() {
//                                        @Override
//                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                            progressBar.setVisibility(View.GONE);
//                                            Log.e(TAG, "Glide onException: " + e);
//                                            return false;
//                                        }
//
//                                        @Override
//                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                            progressBar.setVisibility(View.GONE);
//                                            return false;
//                                        }
//                                    })
//                                    .error(R.drawable.no_img)
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .into(imgView);
//                        }
//                        else {
////                            builder.append(Html.fromHtml(node.outerHtml()));
//                            builder.append(Html.fromHtml(node.outerHtml()));
//                        }
//                    }
//
//
//                    // есть картинки?
////                    else if (node.nodeName().contains("img")) {
//////                  else if (node.nodeName().equals("img") && node.absUrl("src").contains("http")) {
////                        Log.d(TAG, "****** img ******* ");
////                        main.addView(getTextView(builder.toString()));
////                        builder = new StringBuilder();
////
////                        ImageView imgView = new ImageView(context);
////                        final String imgUrl = node.absUrl("src");
////
////                        main.addView(imgView);
////                        final ProgressBar progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
////                        main.addView(progressBar);
////                        // URL картинки (н-р: "http://site.com/image.png", "file:///mnt/sdcard/img/image.jpg")
////                        // Запустили асинхронный показ картинки
//////                        imageLoader.displayImage(node.absUrl("src"), imgView, picOptions);
////
//////                        Log.i(TAG, "node.absUrl(src).trim()|" + node.absUrl("src").trim() + "|");
//////						Glide.with(context).load(node.absUrl("src").trim()).into(imgView);
////                        Glide.with(context)
////                                .load(imgUrl)
////                                .listener(new RequestListener<String, GlideDrawable>() {
////                                    @Override
////                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
////                                        progressBar.setVisibility(View.GONE);
////                                        Log.e(TAG, "Glide onException: " + e);
////                                        return false;
////                                    }
////
////                                    @Override
////                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
////                                        progressBar.setVisibility(View.GONE);
////                                        return false;
////                                    }
////                                })
////                                .error(R.drawable.no_img)
////                                .diskCacheStrategy(DiskCacheStrategy.ALL)
////                                .into(imgView);
////                    }
//
////                    else builder.append(Html.fromHtml(node.outerHtml()));
//                    else builder.append(Html.fromHtml(node.outerHtml()));
//
//                } // end for
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(context, R.string.unpossible_view, Toast.LENGTH_SHORT).show();
//            }
//        }
//        else {
//            builder.append("");
//        }
//        main.addView(getTextView(builder.toString()));
//        //		builder = new StringBuilder();
//
//
//        //***************************************
//        //*										*
//        //* добавляем видео (DISABLED 25.10.20) *
//        //*										*
//        //***************************************
//
////        if (!this.video.equals("")) { // есть видео в шапке темы
////
//////				btnVideo.setOnClickListener(new View.OnClickListener() {
//////					@Override
//////					public void onClick(View v) {
//////						// TODO Auto-generated method stub
//////						//	url = video.substring(26, video.indexOf("?fs"));
//////						url = Converter.extractYTId(video); // получаем ID видео из url
//////						Log.i(TAG, "Content.java videoUrl= " + url);
//////						// вызываем метод из MainActivity для просмотра видео
////////						((MainActivity) context).PlayVideo(url);
//////					}
//////				});
////
////            // Creating a new RelativeLayout
////            RelativeLayout relativeLayout = new RelativeLayout(context);
////
////            // YouTubeThumbnaile
////            YouTubeThumbnailView ytView = new YouTubeThumbnailView(context);
////
////            ytView.initialize(DeveloperKey.DEVELOPER_KEY, new YouTubeThumbnailView.OnInitializedListener() {
////                @Override
////                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, final YouTubeThumbnailLoader youTubeThumbnailLoader) {
////                    youTubeThumbnailLoader.setVideo(Converter.extractYTId(Content.this.video));
////                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
////                        @Override
////                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
////                            youTubeThumbnailLoader.release();
////                        }
////
////                        @Override
////                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
////
////                        }
////                    });
////                }
////
////                @Override
////                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
////
////                }
////            });
////
////            // Defining the layout parameters of the TextView
////            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
////                    RelativeLayout.LayoutParams.MATCH_PARENT,
////                    RelativeLayout.LayoutParams.WRAP_CONTENT);
////            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
////
////            // Setting the parameters
//////				videoGlImage.setLayoutParams(lp);
////            ytView.setLayoutParams(lp);
////            // Adding the TextView to the RelativeLayout as a child
//////				relativeLayout.addView(videoGlImage);
////            relativeLayout.addView(ytView);
////
////            ImageView videoPlayButton = new ImageView(context);
////            videoPlayButton.setImageResource(R.drawable.yt_player_frame);
////            videoPlayButton.setLayoutParams(lp);
////            videoPlayButton.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    ((MainActivity) context).watchYoutubeVideo(Converter.extractYTId(Content.this.video));
////                }
////            });
////            relativeLayout.addView(videoPlayButton);
////
////            main.addView(relativeLayout);
////
////        }
//
//
//        return main;
//    }
//
//    private View geFrameContentView(String t) {
//
////        new FrameContentParsing(context, new iFrameContentInterface() {
////            @Override
////            public void iframeDataCallback(String title) {
////                tv.setText(title);
////            }
////        }).doParseContent(t);
//        View frameView = LayoutInflater.from(context).inflate(R.layout.tema_frame_content, parent, false);
//        TextView tvFrame = (TextView) frameView.findViewById(R.id.txtFrameTitle);
//        tvFrame.setText(t);
//
//        return tvFrame;
//    }
//
//
//    //
//    private View getTextView(String t) {
//
//        TextView tv = new TextView(context);
//        tv.setTextIsSelectable(true);
////		tv.setAlpha(.87f);
//        tv.setMaxLines(100);
//
//        try {
//            tv.setText(Html.fromHtml(t, imgGetter, null));
////            tv.setText((new HtmlSpanner()).fromHtml(t));
//        } catch (Exception e) {
//            e.printStackTrace();
//            tv.setText(t);
//        }
//
//        return tv;
//    }
//
//    /** Trims trailing whitespace. Removes any of these characters:
//     * 0009, HORIZONTAL TABULATION
//     * 000A, LINE FEED
//     * 000B, VERTICAL TABULATION
//     * 000C, FORM FEED
//     * 000D, CARRIAGE RETURN
//     * 001C, FILE SEPARATOR
//     * 001D, GROUP SEPARATOR
//     * 001E, RECORD SEPARATOR
//     * 001F, UNIT SEPARATOR
//     * @return "" if source is null, otherwise string with all trailing whitespace removed
//     */
//
//
//}
