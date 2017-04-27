/**
 * 
 */
package com.hh.system.result;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Spectrum
 *
 */
public class StreamConnector {
	public StreamConnector(InputStream inStream, OutputStream outStream) {
		this.inStream = inStream;
		this.outStream = outStream;
	}
	
	private InputStream inStream;
	private OutputStream outStream;
	
	public static final int DEFAULT_STREAM_BUFFER_SIZE = 4096;
	public static final int MINIMUM_STREAM_BUFFER_SIZE = 256;
	public static final int MAXIMUM_STREAM_BUFFER_SIZE = 0x100000;
	
	private static int defaultBufferSize = DEFAULT_STREAM_BUFFER_SIZE;
	
	public static int getDefaultBufferSize() {
		return defaultBufferSize;
	}
	
	public static void setDefaultBufferSize(int defaultStreamBuffer) {
		int buff = toLegalSize(defaultStreamBuffer);
		defaultBufferSize = buff;
	}

	private static int toLegalSize(int defaultStreamBuffer) {
		int buff = Math.max(MINIMUM_STREAM_BUFFER_SIZE, defaultStreamBuffer);
		buff = Math.min(MAXIMUM_STREAM_BUFFER_SIZE, buff);
		return buff;
	}
	
	private int bufferSize = 0;

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = toLegalSize(bufferSize);
	}
	
	public void flush() throws IOException {
		int bsize = bufferSize == 0 ? defaultBufferSize : bufferSize;
		byte[] b = new byte[bsize];
		int read = -1;
		while ((read = inStream.read(b)) >= 0) {
			if (read > 0)
				outStream.write(b, 0, read);
		}
	}
}
