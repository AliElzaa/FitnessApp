/*
 * Copyright 2017 Rozdoum
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.FitAlly.MyFitAllyApp.Utils;


import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;



import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;

import com.FitAlly.MyFitAllyApp.R;

import java.util.Date;
//functions made for image handling

public class ImageUtil {


    public static String generateImageTitle(UploadImagePrefix prefix, String parentId) {
        if (parentId != null) {
            return prefix.toString() + parentId;
        }

        return prefix.toString() + new Date().getTime();
    }

    public static void loadImage(GlideRequests glideRequests, String url, ImageView imageView) {
        loadImage(glideRequests, url, imageView, DiskCacheStrategy.ALL);
    }

    public static void loadImage(GlideRequests glideRequests, String url, ImageView imageView, DiskCacheStrategy diskCacheStrategy) {
        glideRequests.load(url)
                .diskCacheStrategy(diskCacheStrategy)
                .error(R.drawable.ic_stub)
                .into(imageView);
    }

    public static void loadImage(GlideRequests glideRequests, String url, ImageView imageView,
                                 RequestListener<Drawable> listener) {
        glideRequests.load(url)
                .error(R.drawable.ic_stub)
                .listener(listener)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imageView);
    }








    public static void loadLocalImage(GlideRequests glideRequests, Uri uri, ImageView imageView,
                                      RequestListener<Drawable> listener) {
        glideRequests.load(uri)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .listener(listener)
                .into(imageView);
    }
    public static String getURLForResource (int resourceId) {
        return Uri.parse("android.resource://"+R.class.getPackage().getName()+"/" +resourceId).toString();
    }
}
