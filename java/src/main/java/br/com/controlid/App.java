package br.com.controlid;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class App {

	//ESCPOS
	private static final byte GS = 0x1d;
	private static final byte ESC = 0x1b;

	private static final byte AT = 0x40;
	private static final byte V = 0x56;
	private static final byte EXCLAMATION = 0x21;
	private static final byte HIFEN = 0x2d;
	private static final byte d = 0x64;
	private static final byte PARENTHESES_OPEN = 0x28;
	private static final byte L = 0x4c;
	private static final byte a = 0x61;
	private static final byte E = 0x45;
	private static final byte b = 0x62;
	private static final byte r = 0x72;

	//COMANDOS
	private static final byte[] INIT = {ESC, AT};
	private static final byte[] FEED = {ESC, d, 0};
	private static final byte[] PRINT_LOGO = {GS, PARENTHESES_OPEN, L, 6, 0, 0x30, 0x45, 32, 32, 1, 1};

	private static final byte[] CUT_PARTIAL = {GS, V, 1};
	private static final byte[] CUT_FULL = {GS, V, 0};

	private static final byte[] UNDERLINE = {ESC, HIFEN, 1};
	private static final byte[] DOUBLE_UNDERLINE = {ESC, HIFEN, 2};

	private static final byte[] ALIGN_LEFT = {ESC, a, 0};
	private static final byte[] ALIGN_CENTER = {ESC, a, 1};
	private static final byte[] ALIGN_RIGHT = {ESC, a, 2};

	private static final byte[] SELECT = {ESC, EXCLAMATION, 0};
	private static final byte[] SELECT_FONT1 = {ESC, EXCLAMATION, 0};
	private static final byte[] SELECT_FONT1_EMPHASIZED = {ESC, EXCLAMATION, 8};
	private static final byte[] SELECT_FONT1_UNDERLINE = {ESC, EXCLAMATION, (byte) 128};
	private static final byte[] SELECT_FONT1_DOUBLEWIDTH = {ESC, EXCLAMATION, 32};
	private static final byte[] SELECT_FONT1_DOUBLEHEIGHT = {ESC, EXCLAMATION, 16};

	private static final byte[] SELECT_FONT2 = {ESC, EXCLAMATION, 1};
	private static final byte[] SELECT_FONT2_EMPHASIZED = {ESC, EXCLAMATION, 9};
	private static final byte[] SELECT_FONT2_UNDERLINE = {ESC, EXCLAMATION, (byte) 129};
	private static final byte[] SELECT_FONT2_DOUBLEWIDTH = {ESC, EXCLAMATION, 33};
	private static final byte[] SELECT_FONT2_DOUBLEHEIGHT = {ESC, EXCLAMATION, 17};
	
	private static final byte[] STATUS_CASH_DRAWER = {GS, r, 2};

	//CONSTANTES
	private static final int UM_SEGUNDO = 1000;
	private static final int DOIS_SEGUNDOS = 2 * UM_SEGUNDO;
	private static final int CINCO_SEGUNDOS = 5 * UM_SEGUNDO;
	private static final int DEZ_SEGUNDOS = 2 * CINCO_SEGUNDOS;
	private static final int FEED_SIZE = 3;
	private static final String ADDRESS = "192.168.121.3";
	private static final int PORT = 9100;

	public static void main(String[] args) throws Exception {
		Socket clientSocket = new Socket(ADDRESS, PORT);

		OutputStream outputStream = clientSocket.getOutputStream();
		changeFeedSize();

		outputStream.write(STATUS_CASH_DRAWER);
		InputStream inputStream = clientSocket.getInputStream();

		byte[] bytes = new byte[1];
		int read = inputStream.read(bytes, 0, 1);
		System.out.println("Read: " + read);

		closeAll(clientSocket, outputStream);
	}

	private static void changeFeedSize() {
		FEED[2] = (byte) FEED_SIZE;
	}

	private static void closeAll(Closeable... closeables) {
		for (Closeable closeable : closeables) {
			try {
				closeable.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
