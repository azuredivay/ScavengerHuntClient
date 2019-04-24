package com.codelads.scavengerhunt.Services;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.codelads.scavengerhunt.R;
import com.google.android.gms.maps.model.LatLng;

public class OfflineHelper
{
    public static PopupWindow handlePopup(View v, View pp, int type, Context c) //give preinflated pp view, 0 is center, 1 is right
    {
        PopupWindow pw = null;

        float dim = 0.8f;
        switch (type) //0 is mid, 1 is side
        {
            case 0:
                pw = new PopupWindow(pp, LinearLayout.LayoutParams.MATCH_PARENT, 700, true);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case 1:
                pw = new PopupWindow(pp, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                pw.showAtLocation(v, Gravity.RIGHT, 0, 0);
                dim = 0.5f;
                break;
            case 2:
                pw = new PopupWindow(pp, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case 3:
                pw = new PopupWindow(pp, ((int) ((c.getResources().getDisplayMetrics().widthPixels * 0.8))), LinearLayout.LayoutParams.MATCH_PARENT, true);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case 4:
                pw = new PopupWindow(pp, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case 5:
                pw = new PopupWindow(pp, LinearLayout.LayoutParams.MATCH_PARENT, ((int) ((c.getResources().getDisplayMetrics().heightPixels * 0.8))), true);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
            case 6:
                pw = new PopupWindow(pp, LinearLayout.LayoutParams.MATCH_PARENT, ((int) ((c.getResources().getDisplayMetrics().heightPixels * 0.6))), true);
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                break;
        }
        View container = pw.getContentView().getRootView();
        WindowManager wm = (WindowManager) pw.getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags |= WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = dim; //dim enough?
        if (wm != null) wm.updateViewLayout(container, p);
        PopupWindow finalPw = pw;
        pp.setOnTouchListener((v2, event) ->
        {
            v2.performClick();
            //v.setBackgroundColor(Color.RED);
            finalPw.dismiss();
            return true;
        });
        return pw;
    }

    public static void ShowWeb(String url, View v, Context c,int type)
    {
        PopupWindow pw = OfflineHelper.handlePopup(v, LayoutInflater.from(c).inflate(R.layout.webpopup,null),type,c);
        View cv = pw.getContentView();
        WebView popweb = cv.findViewById(R.id.webpwv);
        popweb.setWebViewClient(new WebViewClient());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)CookieManager.getInstance().setAcceptThirdPartyCookies(popweb, true);
        else CookieManager.getInstance().setAcceptCookie(true);
        popweb.getSettings().setJavaScriptEnabled(true);
        //popweb.getSettings().setUserAgentString("Mozilla/5.0 (Linux; Android 7.0; SM-G892A Build/NRD90M; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Mobile Safari/537.36 Edge/15.15063");
        popweb.loadUrl(url);
        Button cb = cv.findViewById(R.id.wclose);
        TextView ob = cv.findViewById(R.id.oibc);
        LinearLayout ec = cv.findViewById(R.id.edgec);
        cb.setOnClickListener(v1-> pw.dismiss());
        ob.setOnClickListener(v1-> c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(popweb.getUrl()))));
        ec.setOnClickListener(v1-> c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.microsoft.emmx"))));
    }

    public static boolean IsWithinGeoFence(double lat1, double lat2, double lon1, double lon2, double el1, double el2)
    {
        //use self location? or take params?
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; //to metres

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        //return Math.sqrt(distance) < 15;
        return true;
    }
}
