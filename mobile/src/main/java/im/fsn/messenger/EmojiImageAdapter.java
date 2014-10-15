package im.fsn.messenger;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EmojiImageAdapter extends BaseAdapter {
	private Context mContext;

	public EmojiImageAdapter(Context c) {
		mContext = c;
	}

	public int getCount() {
		return mThumbIds.length;
	}

	public Object getItem(int position) {
		return mValues[position];
	}

	public long getItemId(int position) {
		return mValues[position];
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(mContext);
			int pixelSize = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, 48, this.mContext
							.getResources().getDisplayMetrics());

			imageView.setLayoutParams(new GridView.LayoutParams(pixelSize,
					pixelSize));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			// imageView.setPadding(8, 8, 8, 8);
		} else {
			imageView = (ImageView) convertView;
		}
		int value = (Integer) getItem(position);
		SoftReference<Bitmap> sfb = ImageCache.get(value);
		imageView.setTag(String.valueOf(value));
		Bitmap b = null;
		if (sfb != null)
			b = sfb.get();

		imageView.setId(value);
		imageView.setImageBitmap(b);
		if (b == null) {
			ImageLoader il = new ImageLoader(imageView, position);
			il.execute();
		}
		return imageView;
	}

	private class ImageLoader extends AsyncTask<Void, Void, Bitmap> {

		private final WeakReference<ImageView> mImageView;
		private int mPosition;

		public ImageLoader(ImageView imageView, int position) {

			this.mImageView = new WeakReference<ImageView>(imageView);
			this.mPosition = position;

		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);

			ImageView iv = mImageView.get();
			if (iv == null)
				return;
			if (iv.getId() != mValues[mPosition])
				return;

			ImageCache.put(mPosition, new SoftReference<Bitmap>(result));
			iv.setImageBitmap(result);
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			if (mResources == null)
				mResources = mContext.getResources();
			return Bitmap.createScaledBitmap(BitmapFactory.decodeResource(
					mResources, mThumbIds[mPosition]), 85, 85, false);
		}
	}

	private Resources mResources = null;

	public static HashMap<Integer, SoftReference<Bitmap>> ImageCache = new HashMap<Integer, SoftReference<Bitmap>>();

	private Integer[] mValues = { 0x00a9, 0x00ae, 0x1f004, 0x1f0cf, 0x1f170,
			0x1f171, 0x1f17e, 0x1f17f, 0x1f18e, 0x1f191, 0x1f192, 0x1f193,
			0x1f194, 0x1f195, 0x1f196, 0x1f197, 0x1f198, 0x1f199, 0x1f19a,
			0x1f1e6, 0x1f1e7, 0x1f1e8, 0x1f1e9, 0x1f1ea, 0x1f1eb, 0x1f1ec,
			0x1f1ed, 0x1f1ee, 0x1f1ef, 0x1f1f0, 0x1f1f1, 0x1f1f2, 0x1f1f3,
			0x1f1f4, 0x1f1f5, 0x1f1f6, 0x1f1f7, 0x1f1f8, 0x1f1f9, 0x1f1fa,
			0x1f1fb, 0x1f1fc, 0x1f1fd, 0x1f1fe, 0x1f1ff, 0x1f201, 0x1f202,
			0x1f21a, 0x1f22f, 0x1f232, 0x1f233, 0x1f234, 0x1f235, 0x1f236,
			0x1f237, 0x1f238, 0x1f239, 0x1f23a, 0x1f250, 0x1f251, 0x1f300,
			0x1f301, 0x1f302, 0x1f303, 0x1f304, 0x1f305, 0x1f306, 0x1f307,
			0x1f308, 0x1f309, 0x1f30a, 0x1f30b, 0x1f30c, 0x1f30d, 0x1f30e,
			0x1f30f, 0x1f310, 0x1f311, 0x1f312, 0x1f313, 0x1f314, 0x1f315,
			0x1f316, 0x1f317, 0x1f318, 0x1f319, 0x1f31a, 0x1f31b, 0x1f31c,
			0x1f31d, 0x1f31e, 0x1f31f, 0x1f320, 0x1f330, 0x1f331, 0x1f332,
			0x1f333, 0x1f334, 0x1f335, 0x1f337, 0x1f338, 0x1f339, 0x1f33a,
			0x1f33b, 0x1f33c, 0x1f33d, 0x1f33e, 0x1f33f, 0x1f340, 0x1f341,
			0x1f342, 0x1f343, 0x1f344, 0x1f345, 0x1f346, 0x1f347, 0x1f348,
			0x1f349, 0x1f34a, 0x1f34b, 0x1f34c, 0x1f34d, 0x1f34e, 0x1f34f,
			0x1f350, 0x1f351, 0x1f352, 0x1f353, 0x1f354, 0x1f355, 0x1f356,
			0x1f357, 0x1f358, 0x1f359, 0x1f35a, 0x1f35b, 0x1f35c, 0x1f35d,
			0x1f35e, 0x1f35f, 0x1f360, 0x1f361, 0x1f362, 0x1f363, 0x1f364,
			0x1f365, 0x1f366, 0x1f367, 0x1f368, 0x1f369, 0x1f36a, 0x1f36b,
			0x1f36c, 0x1f36d, 0x1f36e, 0x1f36f, 0x1f370, 0x1f371, 0x1f372,
			0x1f373, 0x1f374, 0x1f375, 0x1f376, 0x1f377, 0x1f378, 0x1f379,
			0x1f37a, 0x1f37b, 0x1f37c, 0x1f380, 0x1f381, 0x1f382, 0x1f383,
			0x1f384, 0x1f385, 0x1f386, 0x1f387, 0x1f388, 0x1f389, 0x1f38a,
			0x1f38b, 0x1f38c, 0x1f38d, 0x1f38e, 0x1f38f, 0x1f390, 0x1f391,
			0x1f392, 0x1f393, 0x1f3a0, 0x1f3a1, 0x1f3a2, 0x1f3a3, 0x1f3a4,
			0x1f3a5, 0x1f3a6, 0x1f3a7, 0x1f3a8, 0x1f3a9, 0x1f3aa, 0x1f3ab,
			0x1f3ac, 0x1f3ad, 0x1f3ae, 0x1f3af, 0x1f3b0, 0x1f3b1, 0x1f3b2,
			0x1f3b3, 0x1f3b4, 0x1f3b5, 0x1f3b6, 0x1f3b7, 0x1f3b8, 0x1f3b9,
			0x1f3ba, 0x1f3bb, 0x1f3bc, 0x1f3bd, 0x1f3be, 0x1f3bf, 0x1f3c0,
			0x1f3c1, 0x1f3c2, 0x1f3c3, 0x1f3c4, 0x1f3c6, 0x1f3c7, 0x1f3c8,
			0x1f3c9, 0x1f3ca, 0x1f3e0, 0x1f3e1, 0x1f3e2, 0x1f3e3, 0x1f3e4,
			0x1f3e5, 0x1f3e6, 0x1f3e7, 0x1f3e8, 0x1f3e9, 0x1f3ea, 0x1f3eb,
			0x1f3ec, 0x1f3ed, 0x1f3ee, 0x1f3ef, 0x1f3f0, 0x1f400, 0x1f401,
			0x1f402, 0x1f403, 0x1f404, 0x1f405, 0x1f406, 0x1f407, 0x1f408,
			0x1f409, 0x1f40a, 0x1f40b, 0x1f40c, 0x1f40d, 0x1f40e, 0x1f40f,
			0x1f410, 0x1f411, 0x1f412, 0x1f413, 0x1f414, 0x1f415, 0x1f416,
			0x1f417, 0x1f418, 0x1f419, 0x1f41a, 0x1f41b, 0x1f41c, 0x1f41d,
			0x1f41e, 0x1f41f, 0x1f420, 0x1f421, 0x1f422, 0x1f423, 0x1f424,
			0x1f425, 0x1f426, 0x1f427, 0x1f428, 0x1f429, 0x1f42a, 0x1f42b,
			0x1f42c, 0x1f42d, 0x1f42e, 0x1f42f, 0x1f430, 0x1f431, 0x1f432,
			0x1f433, 0x1f434, 0x1f435, 0x1f436, 0x1f437, 0x1f438, 0x1f439,
			0x1f43a, 0x1f43b, 0x1f43c, 0x1f43d, 0x1f43e, 0x1f440, 0x1f442,
			0x1f443, 0x1f444, 0x1f445, 0x1f446, 0x1f447, 0x1f448, 0x1f449,
			0x1f44a, 0x1f44b, 0x1f44c, 0x1f44d, 0x1f44e, 0x1f44f, 0x1f450,
			0x1f451, 0x1f452, 0x1f453, 0x1f454, 0x1f455, 0x1f456, 0x1f457,
			0x1f458, 0x1f459, 0x1f45a, 0x1f45b, 0x1f45c, 0x1f45d, 0x1f45e,
			0x1f45f, 0x1f460, 0x1f461, 0x1f462, 0x1f463, 0x1f464, 0x1f465,
			0x1f466, 0x1f467, 0x1f468, 0x1f469, 0x1f46a, 0x1f46b, 0x1f46c,
			0x1f46d, 0x1f46e, 0x1f46f, 0x1f470, 0x1f471, 0x1f472, 0x1f473,
			0x1f474, 0x1f475, 0x1f476, 0x1f477, 0x1f478, 0x1f479, 0x1f47a,
			0x1f47b, 0x1f47c, 0x1f47d, 0x1f47e, 0x1f47f, 0x1f480, 0x1f481,
			0x1f482, 0x1f483, 0x1f484, 0x1f485, 0x1f486, 0x1f487, 0x1f488,
			0x1f489, 0x1f48a, 0x1f48b, 0x1f48c, 0x1f48d, 0x1f48e, 0x1f48f,
			0x1f490, 0x1f491, 0x1f492, 0x1f493, 0x1f494, 0x1f495, 0x1f496,
			0x1f497, 0x1f498, 0x1f499, 0x1f49a, 0x1f49b, 0x1f49c, 0x1f49d,
			0x1f49e, 0x1f49f, 0x1f4a0, 0x1f4a1, 0x1f4a2, 0x1f4a3, 0x1f4a4,
			0x1f4a5, 0x1f4a6, 0x1f4a7, 0x1f4a8, 0x1f4a9, 0x1f4aa, 0x1f4ab,
			0x1f4ac, 0x1f4ad, 0x1f4ae, 0x1f4af, 0x1f4b0, 0x1f4b1, 0x1f4b2,
			0x1f4b3, 0x1f4b4, 0x1f4b5, 0x1f4b6, 0x1f4b7, 0x1f4b8, 0x1f4b9,
			0x1f4ba, 0x1f4bb, 0x1f4bc, 0x1f4bd, 0x1f4be, 0x1f4bf, 0x1f4c0,
			0x1f4c1, 0x1f4c2, 0x1f4c3, 0x1f4c4, 0x1f4c5, 0x1f4c6, 0x1f4c7,
			0x1f4c8, 0x1f4c9, 0x1f4ca, 0x1f4cb, 0x1f4cc, 0x1f4cd, 0x1f4ce,
			0x1f4cf, 0x1f4d0, 0x1f4d1, 0x1f4d2, 0x1f4d3, 0x1f4d4, 0x1f4d5,
			0x1f4d6, 0x1f4d7, 0x1f4d8, 0x1f4d9, 0x1f4da, 0x1f4db, 0x1f4dc,
			0x1f4dd, 0x1f4de, 0x1f4df, 0x1f4e0, 0x1f4e1, 0x1f4e2, 0x1f4e3,
			0x1f4e4, 0x1f4e5, 0x1f4e6, 0x1f4e7, 0x1f4e8, 0x1f4e9, 0x1f4ea,
			0x1f4eb, 0x1f4ec, 0x1f4ed, 0x1f4ee, 0x1f4ef, 0x1f4f0, 0x1f4f1,
			0x1f4f2, 0x1f4f3, 0x1f4f4, 0x1f4f5, 0x1f4f6, 0x1f4f7, 0x1f4f9,
			0x1f4fa, 0x1f4fb, 0x1f4fc, 0x1f500, 0x1f501, 0x1f502, 0x1f503,
			0x1f504, 0x1f505, 0x1f506, 0x1f507, 0x1f508, 0x1f509, 0x1f50a,
			0x1f50b, 0x1f50c, 0x1f50d, 0x1f50e, 0x1f50f, 0x1f510, 0x1f511,
			0x1f512, 0x1f513, 0x1f514, 0x1f515, 0x1f516, 0x1f517, 0x1f518,
			0x1f519, 0x1f51a, 0x1f51b, 0x1f51c, 0x1f51d, 0x1f51e, 0x1f51f,
			0x1f520, 0x1f521, 0x1f522, 0x1f523, 0x1f524, 0x1f525, 0x1f526,
			0x1f527, 0x1f528, 0x1f529, 0x1f52a, 0x1f52b, 0x1f52c, 0x1f52d,
			0x1f52e, 0x1f52f, 0x1f530, 0x1f531, 0x1f532, 0x1f533, 0x1f534,
			0x1f535, 0x1f536, 0x1f537, 0x1f538, 0x1f539, 0x1f53a, 0x1f53b,
			0x1f53c, 0x1f53d, 0x1f550, 0x1f551, 0x1f552, 0x1f553, 0x1f554,
			0x1f555, 0x1f556, 0x1f557, 0x1f558, 0x1f559, 0x1f55a, 0x1f55b,
			0x1f55c, 0x1f55d, 0x1f55e, 0x1f55f, 0x1f560, 0x1f561, 0x1f562,
			0x1f563, 0x1f564, 0x1f565, 0x1f566, 0x1f567, 0x1f5fb, 0x1f5fc,
			0x1f5fd, 0x1f5fe, 0x1f5ff, 0x1f600, 0x1f601, 0x1f602, 0x1f603,
			0x1f604, 0x1f605, 0x1f606, 0x1f607, 0x1f608, 0x1f609, 0x1f60a,
			0x1f60b, 0x1f60c, 0x1f60d, 0x1f60e, 0x1f60f, 0x1f610, 0x1f611,
			0x1f612, 0x1f613, 0x1f614, 0x1f615, 0x1f616, 0x1f617, 0x1f618,
			0x1f619, 0x1f61a, 0x1f61b, 0x1f61c, 0x1f61d, 0x1f61e, 0x1f61f,
			0x1f620, 0x1f621, 0x1f622, 0x1f623, 0x1f624, 0x1f625, 0x1f626,
			0x1f627, 0x1f628, 0x1f629, 0x1f62a, 0x1f62b, 0x1f62c, 0x1f62d,
			0x1f62e, 0x1f62f, 0x1f630, 0x1f631, 0x1f632, 0x1f633, 0x1f634,
			0x1f635, 0x1f636, 0x1f637, 0x1f638, 0x1f639, 0x1f63a, 0x1f63b,
			0x1f63c, 0x1f63d, 0x1f63e, 0x1f63f, 0x1f640, 0x1f645, 0x1f646,
			0x1f647, 0x1f648, 0x1f649, 0x1f64a, 0x1f64b, 0x1f64c, 0x1f64d,
			0x1f64e, 0x1f64f, 0x1f680, 0x1f681, 0x1f682, 0x1f683, 0x1f684,
			0x1f685, 0x1f686, 0x1f687, 0x1f688, 0x1f689, 0x1f68a, 0x1f68b,
			0x1f68c, 0x1f68d, 0x1f68e, 0x1f68f, 0x1f690, 0x1f691, 0x1f692,
			0x1f693, 0x1f694, 0x1f695, 0x1f696, 0x1f697, 0x1f698, 0x1f699,
			0x1f69a, 0x1f69b, 0x1f69c, 0x1f69d, 0x1f69e, 0x1f69f, 0x1f6a0,
			0x1f6a1, 0x1f6a2, 0x1f6a3, 0x1f6a4, 0x1f6a5, 0x1f6a6, 0x1f6a7,
			0x1f6a8, 0x1f6a9, 0x1f6aa, 0x1f6ab, 0x1f6ac, 0x1f6ad, 0x1f6ae,
			0x1f6af, 0x1f6b0, 0x1f6b1, 0x1f6b2, 0x1f6b3, 0x1f6b4, 0x1f6b5,
			0x1f6b6, 0x1f6b7, 0x1f6b8, 0x1f6b9, 0x1f6ba, 0x1f6bb, 0x1f6bc,
			0x1f6bd, 0x1f6be, 0x1f6bf, 0x1f6c0, 0x1f6c1, 0x1f6c2, 0x1f6c3,
			0x1f6c4, 0x1f6c5, 0x2002, 0x2003, 0x2005, 0x203c, 0x2049, 0x20e3,
			0x2122, 0x2139, 0x2194, 0x2195, 0x2196, 0x2197, 0x2198, 0x2199,
			0x21a9, 0x21aa, 0x231a, 0x231b, 0x23e9, 0x23ea, 0x23eb, 0x23ec,
			0x23f0, 0x23f3, 0x24c2, 0x25aa, 0x25ab, 0x25b6, 0x25c0, 0x25fb,
			0x25fc, 0x25fd, 0x25fe, 0x2600, 0x2601, 0x260e, 0x2611, 0x2614,
			0x2615, 0x261d, 0x263a, 0x2648, 0x2649, 0x264a, 0x264b, 0x264c,
			0x264d, 0x264e, 0x264f, 0x2650, 0x2651, 0x2652, 0x2653, 0x2660,
			0x2663, 0x2665, 0x2666, 0x2668, 0x267b, 0x267f, 0x2693, 0x26a0,
			0x26a1, 0x26aa, 0x26ab, 0x26bd, 0x26be, 0x26c4, 0x26c5, 0x26ce,
			0x26d4, 0x26ea, 0x26f2, 0x26f3, 0x26f5, 0x26fa, 0x26fd, 0x2702,
			0x2705, 0x2708, 0x2709, 0x270a, 0x270b, 0x270c, 0x270f, 0x2712,
			0x2714, 0x2716, 0x2728, 0x2733, 0x2734, 0x2744, 0x2747, 0x274c,
			0x274e, 0x2753, 0x2754, 0x2755, 0x2757, 0x2762, 0x2764, 0x2795,
			0x2796, 0x2797, 0x27a1, 0x27b0, 0x27bf, 0x2934, 0x2935, 0x2b05,
			0x2b06, 0x2b07, 0x2b1b, 0x2b1c, 0x2b50, 0x2b55, 0x3030, 0x303d,
			0x3297, 0x3299, 0xfe4e5, 0xfe4e6, 0xfe4e7, 0xfe4e8, 0xfe4e9,
			0xfe4ea, 0xfe4eb, 0xfe4ec, 0xfe4ed, 0xfe4ee, 0xfe82c, 0xfe82e,
			0xfe82f, 0xfe830, 0xfe831, 0xfe832, 0xfe833, 0xfe834, 0xfe835,
			0xfe836, 0xfe837 };
	// references to our images
	private Integer[] mThumbIds = { R.drawable.emoji_u00a9,
			R.drawable.emoji_u00ae, R.drawable.emoji_u1f004,
			R.drawable.emoji_u1f0cf, R.drawable.emoji_u1f170,
			R.drawable.emoji_u1f171, R.drawable.emoji_u1f17e,
			R.drawable.emoji_u1f17f, R.drawable.emoji_u1f18e,
			R.drawable.emoji_u1f191, R.drawable.emoji_u1f192,
			R.drawable.emoji_u1f193, R.drawable.emoji_u1f194,
			R.drawable.emoji_u1f195, R.drawable.emoji_u1f196,
			R.drawable.emoji_u1f197, R.drawable.emoji_u1f198,
			R.drawable.emoji_u1f199, R.drawable.emoji_u1f19a,
			R.drawable.emoji_u1f1e6, R.drawable.emoji_u1f1e7,
			R.drawable.emoji_u1f1e8, R.drawable.emoji_u1f1e9,
			R.drawable.emoji_u1f1ea, R.drawable.emoji_u1f1eb,
			R.drawable.emoji_u1f1ec, R.drawable.emoji_u1f1ed,
			R.drawable.emoji_u1f1ee, R.drawable.emoji_u1f1ef,
			R.drawable.emoji_u1f1f0, R.drawable.emoji_u1f1f1,
			R.drawable.emoji_u1f1f2, R.drawable.emoji_u1f1f3,
			R.drawable.emoji_u1f1f4, R.drawable.emoji_u1f1f5,
			R.drawable.emoji_u1f1f6, R.drawable.emoji_u1f1f7,
			R.drawable.emoji_u1f1f8, R.drawable.emoji_u1f1f9,
			R.drawable.emoji_u1f1fa, R.drawable.emoji_u1f1fb,
			R.drawable.emoji_u1f1fc, R.drawable.emoji_u1f1fd,
			R.drawable.emoji_u1f1fe, R.drawable.emoji_u1f1ff,
			R.drawable.emoji_u1f201, R.drawable.emoji_u1f202,
			R.drawable.emoji_u1f21a, R.drawable.emoji_u1f22f,
			R.drawable.emoji_u1f232, R.drawable.emoji_u1f233,
			R.drawable.emoji_u1f234, R.drawable.emoji_u1f235,
			R.drawable.emoji_u1f236, R.drawable.emoji_u1f237,
			R.drawable.emoji_u1f238, R.drawable.emoji_u1f239,
			R.drawable.emoji_u1f23a, R.drawable.emoji_u1f250,
			R.drawable.emoji_u1f251, R.drawable.emoji_u1f300,
			R.drawable.emoji_u1f301, R.drawable.emoji_u1f302,
			R.drawable.emoji_u1f303, R.drawable.emoji_u1f304,
			R.drawable.emoji_u1f305, R.drawable.emoji_u1f306,
			R.drawable.emoji_u1f307, R.drawable.emoji_u1f308,
			R.drawable.emoji_u1f309, R.drawable.emoji_u1f30a,
			R.drawable.emoji_u1f30b, R.drawable.emoji_u1f30c,
			R.drawable.emoji_u1f30d, R.drawable.emoji_u1f30e,
			R.drawable.emoji_u1f30f, R.drawable.emoji_u1f310,
			R.drawable.emoji_u1f311, R.drawable.emoji_u1f312,
			R.drawable.emoji_u1f313, R.drawable.emoji_u1f314,
			R.drawable.emoji_u1f315, R.drawable.emoji_u1f316,
			R.drawable.emoji_u1f317, R.drawable.emoji_u1f318,
			R.drawable.emoji_u1f319, R.drawable.emoji_u1f31a,
			R.drawable.emoji_u1f31b, R.drawable.emoji_u1f31c,
			R.drawable.emoji_u1f31d, R.drawable.emoji_u1f31e,
			R.drawable.emoji_u1f31f, R.drawable.emoji_u1f320,
			R.drawable.emoji_u1f330, R.drawable.emoji_u1f331,
			R.drawable.emoji_u1f332, R.drawable.emoji_u1f333,
			R.drawable.emoji_u1f334, R.drawable.emoji_u1f335,
			R.drawable.emoji_u1f337, R.drawable.emoji_u1f338,
			R.drawable.emoji_u1f339, R.drawable.emoji_u1f33a,
			R.drawable.emoji_u1f33b, R.drawable.emoji_u1f33c,
			R.drawable.emoji_u1f33d, R.drawable.emoji_u1f33e,
			R.drawable.emoji_u1f33f, R.drawable.emoji_u1f340,
			R.drawable.emoji_u1f341, R.drawable.emoji_u1f342,
			R.drawable.emoji_u1f343, R.drawable.emoji_u1f344,
			R.drawable.emoji_u1f345, R.drawable.emoji_u1f346,
			R.drawable.emoji_u1f347, R.drawable.emoji_u1f348,
			R.drawable.emoji_u1f349, R.drawable.emoji_u1f34a,
			R.drawable.emoji_u1f34b, R.drawable.emoji_u1f34c,
			R.drawable.emoji_u1f34d, R.drawable.emoji_u1f34e,
			R.drawable.emoji_u1f34f, R.drawable.emoji_u1f350,
			R.drawable.emoji_u1f351, R.drawable.emoji_u1f352,
			R.drawable.emoji_u1f353, R.drawable.emoji_u1f354,
			R.drawable.emoji_u1f355, R.drawable.emoji_u1f356,
			R.drawable.emoji_u1f357, R.drawable.emoji_u1f358,
			R.drawable.emoji_u1f359, R.drawable.emoji_u1f35a,
			R.drawable.emoji_u1f35b, R.drawable.emoji_u1f35c,
			R.drawable.emoji_u1f35d, R.drawable.emoji_u1f35e,
			R.drawable.emoji_u1f35f, R.drawable.emoji_u1f360,
			R.drawable.emoji_u1f361, R.drawable.emoji_u1f362,
			R.drawable.emoji_u1f363, R.drawable.emoji_u1f364,
			R.drawable.emoji_u1f365, R.drawable.emoji_u1f366,
			R.drawable.emoji_u1f367, R.drawable.emoji_u1f368,
			R.drawable.emoji_u1f369, R.drawable.emoji_u1f36a,
			R.drawable.emoji_u1f36b, R.drawable.emoji_u1f36c,
			R.drawable.emoji_u1f36d, R.drawable.emoji_u1f36e,
			R.drawable.emoji_u1f36f, R.drawable.emoji_u1f370,
			R.drawable.emoji_u1f371, R.drawable.emoji_u1f372,
			R.drawable.emoji_u1f373, R.drawable.emoji_u1f374,
			R.drawable.emoji_u1f375, R.drawable.emoji_u1f376,
			R.drawable.emoji_u1f377, R.drawable.emoji_u1f378,
			R.drawable.emoji_u1f379, R.drawable.emoji_u1f37a,
			R.drawable.emoji_u1f37b, R.drawable.emoji_u1f37c,
			R.drawable.emoji_u1f380, R.drawable.emoji_u1f381,
			R.drawable.emoji_u1f382, R.drawable.emoji_u1f383,
			R.drawable.emoji_u1f384, R.drawable.emoji_u1f385,
			R.drawable.emoji_u1f386, R.drawable.emoji_u1f387,
			R.drawable.emoji_u1f388, R.drawable.emoji_u1f389,
			R.drawable.emoji_u1f38a, R.drawable.emoji_u1f38b,
			R.drawable.emoji_u1f38c, R.drawable.emoji_u1f38d,
			R.drawable.emoji_u1f38e, R.drawable.emoji_u1f38f,
			R.drawable.emoji_u1f390, R.drawable.emoji_u1f391,
			R.drawable.emoji_u1f392, R.drawable.emoji_u1f393,
			R.drawable.emoji_u1f3a0, R.drawable.emoji_u1f3a1,
			R.drawable.emoji_u1f3a2, R.drawable.emoji_u1f3a3,
			R.drawable.emoji_u1f3a4, R.drawable.emoji_u1f3a5,
			R.drawable.emoji_u1f3a6, R.drawable.emoji_u1f3a7,
			R.drawable.emoji_u1f3a8, R.drawable.emoji_u1f3a9,
			R.drawable.emoji_u1f3aa, R.drawable.emoji_u1f3ab,
			R.drawable.emoji_u1f3ac, R.drawable.emoji_u1f3ad,
			R.drawable.emoji_u1f3ae, R.drawable.emoji_u1f3af,
			R.drawable.emoji_u1f3b0, R.drawable.emoji_u1f3b1,
			R.drawable.emoji_u1f3b2, R.drawable.emoji_u1f3b3,
			R.drawable.emoji_u1f3b4, R.drawable.emoji_u1f3b5,
			R.drawable.emoji_u1f3b6, R.drawable.emoji_u1f3b7,
			R.drawable.emoji_u1f3b8, R.drawable.emoji_u1f3b9,
			R.drawable.emoji_u1f3ba, R.drawable.emoji_u1f3bb,
			R.drawable.emoji_u1f3bc, R.drawable.emoji_u1f3bd,
			R.drawable.emoji_u1f3be, R.drawable.emoji_u1f3bf,
			R.drawable.emoji_u1f3c0, R.drawable.emoji_u1f3c1,
			R.drawable.emoji_u1f3c2, R.drawable.emoji_u1f3c3,
			R.drawable.emoji_u1f3c4, R.drawable.emoji_u1f3c6,
			R.drawable.emoji_u1f3c7, R.drawable.emoji_u1f3c8,
			R.drawable.emoji_u1f3c9, R.drawable.emoji_u1f3ca,
			R.drawable.emoji_u1f3e0, R.drawable.emoji_u1f3e1,
			R.drawable.emoji_u1f3e2, R.drawable.emoji_u1f3e3,
			R.drawable.emoji_u1f3e4, R.drawable.emoji_u1f3e5,
			R.drawable.emoji_u1f3e6, R.drawable.emoji_u1f3e7,
			R.drawable.emoji_u1f3e8, R.drawable.emoji_u1f3e9,
			R.drawable.emoji_u1f3ea, R.drawable.emoji_u1f3eb,
			R.drawable.emoji_u1f3ec, R.drawable.emoji_u1f3ed,
			R.drawable.emoji_u1f3ee, R.drawable.emoji_u1f3ef,
			R.drawable.emoji_u1f3f0, R.drawable.emoji_u1f400,
			R.drawable.emoji_u1f401, R.drawable.emoji_u1f402,
			R.drawable.emoji_u1f403, R.drawable.emoji_u1f404,
			R.drawable.emoji_u1f405, R.drawable.emoji_u1f406,
			R.drawable.emoji_u1f407, R.drawable.emoji_u1f408,
			R.drawable.emoji_u1f409, R.drawable.emoji_u1f40a,
			R.drawable.emoji_u1f40b, R.drawable.emoji_u1f40c,
			R.drawable.emoji_u1f40d, R.drawable.emoji_u1f40e,
			R.drawable.emoji_u1f40f, R.drawable.emoji_u1f410,
			R.drawable.emoji_u1f411, R.drawable.emoji_u1f412,
			R.drawable.emoji_u1f413, R.drawable.emoji_u1f414,
			R.drawable.emoji_u1f415, R.drawable.emoji_u1f416,
			R.drawable.emoji_u1f417, R.drawable.emoji_u1f418,
			R.drawable.emoji_u1f419, R.drawable.emoji_u1f41a,
			R.drawable.emoji_u1f41b, R.drawable.emoji_u1f41c,
			R.drawable.emoji_u1f41d, R.drawable.emoji_u1f41e,
			R.drawable.emoji_u1f41f, R.drawable.emoji_u1f420,
			R.drawable.emoji_u1f421, R.drawable.emoji_u1f422,
			R.drawable.emoji_u1f423, R.drawable.emoji_u1f424,
			R.drawable.emoji_u1f425, R.drawable.emoji_u1f426,
			R.drawable.emoji_u1f427, R.drawable.emoji_u1f428,
			R.drawable.emoji_u1f429, R.drawable.emoji_u1f42a,
			R.drawable.emoji_u1f42b, R.drawable.emoji_u1f42c,
			R.drawable.emoji_u1f42d, R.drawable.emoji_u1f42e,
			R.drawable.emoji_u1f42f, R.drawable.emoji_u1f430,
			R.drawable.emoji_u1f431, R.drawable.emoji_u1f432,
			R.drawable.emoji_u1f433, R.drawable.emoji_u1f434,
			R.drawable.emoji_u1f435, R.drawable.emoji_u1f436,
			R.drawable.emoji_u1f437, R.drawable.emoji_u1f438,
			R.drawable.emoji_u1f439, R.drawable.emoji_u1f43a,
			R.drawable.emoji_u1f43b, R.drawable.emoji_u1f43c,
			R.drawable.emoji_u1f43d, R.drawable.emoji_u1f43e,
			R.drawable.emoji_u1f440, R.drawable.emoji_u1f442,
			R.drawable.emoji_u1f443, R.drawable.emoji_u1f444,
			R.drawable.emoji_u1f445, R.drawable.emoji_u1f446,
			R.drawable.emoji_u1f447, R.drawable.emoji_u1f448,
			R.drawable.emoji_u1f449, R.drawable.emoji_u1f44a,
			R.drawable.emoji_u1f44b, R.drawable.emoji_u1f44c,
			R.drawable.emoji_u1f44d, R.drawable.emoji_u1f44e,
			R.drawable.emoji_u1f44f, R.drawable.emoji_u1f450,
			R.drawable.emoji_u1f451, R.drawable.emoji_u1f452,
			R.drawable.emoji_u1f453, R.drawable.emoji_u1f454,
			R.drawable.emoji_u1f455, R.drawable.emoji_u1f456,
			R.drawable.emoji_u1f457, R.drawable.emoji_u1f458,
			R.drawable.emoji_u1f459, R.drawable.emoji_u1f45a,
			R.drawable.emoji_u1f45b, R.drawable.emoji_u1f45c,
			R.drawable.emoji_u1f45d, R.drawable.emoji_u1f45e,
			R.drawable.emoji_u1f45f, R.drawable.emoji_u1f460,
			R.drawable.emoji_u1f461, R.drawable.emoji_u1f462,
			R.drawable.emoji_u1f463, R.drawable.emoji_u1f464,
			R.drawable.emoji_u1f465, R.drawable.emoji_u1f466,
			R.drawable.emoji_u1f467, R.drawable.emoji_u1f468,
			R.drawable.emoji_u1f469, R.drawable.emoji_u1f46a,
			R.drawable.emoji_u1f46b, R.drawable.emoji_u1f46c,
			R.drawable.emoji_u1f46d, R.drawable.emoji_u1f46e,
			R.drawable.emoji_u1f46f, R.drawable.emoji_u1f470,
			R.drawable.emoji_u1f471, R.drawable.emoji_u1f472,
			R.drawable.emoji_u1f473, R.drawable.emoji_u1f474,
			R.drawable.emoji_u1f475, R.drawable.emoji_u1f476,
			R.drawable.emoji_u1f477, R.drawable.emoji_u1f478,
			R.drawable.emoji_u1f479, R.drawable.emoji_u1f47a,
			R.drawable.emoji_u1f47b, R.drawable.emoji_u1f47c,
			R.drawable.emoji_u1f47d, R.drawable.emoji_u1f47e,
			R.drawable.emoji_u1f47f, R.drawable.emoji_u1f480,
			R.drawable.emoji_u1f481, R.drawable.emoji_u1f482,
			R.drawable.emoji_u1f483, R.drawable.emoji_u1f484,
			R.drawable.emoji_u1f485, R.drawable.emoji_u1f486,
			R.drawable.emoji_u1f487, R.drawable.emoji_u1f488,
			R.drawable.emoji_u1f489, R.drawable.emoji_u1f48a,
			R.drawable.emoji_u1f48b, R.drawable.emoji_u1f48c,
			R.drawable.emoji_u1f48d, R.drawable.emoji_u1f48e,
			R.drawable.emoji_u1f48f, R.drawable.emoji_u1f490,
			R.drawable.emoji_u1f491, R.drawable.emoji_u1f492,
			R.drawable.emoji_u1f493, R.drawable.emoji_u1f494,
			R.drawable.emoji_u1f495, R.drawable.emoji_u1f496,
			R.drawable.emoji_u1f497, R.drawable.emoji_u1f498,
			R.drawable.emoji_u1f499, R.drawable.emoji_u1f49a,
			R.drawable.emoji_u1f49b, R.drawable.emoji_u1f49c,
			R.drawable.emoji_u1f49d, R.drawable.emoji_u1f49e,
			R.drawable.emoji_u1f49f, R.drawable.emoji_u1f4a0,
			R.drawable.emoji_u1f4a1, R.drawable.emoji_u1f4a2,
			R.drawable.emoji_u1f4a3, R.drawable.emoji_u1f4a4,
			R.drawable.emoji_u1f4a5, R.drawable.emoji_u1f4a6,
			R.drawable.emoji_u1f4a7, R.drawable.emoji_u1f4a8,
			R.drawable.emoji_u1f4a9, R.drawable.emoji_u1f4aa,
			R.drawable.emoji_u1f4ab, R.drawable.emoji_u1f4ac,
			R.drawable.emoji_u1f4ad, R.drawable.emoji_u1f4ae,
			R.drawable.emoji_u1f4af, R.drawable.emoji_u1f4b0,
			R.drawable.emoji_u1f4b1, R.drawable.emoji_u1f4b2,
			R.drawable.emoji_u1f4b3, R.drawable.emoji_u1f4b4,
			R.drawable.emoji_u1f4b5, R.drawable.emoji_u1f4b6,
			R.drawable.emoji_u1f4b7, R.drawable.emoji_u1f4b8,
			R.drawable.emoji_u1f4b9, R.drawable.emoji_u1f4ba,
			R.drawable.emoji_u1f4bb, R.drawable.emoji_u1f4bc,
			R.drawable.emoji_u1f4bd, R.drawable.emoji_u1f4be,
			R.drawable.emoji_u1f4bf, R.drawable.emoji_u1f4c0,
			R.drawable.emoji_u1f4c1, R.drawable.emoji_u1f4c2,
			R.drawable.emoji_u1f4c3, R.drawable.emoji_u1f4c4,
			R.drawable.emoji_u1f4c5, R.drawable.emoji_u1f4c6,
			R.drawable.emoji_u1f4c7, R.drawable.emoji_u1f4c8,
			R.drawable.emoji_u1f4c9, R.drawable.emoji_u1f4ca,
			R.drawable.emoji_u1f4cb, R.drawable.emoji_u1f4cc,
			R.drawable.emoji_u1f4cd, R.drawable.emoji_u1f4ce,
			R.drawable.emoji_u1f4cf, R.drawable.emoji_u1f4d0,
			R.drawable.emoji_u1f4d1, R.drawable.emoji_u1f4d2,
			R.drawable.emoji_u1f4d3, R.drawable.emoji_u1f4d4,
			R.drawable.emoji_u1f4d5, R.drawable.emoji_u1f4d6,
			R.drawable.emoji_u1f4d7, R.drawable.emoji_u1f4d8,
			R.drawable.emoji_u1f4d9, R.drawable.emoji_u1f4da,
			R.drawable.emoji_u1f4db, R.drawable.emoji_u1f4dc,
			R.drawable.emoji_u1f4dd, R.drawable.emoji_u1f4de,
			R.drawable.emoji_u1f4df, R.drawable.emoji_u1f4e0,
			R.drawable.emoji_u1f4e1, R.drawable.emoji_u1f4e2,
			R.drawable.emoji_u1f4e3, R.drawable.emoji_u1f4e4,
			R.drawable.emoji_u1f4e5, R.drawable.emoji_u1f4e6,
			R.drawable.emoji_u1f4e7, R.drawable.emoji_u1f4e8,
			R.drawable.emoji_u1f4e9, R.drawable.emoji_u1f4ea,
			R.drawable.emoji_u1f4eb, R.drawable.emoji_u1f4ec,
			R.drawable.emoji_u1f4ed, R.drawable.emoji_u1f4ee,
			R.drawable.emoji_u1f4ef, R.drawable.emoji_u1f4f0,
			R.drawable.emoji_u1f4f1, R.drawable.emoji_u1f4f2,
			R.drawable.emoji_u1f4f3, R.drawable.emoji_u1f4f4,
			R.drawable.emoji_u1f4f5, R.drawable.emoji_u1f4f6,
			R.drawable.emoji_u1f4f7, R.drawable.emoji_u1f4f9,
			R.drawable.emoji_u1f4fa, R.drawable.emoji_u1f4fb,
			R.drawable.emoji_u1f4fc, R.drawable.emoji_u1f500,
			R.drawable.emoji_u1f501, R.drawable.emoji_u1f502,
			R.drawable.emoji_u1f503, R.drawable.emoji_u1f504,
			R.drawable.emoji_u1f505, R.drawable.emoji_u1f506,
			R.drawable.emoji_u1f507, R.drawable.emoji_u1f508,
			R.drawable.emoji_u1f509, R.drawable.emoji_u1f50a,
			R.drawable.emoji_u1f50b, R.drawable.emoji_u1f50c,
			R.drawable.emoji_u1f50d, R.drawable.emoji_u1f50e,
			R.drawable.emoji_u1f50f, R.drawable.emoji_u1f510,
			R.drawable.emoji_u1f511, R.drawable.emoji_u1f512,
			R.drawable.emoji_u1f513, R.drawable.emoji_u1f514,
			R.drawable.emoji_u1f515, R.drawable.emoji_u1f516,
			R.drawable.emoji_u1f517, R.drawable.emoji_u1f518,
			R.drawable.emoji_u1f519, R.drawable.emoji_u1f51a,
			R.drawable.emoji_u1f51b, R.drawable.emoji_u1f51c,
			R.drawable.emoji_u1f51d, R.drawable.emoji_u1f51e,
			R.drawable.emoji_u1f51f, R.drawable.emoji_u1f520,
			R.drawable.emoji_u1f521, R.drawable.emoji_u1f522,
			R.drawable.emoji_u1f523, R.drawable.emoji_u1f524,
			R.drawable.emoji_u1f525, R.drawable.emoji_u1f526,
			R.drawable.emoji_u1f527, R.drawable.emoji_u1f528,
			R.drawable.emoji_u1f529, R.drawable.emoji_u1f52a,
			R.drawable.emoji_u1f52b, R.drawable.emoji_u1f52c,
			R.drawable.emoji_u1f52d, R.drawable.emoji_u1f52e,
			R.drawable.emoji_u1f52f, R.drawable.emoji_u1f530,
			R.drawable.emoji_u1f531, R.drawable.emoji_u1f532,
			R.drawable.emoji_u1f533, R.drawable.emoji_u1f534,
			R.drawable.emoji_u1f535, R.drawable.emoji_u1f536,
			R.drawable.emoji_u1f537, R.drawable.emoji_u1f538,
			R.drawable.emoji_u1f539, R.drawable.emoji_u1f53a,
			R.drawable.emoji_u1f53b, R.drawable.emoji_u1f53c,
			R.drawable.emoji_u1f53d, R.drawable.emoji_u1f550,
			R.drawable.emoji_u1f551, R.drawable.emoji_u1f552,
			R.drawable.emoji_u1f553, R.drawable.emoji_u1f554,
			R.drawable.emoji_u1f555, R.drawable.emoji_u1f556,
			R.drawable.emoji_u1f557, R.drawable.emoji_u1f558,
			R.drawable.emoji_u1f559, R.drawable.emoji_u1f55a,
			R.drawable.emoji_u1f55b, R.drawable.emoji_u1f55c,
			R.drawable.emoji_u1f55d, R.drawable.emoji_u1f55e,
			R.drawable.emoji_u1f55f, R.drawable.emoji_u1f560,
			R.drawable.emoji_u1f561, R.drawable.emoji_u1f562,
			R.drawable.emoji_u1f563, R.drawable.emoji_u1f564,
			R.drawable.emoji_u1f565, R.drawable.emoji_u1f566,
			R.drawable.emoji_u1f567, R.drawable.emoji_u1f5fb,
			R.drawable.emoji_u1f5fc, R.drawable.emoji_u1f5fd,
			R.drawable.emoji_u1f5fe, R.drawable.emoji_u1f5ff,
			R.drawable.emoji_u1f600, R.drawable.emoji_u1f601,
			R.drawable.emoji_u1f602, R.drawable.emoji_u1f603,
			R.drawable.emoji_u1f604, R.drawable.emoji_u1f605,
			R.drawable.emoji_u1f606, R.drawable.emoji_u1f607,
			R.drawable.emoji_u1f608, R.drawable.emoji_u1f609,
			R.drawable.emoji_u1f60a, R.drawable.emoji_u1f60b,
			R.drawable.emoji_u1f60c, R.drawable.emoji_u1f60d,
			R.drawable.emoji_u1f60e, R.drawable.emoji_u1f60f,
			R.drawable.emoji_u1f610, R.drawable.emoji_u1f611,
			R.drawable.emoji_u1f612, R.drawable.emoji_u1f613,
			R.drawable.emoji_u1f614, R.drawable.emoji_u1f615,
			R.drawable.emoji_u1f616, R.drawable.emoji_u1f617,
			R.drawable.emoji_u1f618, R.drawable.emoji_u1f619,
			R.drawable.emoji_u1f61a, R.drawable.emoji_u1f61b,
			R.drawable.emoji_u1f61c, R.drawable.emoji_u1f61d,
			R.drawable.emoji_u1f61e, R.drawable.emoji_u1f61f,
			R.drawable.emoji_u1f620, R.drawable.emoji_u1f621,
			R.drawable.emoji_u1f622, R.drawable.emoji_u1f623,
			R.drawable.emoji_u1f624, R.drawable.emoji_u1f625,
			R.drawable.emoji_u1f626, R.drawable.emoji_u1f627,
			R.drawable.emoji_u1f628, R.drawable.emoji_u1f629,
			R.drawable.emoji_u1f62a, R.drawable.emoji_u1f62b,
			R.drawable.emoji_u1f62c, R.drawable.emoji_u1f62d,
			R.drawable.emoji_u1f62e, R.drawable.emoji_u1f62f,
			R.drawable.emoji_u1f630, R.drawable.emoji_u1f631,
			R.drawable.emoji_u1f632, R.drawable.emoji_u1f633,
			R.drawable.emoji_u1f634, R.drawable.emoji_u1f635,
			R.drawable.emoji_u1f636, R.drawable.emoji_u1f637,
			R.drawable.emoji_u1f638, R.drawable.emoji_u1f639,
			R.drawable.emoji_u1f63a, R.drawable.emoji_u1f63b,
			R.drawable.emoji_u1f63c, R.drawable.emoji_u1f63d,
			R.drawable.emoji_u1f63e, R.drawable.emoji_u1f63f,
			R.drawable.emoji_u1f640, R.drawable.emoji_u1f645,
			R.drawable.emoji_u1f646, R.drawable.emoji_u1f647,
			R.drawable.emoji_u1f648, R.drawable.emoji_u1f649,
			R.drawable.emoji_u1f64a, R.drawable.emoji_u1f64b,
			R.drawable.emoji_u1f64c, R.drawable.emoji_u1f64d,
			R.drawable.emoji_u1f64e, R.drawable.emoji_u1f64f,
			R.drawable.emoji_u1f680, R.drawable.emoji_u1f681,
			R.drawable.emoji_u1f682, R.drawable.emoji_u1f683,
			R.drawable.emoji_u1f684, R.drawable.emoji_u1f685,
			R.drawable.emoji_u1f686, R.drawable.emoji_u1f687,
			R.drawable.emoji_u1f688, R.drawable.emoji_u1f689,
			R.drawable.emoji_u1f68a, R.drawable.emoji_u1f68b,
			R.drawable.emoji_u1f68c, R.drawable.emoji_u1f68d,
			R.drawable.emoji_u1f68e, R.drawable.emoji_u1f68f,
			R.drawable.emoji_u1f690, R.drawable.emoji_u1f691,
			R.drawable.emoji_u1f692, R.drawable.emoji_u1f693,
			R.drawable.emoji_u1f694, R.drawable.emoji_u1f695,
			R.drawable.emoji_u1f696, R.drawable.emoji_u1f697,
			R.drawable.emoji_u1f698, R.drawable.emoji_u1f699,
			R.drawable.emoji_u1f69a, R.drawable.emoji_u1f69b,
			R.drawable.emoji_u1f69c, R.drawable.emoji_u1f69d,
			R.drawable.emoji_u1f69e, R.drawable.emoji_u1f69f,
			R.drawable.emoji_u1f6a0, R.drawable.emoji_u1f6a1,
			R.drawable.emoji_u1f6a2, R.drawable.emoji_u1f6a3,
			R.drawable.emoji_u1f6a4, R.drawable.emoji_u1f6a5,
			R.drawable.emoji_u1f6a6, R.drawable.emoji_u1f6a7,
			R.drawable.emoji_u1f6a8, R.drawable.emoji_u1f6a9,
			R.drawable.emoji_u1f6aa, R.drawable.emoji_u1f6ab,
			R.drawable.emoji_u1f6ac, R.drawable.emoji_u1f6ad,
			R.drawable.emoji_u1f6ae, R.drawable.emoji_u1f6af,
			R.drawable.emoji_u1f6b0, R.drawable.emoji_u1f6b1,
			R.drawable.emoji_u1f6b2, R.drawable.emoji_u1f6b3,
			R.drawable.emoji_u1f6b4, R.drawable.emoji_u1f6b5,
			R.drawable.emoji_u1f6b6, R.drawable.emoji_u1f6b7,
			R.drawable.emoji_u1f6b8, R.drawable.emoji_u1f6b9,
			R.drawable.emoji_u1f6ba, R.drawable.emoji_u1f6bb,
			R.drawable.emoji_u1f6bc, R.drawable.emoji_u1f6bd,
			R.drawable.emoji_u1f6be, R.drawable.emoji_u1f6bf,
			R.drawable.emoji_u1f6c0, R.drawable.emoji_u1f6c1,
			R.drawable.emoji_u1f6c2, R.drawable.emoji_u1f6c3,
			R.drawable.emoji_u1f6c4, R.drawable.emoji_u1f6c5,
			R.drawable.emoji_u2002, R.drawable.emoji_u2003,
			R.drawable.emoji_u2005, R.drawable.emoji_u203c,
			R.drawable.emoji_u2049, R.drawable.emoji_u20e3,
			R.drawable.emoji_u2122, R.drawable.emoji_u2139,
			R.drawable.emoji_u2194, R.drawable.emoji_u2195,
			R.drawable.emoji_u2196, R.drawable.emoji_u2197,
			R.drawable.emoji_u2198, R.drawable.emoji_u2199,
			R.drawable.emoji_u21a9, R.drawable.emoji_u21aa,
			R.drawable.emoji_u231a, R.drawable.emoji_u231b,
			R.drawable.emoji_u23e9, R.drawable.emoji_u23ea,
			R.drawable.emoji_u23eb, R.drawable.emoji_u23ec,
			R.drawable.emoji_u23f0, R.drawable.emoji_u23f3,
			R.drawable.emoji_u24c2, R.drawable.emoji_u25aa,
			R.drawable.emoji_u25ab, R.drawable.emoji_u25b6,
			R.drawable.emoji_u25c0, R.drawable.emoji_u25fb,
			R.drawable.emoji_u25fc, R.drawable.emoji_u25fd,
			R.drawable.emoji_u25fe, R.drawable.emoji_u2600,
			R.drawable.emoji_u2601, R.drawable.emoji_u260e,
			R.drawable.emoji_u2611, R.drawable.emoji_u2614,
			R.drawable.emoji_u2615, R.drawable.emoji_u261d,
			R.drawable.emoji_u263a, R.drawable.emoji_u2648,
			R.drawable.emoji_u2649, R.drawable.emoji_u264a,
			R.drawable.emoji_u264b, R.drawable.emoji_u264c,
			R.drawable.emoji_u264d, R.drawable.emoji_u264e,
			R.drawable.emoji_u264f, R.drawable.emoji_u2650,
			R.drawable.emoji_u2651, R.drawable.emoji_u2652,
			R.drawable.emoji_u2653, R.drawable.emoji_u2660,
			R.drawable.emoji_u2663, R.drawable.emoji_u2665,
			R.drawable.emoji_u2666, R.drawable.emoji_u2668,
			R.drawable.emoji_u267b, R.drawable.emoji_u267f,
			R.drawable.emoji_u2693, R.drawable.emoji_u26a0,
			R.drawable.emoji_u26a1, R.drawable.emoji_u26aa,
			R.drawable.emoji_u26ab, R.drawable.emoji_u26bd,
			R.drawable.emoji_u26be, R.drawable.emoji_u26c4,
			R.drawable.emoji_u26c5, R.drawable.emoji_u26ce,
			R.drawable.emoji_u26d4, R.drawable.emoji_u26ea,
			R.drawable.emoji_u26f2, R.drawable.emoji_u26f3,
			R.drawable.emoji_u26f5, R.drawable.emoji_u26fa,
			R.drawable.emoji_u26fd, R.drawable.emoji_u2702,
			R.drawable.emoji_u2705, R.drawable.emoji_u2708,
			R.drawable.emoji_u2709, R.drawable.emoji_u270a,
			R.drawable.emoji_u270b, R.drawable.emoji_u270c,
			R.drawable.emoji_u270f, R.drawable.emoji_u2712,
			R.drawable.emoji_u2714, R.drawable.emoji_u2716,
			R.drawable.emoji_u2728, R.drawable.emoji_u2733,
			R.drawable.emoji_u2734, R.drawable.emoji_u2744,
			R.drawable.emoji_u2747, R.drawable.emoji_u274c,
			R.drawable.emoji_u274e, R.drawable.emoji_u2753,
			R.drawable.emoji_u2754, R.drawable.emoji_u2755,
			R.drawable.emoji_u2757, R.drawable.emoji_u2762,
			R.drawable.emoji_u2764, R.drawable.emoji_u2795,
			R.drawable.emoji_u2796, R.drawable.emoji_u2797,
			R.drawable.emoji_u27a1, R.drawable.emoji_u27b0,
			R.drawable.emoji_u27bf, R.drawable.emoji_u2934,
			R.drawable.emoji_u2935, R.drawable.emoji_u2b05,
			R.drawable.emoji_u2b06, R.drawable.emoji_u2b07,
			R.drawable.emoji_u2b1b, R.drawable.emoji_u2b1c,
			R.drawable.emoji_u2b50, R.drawable.emoji_u2b55,
			R.drawable.emoji_u3030, R.drawable.emoji_u303d,
			R.drawable.emoji_u3297, R.drawable.emoji_u3299,
			R.drawable.emoji_ufe4e5, R.drawable.emoji_ufe4e6,
			R.drawable.emoji_ufe4e7, R.drawable.emoji_ufe4e8,
			R.drawable.emoji_ufe4e9, R.drawable.emoji_ufe4ea,
			R.drawable.emoji_ufe4eb, R.drawable.emoji_ufe4ec,
			R.drawable.emoji_ufe4ed, R.drawable.emoji_ufe4ee,
			R.drawable.emoji_ufe82c, R.drawable.emoji_ufe82e,
			R.drawable.emoji_ufe82f, R.drawable.emoji_ufe830,
			R.drawable.emoji_ufe831, R.drawable.emoji_ufe832,
			R.drawable.emoji_ufe833, R.drawable.emoji_ufe834,
			R.drawable.emoji_ufe835, R.drawable.emoji_ufe836,
			R.drawable.emoji_ufe837 };
}