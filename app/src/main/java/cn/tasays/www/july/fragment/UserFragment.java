package cn.tasays.www.july.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import cn.tasays.www.july.R;

/**
 * user信息
 */

public class UserFragment extends BaseFragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //添加布局文件到容器
        view = inflater.inflate(R.layout.user,container,false);

        init_view(view);

        return  view;
    }

    /**
     * 初始化用户数据 api请求获得用户信息接口
     */
    public void init_view(View view)
    {
        //请求地址
        String getUrl = "http://www.tasays.cn/api/user";

    }
}