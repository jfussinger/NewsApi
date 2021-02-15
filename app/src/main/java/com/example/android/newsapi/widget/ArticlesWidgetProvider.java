package com.example.android.newsapi.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.example.android.newsapi.R;

public class ArticlesWidgetProvider extends AppWidgetProvider {

    //private AppWidgetTarget appWidgetTarget;
    //private AppWidgetManager mAppWidgetManager;
    //private ComponentName appWidget;
    //private int mCurrentWidgetId;

    public static final String TOAST_ACTION = "com.example.android.newsapi.TOAST_ACTION";
    public static final String EXTRA_ITEM = "com.example.android.newsapi.EXTRA_ITEM";
    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }
    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        if (intent.getAction().equals(TOAST_ACTION)) {
            int appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
            int viewIndex = intent.getIntExtra(EXTRA_ITEM, 0);
            Toast.makeText(context, "Touched view " + viewIndex, Toast.LENGTH_SHORT).show();
        }
        super.onReceive(context, intent);
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // update each of the widgets with the remote adapter
        for (int i = 0; i < appWidgetIds.length; ++i) {
            // Here we setup the intent which points to the StackViewService which will
            // provide the views for this collection.
            Intent intent = new Intent(context, ArticlesWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            // When intents are compared, the extras are ignored, so we need to embed the extras
            // into the data so that the extras will not be ignored.
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

            rv.setTextViewText(R.id.widgetAppName, context.getString(R.string.app_name));

            //https://futurestud.io/tutorials/glide-loading-images-into-notifications-and-appwidgets
            //https://stackoverflow.com/questions/47993270/widget-load-image-from-url-into-remote-view

            //Only used for loading a single image at a time on main thread; not used on remote view at for collection widget

            //AppWidgetTarget appWidgetTarget = new AppWidgetTarget(mContext, R.id.urlToImage, rv, mCurrentWidgetId) {
            //@Override
            //public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
            //super.onResourceReady(resource, transition);
            //}
            //};

            //RequestOptions options = new RequestOptions().
            //override(150, 250).placeholder(R.drawable.ic_gb).error(R.drawable.ic_gb);

            //Glide.with(mContext.getApplicationContext())
            //.asBitmap()
            //.load(articlesList.get(position).getUrlToImage())
            //.apply(options)
            //.into(appWidgetTarget);

            rv.setRemoteAdapter(appWidgetIds[i], R.id.list_view, intent);
            // The empty view is displayed when the collection has no items. It should be a sibling
            // of the collection view.
            rv.setEmptyView(R.id.list_view, R.id.empty_view);
            // Here we setup the a pending intent template. Individuals items of a collection
            // cannot setup their own pending intents, instead, the collection as a whole can
            // setup a pending intent template, and the individual items can set a fillInIntent
            // to create unique before on an item to item basis.
            Intent toastIntent = new Intent(context, ArticlesWidgetProvider.class);
            toastIntent.setAction(ArticlesWidgetProvider.TOAST_ACTION);
            toastIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            PendingIntent toastPendingIntent = PendingIntent.getBroadcast(context, 0, toastIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setPendingIntentTemplate(R.id.list_view, toastPendingIntent);
            appWidgetManager.updateAppWidget(appWidgetIds[i], rv);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}