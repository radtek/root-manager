/**
 * Copyright (C) 2014-2015 hewu <hewu2008@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.qihoo.permmgr;

import java.io.File;

import android.content.Context;

import com.qihoo.rtservice.NativeHelper;

public class LocalRoot {
	private static LocalRoot mInstance = null;
	private static final String TAG = LocalRoot.class.getSimpleName();

	public static LocalRoot getInstance() {
		if (mInstance == null)
			mInstance = new LocalRoot();
		return mInstance;
	}

	private native int jmain(int paramInt);

	private native void junmain(Class<?> paramClass);

	public int doRoot(Context context) {
		String libPath = NativeHelper.copyNativeLib(context,"lib360.so");
		NativeHelper.copyNativeLib(context, "Libpermc.so");
		NativeHelper.copyNativeLib(context, "libsu.so");
		System.load(libPath);
		int nRet = jmain(0);
		junmain(getClass());
		return nRet;
	}
	
	/**
	 * 动态库的路径
	 */
	public int doRoot(java.lang.String paramString, android.content.SharedPreferences paramSharedPreferences, c paramc) {
		System.load(paramString);
		int nRet = jmain(0);
		junmain(getClass());
		if (nRet != 3059 || paramc == null) {
			File file = new File(paramString);
			if (true == file.exists()) file.delete();
			return nRet;
		}
		for (int i = 0; i < 10; ++i) {
			if (paramc.onCheckRootServerExist() == true) {
				File file = new File(paramString);
				if (true == file.exists()) file.delete();
				return nRet;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nRet = -2000;
		return nRet;
	}
}