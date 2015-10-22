package com.gitonway.lee.niftynotification.lib;
/*
 * Copyright 2014 gitonway
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gitonway.lee.niftynotification.R;

public class NiftyNotificationView {

    private static final String NULL_PARAMETERS_ARE_NOT_ACCEPTED = "Null parameters are not accepted";

    private Configuration configuration = null;

    private final CharSequence text;

    private final Effects effects;

    private Activity activity;

    private ViewGroup viewGroup;

    private FrameLayout notifyView;

    private Drawable iconDrawable;

    private int  iconRes;

    private boolean isDefault;

    private View.OnClickListener onClickListener;


    private NiftyNotificationView(Activity activity, CharSequence text, Effects effects, ViewGroup viewGroup) {
        if ((activity == null) || (text == null)) {
            throw new IllegalArgumentException(NULL_PARAMETERS_ARE_NOT_ACCEPTED);
        }
        isDefault=true;
        this.activity = activity;
        this.text = text;
        this.effects = effects;
        this.viewGroup = viewGroup;
        this.configuration = new Configuration.Builder().build();
        init(effects);
    }

    private NiftyNotificationView(Activity activity, CharSequence text, Effects effects, ViewGroup viewGroup, Configuration configuration) {
        if ((activity == null) || (text == null) || (configuration == null)) {
            throw new IllegalArgumentException(NULL_PARAMETERS_ARE_NOT_ACCEPTED);
        }
        isDefault=false;
        this.activity = activity;
        this.text = text;
        this.effects = effects;
        this.viewGroup = viewGroup;
        this.configuration = configuration;
        init(effects);
    }

    private void init(Effects effects){
        this.iconDrawable=null;
        this.iconRes=0;
    }

    public static NiftyNotificationView build(Activity activity, CharSequence text, Effects effects, int viewGroupResId) {
        return new NiftyNotificationView(activity, text, effects, (ViewGroup) activity.findViewById(viewGroupResId));
    }

    public static NiftyNotificationView build(Activity activity, CharSequence text, Effects effects, int viewGroupResId, Configuration configuration) {
        return new NiftyNotificationView(activity, text, effects, (ViewGroup) activity.findViewById(viewGroupResId), configuration);
    }

    public long getInDuration() {
        return effects.getAnimator().getDuration();
    }

    public long getOutDuration() {
        return effects.getAnimator().getDuration();
    }

    public long getDispalyDuration() {
        return this.configuration.dispalyDuration;
    }

    public Effects getEffects() {
        return effects;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    Activity getActivity() {
        return activity;
    }

    boolean isShowing() {
        return (null != activity) && isNotifyViewNotNull();
    }

    private boolean isNotifyViewNotNull() {

        return (null != notifyView) && (null != notifyView.getParent());

    }

    void detachActivity() {
        activity = null;
    }

    void detachViewGroup() {
        viewGroup = null;
    }

    ViewGroup getViewGroup() {
        return viewGroup;
    }

    View getView() {

        if (null == this.notifyView) {
            initializeNotifyView();
        }

        return notifyView;
    }

    private void initializeNotifyView() {
        if (this.activity!=null) {

            this.notifyView = initializeCroutonViewGroup();

            //RelativeLayout contentView = initializeContentView();
            //this.notifyView.addView(contentView);

            LayoutInflater layoutInflater = this.activity.getLayoutInflater();
            View contentView = layoutInflater.inflate(R.layout.layout_content, this.notifyView);
            initializeContentView((RelativeLayout) contentView.findViewById(R.id.msg_container));
        }
    }

    private FrameLayout initializeCroutonViewGroup() {

        FrameLayout notifyView = new FrameLayout(this.activity);

        if (null != onClickListener) {
            notifyView.setOnClickListener(onClickListener);
        }

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        notifyView.setLayoutParams(lp);


        return notifyView;
    }

    private void initializeContentView(RelativeLayout contentView) {

        contentView.setBackgroundColor(Color.parseColor(this.configuration.backgroundColor));

        ImageView icon = (ImageView) contentView.findViewById(R.id.msg_icon);
        TextView content = (TextView) contentView.findViewById(R.id.msg_content);

        if ((null != iconDrawable) || (0 != iconRes)) {
            initializeMessageIcon(icon);
        }

        initializeMessageContent(content);
    }

    private void initializeMessageContent(TextView text) {
        text.setText(this.text);
        text.setTextColor(Color.parseColor(this.configuration.textColor));
    }

    private void initializeMessageIcon(ImageView image) {
        //image.setAdjustViewBounds(true);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        if (null != iconDrawable) {
            image.setImageDrawable(iconDrawable);
        }

        if (iconRes!= 0) {
            image.setImageResource(iconRes);
        }
    }

    /*******************Call these methods************************/

    public NiftyNotificationView setIcon(Drawable iconDrawable){
        this.iconDrawable=iconDrawable;
        return this;
    }

    public NiftyNotificationView setIcon(int iconRes){
        this.iconRes=iconRes;
        return this;
    }

    public NiftyNotificationView setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        return this;
    }

    public void show() {

        show(true);
    }

    public void show(boolean repeat) {

        Manager.getInstance().add(this,repeat);
    }

    public void showSticky() {

        Manager.getInstance().addSticky(this);
    }

    public void removeSticky(){
        Manager.getInstance().removeSticky();
    }

    public void hide() {

        Manager.getInstance().removeNotify(this);
    }
}