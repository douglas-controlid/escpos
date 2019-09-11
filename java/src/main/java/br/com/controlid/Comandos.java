package br.com.controlid;

public enum Comandos {

	ESC(0x1b);

	private byte value;

	Comandos(int value) {
		this.value = (byte) value;
	}
}
