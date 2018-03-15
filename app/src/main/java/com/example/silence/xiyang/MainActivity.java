package com.example.silence.xiyang;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.*;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.stephentuso.welcome.WelcomeHelper;

import it.sephiroth.android.library.bottomnavigation.BadgeProvider;
import it.sephiroth.android.library.bottomnavigation.BottomNavigation;


public class MainActivity extends Activity implements  ViewPager.OnPageChangeListener, OnItemClickListener,BottomNavigation.OnMenuItemSelectionListener{
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    WelcomeHelper welcomeScreen;
    private ListView listView;
    private List<HandShow> handShowList = new ArrayList<HandShow>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        welcomeScreen = new WelcomeHelper(this, MyWelcomeActivity.class);
        welcomeScreen.show(savedInstanceState);


        initHandShow();
        initViews();
        init();

    }
    @Override
    public void onMenuItemSelect(final int itemId, final int position, final boolean fromUser) {

        if (fromUser) {
            final FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            MyFragment fragment = (MyFragment) manager.findFragmentById(R.id.fragment_1);

            if (null != fragment) {
                transaction.show(fragment);
            }
        }
    }
    @Override
    public void onMenuItemReselect(final int itemId, final int position, final boolean fromUser) {


        if (fromUser) {
            final FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            MyFragment fragment = (MyFragment) manager.findFragmentById(R.id.fragment_1);

            if (null != fragment) {
                transaction.show(fragment);
            }
        }

    }


    public void initHandShow() {
        HandShow show_0 = new HandShow("手账0号", R.drawable.ic_page_indicator_focused);
        handShowList.add(show_0);
        HandShow show_1 = new HandShow("手账1号", R.drawable.ic_page_indicator_focused);
        handShowList.add(show_1);
        HandShow show_2 = new HandShow("手账2号", R.drawable.ic_page_indicator_focused);
        handShowList.add(show_2);
        HandShow show_3 = new HandShow("手账3号", R.drawable.ic_page_indicator_focused);
        handShowList.add(show_3);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        welcomeScreen.onSaveInstanceState(outState);
    }

    private void initViews() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        HandShowAdapter adapter = new HandShowAdapter(MainActivity.this, R.layout.handshow, handShowList);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(this);

        try {
            Class cls = Class.forName("com.ToxicBakery.viewpager.transforms.RotateDownTransformer");//设置翻页效果
            ABaseTransformer transforemer = (ABaseTransformer) cls.newInstance();
            convenientBanner.getViewPager().setPageTransformer(true, transforemer);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        initImageLoader();
        loadTestDatas();
        //本地图片例子
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)

                .setOnItemClickListener(this);


    }

    //初始化网络图片缓存库
    private void initImageLoader() {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /*
    加入测试Views
    * */
    private void loadTestDatas() {
        //本地图片集合
        for (int position = 0; position < 3; position++)
            localImages.add(getResId("ic_test_" + position, R.drawable.class));
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

    // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this, "监听到翻到第" + position + "了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "点击了第" + position + "个", Toast.LENGTH_SHORT).show();
    }

}
