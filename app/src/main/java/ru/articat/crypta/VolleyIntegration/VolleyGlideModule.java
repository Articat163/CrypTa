//package ru.articat.nicy.VolleyIntegration;
//
//import android.content.Context;
//
//import com.bumptech.glide.Glide;
//import com.bumptech.glide.GlideBuilder;
//import com.bumptech.glide.integration.volley.VolleyUrlLoader;
//import com.bumptech.glide.load.model.GlideUrl;
//import com.bumptech.glide.module.GlideModule;
//
//import java.io.InputStream;
//
///**
// * A {@link GlideModule} implementation to replace Glide's default
// * {@link java.net.HttpURLConnection} based {@link com.bumptech.glide.load.model.ModelLoader} with a Volley based
// * {@link com.bumptech.glide.load.model.ModelLoader}.
// *
// * <p>
// *     If you're using gradle, you can include this module simply by depending on the aar, the module will be merged
// *     in by manifest merger. For other build systems or for more more information, see
// *     {@link GlideModule}.
// * </p>
// */
//public class VolleyGlideModule implements GlideModule {
//    @Override
//    public void applyOptions(Context context, GlideBuilder builder) {
//        // Do nothing.
//    }
//
//    @Override
//    public void registerComponents(Context context, Glide glide) {
//        glide.register(GlideUrl.class, InputStream.class, new VolleyUrlLoader.Factory(context));
//    }
//}
