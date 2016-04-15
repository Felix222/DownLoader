package com.felixpc.down.downloader.mode.tool;

import java.io.Closeable;
import java.io.IOException;

public class IOUtils {
	/** ¹Ø±ÕÁ÷ */
	public static boolean close(Closeable io) {
		if (io != null) {
			try {
				io.close();
			} catch (IOException e) {
			}
		}
		return true;
	}
}
