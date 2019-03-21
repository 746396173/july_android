package cn.tasays.www.july.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import cn.tasays.www.july.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.index,container,false);

        initView(view);

        return  view;
    }

    public void initView(View view)
    {
        WebView webView = (WebView) view.findViewById(R.id.index_webveiw);
        webView.getSettings().setJavaScriptEnabled(true);//开启javascript
        webView.getSettings().setDomStorageEnabled(true);
        webView.loadUrl("http://116.196.125.67:8080/#/");
    }
}
