package com.zxc.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends Activity {

    private ViewGroup anim_mask_layout;// ������
    private ImageView ivFeather;// �����ڽ����Ϸɵ���ë
    private ImageView ivFeatherFrom;
    private ImageView ivFeatherTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivFeatherFrom = (ImageView) findViewById(R.id.iv_feather_from);
        ivFeatherTo = (ImageView) findViewById(R.id.iv_feather_to);
    }

    public void startAnim(View view){
        int[] start_location = new int[2];// һ���������飬�����洢��ť������Ļ��X��Y����
        ivFeatherFrom.getLocationInWindow(start_location);// ���ǻ�ȡ����ť������Ļ��X��Y���꣨��Ҳ�Ƕ�����ʼ�����꣩
        ivFeather = new ImageView(this);
        ivFeather.setImageResource(R.mipmap.live_send_feather);// ����ͼƬ
        setAnim(ivFeather, start_location);// ��ʼִ�ж���
    }

    private void setAnim(final View v, int[] start_location) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);// ����ë��ӵ�������
        final View view = addViewToAnimLayout(anim_mask_layout, v,
                start_location);
        int[] end_location = new int[2];// ���������洢��������λ�õ�X��Y����
        ivFeatherTo.getLocationInWindow(end_location);// ��������ëͼƬ

        // ����λ��
        int endX = end_location[0] - start_location[0];// ����λ�Ƶ�X����
        int endY = end_location[1] - start_location[1];// ����λ�Ƶ�y����
        TranslateAnimation translateAnimationX = new TranslateAnimation(0,
                endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());
        translateAnimationX.setRepeatCount(0);// �����ظ�ִ�еĴ���
        translateAnimationX.setFillAfter(true);

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0,
                0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());
        translateAnimationY.setRepeatCount(0);// �����ظ�ִ�еĴ���
        translateAnimationX.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// ������ִ��ʱ��
        view.startAnimation(set);
        // ���������¼�
        set.setAnimationListener(new Animation.AnimationListener() {
            // �����Ŀ�ʼ
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub
            }

            // �����Ľ���
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
            }
        });

    }

    /**
     * @Description: ����������
     * @param
     * @return void
     * @throws
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    private View addViewToAnimLayout(final ViewGroup vg, final View view,
                                     int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }
}
