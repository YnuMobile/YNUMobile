package com.imooc.YnuMobile.RollViewpager;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.imooc.YnuMobile.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;


public class ImageCacheUtil {

	public static void init(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory().threadPoolSize(5)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging() //这儿不是必须的
				.build();
		ImageLoader.getInstance().init(config);
	}

	// options
	public interface OPTIONS {
		public DisplayImageOptions default_options = new DisplayImageOptions.Builder()
				.bitmapConfig(Bitmap.Config.RGB_565)
				.showStubImage(R.drawable.rol_one)
				.showImageForEmptyUri(R.drawable.rol_one)
				.showImageOnFail(R.drawable.rol_one)
				.cacheInMemory()
				.cacheOnDisc()
				.displayer(new BitmapDisplayer() {
					@Override
					public Bitmap display(Bitmap arg0, ImageView arg1) {
						arg1.setImageBitmap(arg0);
						return arg0;
					}
				})
				.build();;

	}

}
