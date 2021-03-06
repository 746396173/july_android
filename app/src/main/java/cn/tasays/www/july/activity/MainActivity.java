package cn.tasays.www.july.activity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.tasays.www.july.R;
import cn.tasays.www.july.fragment.BookFragment;
import cn.tasays.www.july.fragment.HappyFragment;
import cn.tasays.www.july.fragment.IndexFragment;
import cn.tasays.www.july.fragment.UserFragment;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    //fragement切换
    protected LinearLayout indexll;
    protected LinearLayout happyll;
    protected LinearLayout bookll;
    protected LinearLayout userll;


    public IndexFragment indexfm = new IndexFragment();//首页fm
    public HappyFragment happyfm;//新闻资讯  这里不进行初始化动作
    protected BookFragment  bookfm  =  new BookFragment();//创建bookfragment
    protected UserFragment  userfm  = new UserFragment(); //创建会员中心 fragment
    private long mExitTime;
    private String myFragment = "";//保存当前选中的是哪个fragment用以判断

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if(url != null && url.length() != 0){
            happyfm = new HappyFragment(url);
        } else {
            happyfm = new HappyFragment("base");
        }

        String showFragment = intent.getStringExtra("showFragment");
        if(showFragment==null){
            //2.初始化fragment 默认展示主页fragment
            //默认展示首页
            this.getSupportFragmentManager()
                    //fragment都是以事物的方式添加 删除的
                    .beginTransaction()
                    .add(R.id.container_content,indexfm)//默认展示主页面
                    //.replace(R.id.container_content,indexfm)//replace 删除所有fragment 后添加指定1个
                    .add(R.id.container_content,happyfm)
                    .add(R.id.container_content,bookfm)
                    .add(R.id.container_content,userfm)
                    .hide(bookfm) //隐藏其他fm
                    .hide(happyfm)
                    .hide(userfm)
                    //事物添加
                    .commit();
            myFragment = "index";

            ImageView imageView = findViewById(R.id.menu_index_img);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.index_current));
            TextView textView = findViewById(R.id.menu_index_text);
            setCurrentStyle(textView);
        }else{
            switch (showFragment){
                case "articleList":
                    //默认展示文章
                    this.getSupportFragmentManager()
                            //fragment都是以事物的方式添加 删除的
                            .beginTransaction()
                            .add(R.id.container_content,indexfm)//默认展示主页面
                            //.replace(R.id.container_content,indexfm)//replace 删除所有fragment 后添加指定1个
                            .add(R.id.container_content,happyfm)
                            .add(R.id.container_content,bookfm)
                            .add(R.id.container_content,userfm)
                            .hide(bookfm) //隐藏其他fm
                            .hide(indexfm)
                            .hide(userfm)
                            //事物添加
                            .commit();
                    myFragment = "happy";

                    ImageView imageView = findViewById(R.id.menu_happy_img);
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.woman_current));
                    TextView textView = findViewById(R.id.menu_happy_text);
                    setCurrentStyle(textView);
                    break;
            }
        }

        //添加activity到栈中
        add(MainActivity.this);

        registerMessageReceiver();  // 注册广播接收
    }


    //初始化视图 注册事件
    public  void  initView()
    {
        indexll = (LinearLayout) this.findViewById(R.id.menu_index);
        happyll = (LinearLayout) this.findViewById(R.id.menu_happy);
        bookll  = (LinearLayout) this.findViewById(R.id.menu_book);
        userll  = (LinearLayout) this.findViewById(R.id.menu_me);
        indexll.setOnClickListener(this);
        happyll.setOnClickListener(this);
        bookll.setOnClickListener(this);
        userll.setOnClickListener(this);
    }

    //重写onclick方法
    @Override
    public void onClick(View view)
    {
        ImageView imageView;
        TextView textView;
        switch (view.getId())
        {
            case R.id.menu_index: //首页
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(indexfm)
                        .hide(happyfm)
                        .hide(bookfm)
                        .hide(userfm)
                        .commit();

                myFragment = "index";

                clearStyle();
                imageView = findViewById(R.id.menu_index_img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.index_current));
                textView = findViewById(R.id.menu_index_text);
                setCurrentStyle(textView);
                break;

            case R.id.menu_happy:  //happy
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(happyfm)
                        .hide(indexfm)
                        .hide(bookfm)
                        .hide(userfm)
                        .commit();

                myFragment = "happy";

                clearStyle();
                imageView = findViewById(R.id.menu_happy_img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.woman_current));
                textView = findViewById(R.id.menu_happy_text);
                setCurrentStyle(textView);
                break;

            case R.id.menu_book: //图书
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(bookfm)
                        .hide(indexfm)
                        .hide(happyfm)
                        .hide(userfm)
                        .commit();

                myFragment = "book";

                clearStyle();
                imageView = findViewById(R.id.menu_book_img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.book_current));
                textView = findViewById(R.id.menu_new_text);
                setCurrentStyle(textView);
                break;

            case R.id.menu_me://我的
                this.getSupportFragmentManager()
                        .beginTransaction()
                        .show(userfm)
                        .hide(indexfm)
                        .hide(happyfm)
                        .hide(bookfm)
                        .commit();

                myFragment = "me";

                clearStyle();
                imageView = findViewById(R.id.menu_me_img);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.me_current));
                textView = findViewById(R.id.menu_me_text);
                setCurrentStyle(textView);
                break;
        }
    }

    //监听按钮返回事件
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean ret = false;
        ret = activityParseOnkey(keyCode,event);
       /* if (!ret) {
            ret = indexfm.onKeyDown(keyCode,event);  //这里的mCurFragment是我们前的Fragment
        }*/
        return ret;
    }

    private boolean activityParseOnkey(int keyCode,KeyEvent event) {
        boolean ret = false;
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                break;
            //回退操作
            case KeyEvent.KEYCODE_BACK:
                ret = indexfm.onKeyDown(keyCode,event);
                break;
        }
        return ret;
    }

    //设置选中按钮current样式
    public void setCurrentStyle(TextView textView)
    {
        textView.setTextColor(getResources().getColor(R.color.base_blue));
    }

    //清除其他按钮样式
    public void clearStyle()
    {
        //重新设置字体颜色
        List<TextView> text_list = new ArrayList<TextView>();
        text_list.add((TextView) findViewById(R.id.menu_me_text));
        text_list.add((TextView) findViewById(R.id.menu_index_text));
        text_list.add((TextView) findViewById(R.id.menu_new_text));
        text_list.add((TextView) findViewById(R.id.menu_happy_text));

        for (TextView item:text_list) {
            item.setTextColor(getResources().getColor(R.color.gray));
        }

        //添加需要修改的图片到数组
        List<BaseImg> img_list = new ArrayList<BaseImg>();
        img_list.add(createBaseImgObj((ImageView) findViewById(R.id.menu_happy_img),getResources().getDrawable(R.drawable.woman)));
        img_list.add(createBaseImgObj((ImageView) findViewById(R.id.menu_index_img),getResources().getDrawable(R.drawable.index)));
        img_list.add(createBaseImgObj((ImageView) findViewById(R.id.menu_book_img),getResources().getDrawable(R.drawable.book)));
        img_list.add(createBaseImgObj((ImageView) findViewById(R.id.menu_me_img),getResources().getDrawable(R.drawable.me)));

        //重新设置图片样式
        for(BaseImg item:img_list) {
            item.getImageView().setImageDrawable(item.getBaseImg());
        }
    }

    //创建baseimg 对象
    public BaseImg createBaseImgObj(ImageView imageView,Drawable drawable)
    {
        BaseImg obj = new BaseImg();
        obj.setImageView(imageView);
        obj.setBaseImg(drawable);
        return  obj;
    }

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "cn.tasays.www.july.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_MESSAGE = "message";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String id = "my_channel_01";
                    String name="我是渠道名字";
                    NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                    Notification notification = null;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                        notificationManager.createNotificationChannel(mChannel);
                        notification = new Notification.Builder(context)
                                .setChannelId(id)
                                .setContentTitle("july")
                                .setContentText(messge)
                                .setSmallIcon(R.mipmap.ic_launcher).build();
                    } else {
                        NotificationCompat.Builder notificationBuilder =  new NotificationCompat.Builder(context, id)
                                .setContentTitle("5 new messages")
                                .setContentText("hahaha")
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setOngoing(true);
                        notification = notificationBuilder.build();
                    }
                    int  notifiId=1;
                    notificationManager.notify(notifiId,notification);
                }
            } catch (Exception e){
            }
        }
    }


}

//默认图片样式
class BaseImg{
    public Drawable getBaseImg() {
        return baseImg;
    }

    public void setBaseImg(Drawable base_img) {
        this.baseImg = base_img;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    private Drawable baseImg;
    private ImageView imageView;
}
