package org.david.rain.monitor.monitor.util;


import org.david.rain.monitor.exception.DecoderException;

public class AuthCodeUtil {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	public static byte[] decodeHex(char[] data)  throws DecoderException{

		int len = data.length;

		if ((len & 0x01) != 0) {
			throw new DecoderException("Odd number of characters.");
		}

		byte[] out = new byte[len >> 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; j < len; i++) {
			int f = toDigit(data[j], j) << 4;
			j++;
			f = f | toDigit(data[j], j);
			j++;
			out[i] = (byte) (f & 0xFF);
		}

		return out;
	}

	/**
	 * Converts a hexadecimal character to an integer.
	 * 
	 * @param ch
	 *            A character to convert to an integer digit
	 * @param index
	 *            The index of the character in the source
	 * @return An integer
	 *             Thrown if ch is an illegal hex character
	 */
	private static int toDigit(char ch, int index) throws DecoderException {
		int digit = Character.digit(ch, 16);
		if (digit == -1) {
			throw new DecoderException("Illegal hexadecimal charcter " + ch
					+ " at index " + index);
		}
		return digit;
	}

	/**
	 * Converts an array of bytes into an array of characters representing the
	 * hexidecimal values of each byte in order. The returned array will be
	 * double the length of the passed array, as it takes two characters to
	 * represent any given byte.
	 * 
	 * @param data
	 *            a byte[] to convert to Hex characters
	 * @return A char[] containing hexidecimal characters
	 */
	public static char[] encodeHex(byte[] data) {

		int l = data.length;

		char[] out = new char[l << 1];

		// two characters form the hex value.
		for (int i = 0, j = 0; i < l; i++) {
			out[j++] = DIGITS[(0xF0 & data[i]) >>> 4];
			out[j++] = DIGITS[0x0F & data[i]];
		}

		return out;
	}
	
	public static void main(String args[])
	{
		String aa = "small";
		char [] bb = encodeHex(aa.getBytes());
		try
		{
			System.out.println(new String(decodeHex(bb)));
		} catch (DecoderException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


      public static byte[] hex2Byte(String b) {
		char data[] = b.toCharArray();
		int l = data.length;
		byte out[] = new byte[l >> 1];
		int i = 0;
		for (int j = 0; j < l;) {
			int f = Character.digit(data[j++], 16) << 4;
			f |= Character.digit(data[j++], 16);
			out[i] = (byte) (f & 0xff);
			i++;
		}

		return out;
	}
}
