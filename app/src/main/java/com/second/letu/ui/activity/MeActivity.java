package com.second.letu.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.animation.SlideExit.SlideBottomExit;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.widget.NormalDialog;
import com.second.letu.ConstantValue.ConstantValues;
import com.second.letu.R;
import com.second.letu.util.SPUtil;
import com.second.letu.util.SQLiteUtil;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.onekeyshare.OnekeyShare;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 我的界面
 * Created by Jacket_Xu on 2017/3/24.
 */

public class MeActivity extends Activity {
    @BindView(R.id.ibtn_back)
    ImageButton mIbtnBack;
    @BindView(R.id.twowei)
    ImageButton mTwowei;
    @BindView(R.id.profile_image)
    CircleImageView mProfileImage;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.iv_money)
    ImageView mIvMoney;
    @BindView(R.id.rl_money)
    RelativeLayout mRlMoney;
    @BindView(R.id.iv_binding)
    ImageView mIvBinding;
    @BindView(R.id.rl_binding)
    RelativeLayout mRlBinding;
    @BindView(R.id.iv_contribute)
    ImageView mIvContribute;
    @BindView(R.id.rl_contribute)
    RelativeLayout mRlContribute;
    @BindView(R.id.tv_outdata)
    TextView mTvOutdata;
    @BindView(R.id.tv_help)
    TextView mTvHelp;
    @BindView(R.id.tv_share)
    TextView mTvShare;
    @BindView(R.id.tv_author)
    TextView mTvAuthor;
    private ArrayList<HashMap<String, String>> mData;
    private BounceTopEnter mBasIn;
    private SlideBottomExit mBasOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_me);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ibtn_back, R.id.twowei, R.id.profile_image, R.id.tv_name, R.id.rl_money, R.id.rl_binding, R.id.rl_contribute, R.id.tv_outdata, R.id.tv_help, R.id.tv_share, R.id.tv_author})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.twowei:
                break;
            case R.id.profile_image:
                break;
            case R.id.tv_name:
                break;
            case R.id.rl_money:
                break;
            case R.id.rl_binding:
                break;
            case R.id.rl_contribute:
                int score = SPUtil.getInt(ConstantValues.SCORE, 0);
                mBasIn = new BounceTopEnter();
                mBasOut = new SlideBottomExit();
                final NormalDialog dialog = new NormalDialog(MeActivity.this);
                dialog.title("目前的贡献积分")
                      .contentGravity(Gravity.CENTER)
                      .content(score + "")
                      .btnNum(1)
                      .btnText("确定")
                      .showAnim(mBasIn)
                      .dismissAnim(mBasOut)
                      .show();
                dialog.setOnBtnClickL(new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.tv_outdata:
                SQLiteUtil sqLiteUtil = SQLiteUtil.getInstance();
                mData = sqLiteUtil.searchData();
                Intent intent = new Intent(MeActivity.this,OutActivity.class);
                intent.putExtra("outData",mData);
                startActivity(intent);
                break;
            case R.id.tv_help:
                Intent intentHelp = new Intent(MeActivity.this,HelperActivity.class);
                startActivity(intentHelp);
                break;
            case R.id.tv_share:
                showShare();
                break;
            case R.id.tv_author:
                break;
        }
    }
    private void showShare() {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
        oks.setTitle("标题");
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        //oks.setText("我是分享文本");
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        //oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("ShareSDK");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("https://github.com/JacketXuKR");
        // 启动分享GUI
        oks.show(this);
    }
}
