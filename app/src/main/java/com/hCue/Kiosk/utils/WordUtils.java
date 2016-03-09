/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hCue.Kiosk.utils;

import android.text.TextUtils;

/**
 * <p>Operations on Strings that contain words.</p>
 * 
 * <p>This class tries to handle <code>null</code> input gracefully.
 * An exception will not be thrown for a <code>null</code> input.
 * Each method documents its behaviour in more detail.</p>
 * 
 * @since 2.0
 */
public class WordUtils {

	public WordUtils() {
		super();
	}

	// Capitalizing
	//-----------------------------------------------------------------------
	/**
	 * <p>Capitalizes all the whitespace separated words in a String.
	 * Only the first character of each word is changed. To convert the 
	 * rest of each word to lowercase at the same time, 
	 * use {@link #capitalizeFully(String)}.</p>
	 *
	 * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * A <code>null</code> input String returns <code>null</code>.
	 * Capitalization uses the Unicode title case, normally equivalent to
	 * upper case.</p>
	 *
	 * <pre>
	 * WordUtils.capitalize(null)        = null
	 * WordUtils.capitalize("")          = ""
	 * WordUtils.capitalize("i am FINE") = "I Am FINE"
	 * </pre>
	 * 
	 * @param str  the String to capitalize, may be null
	 * @return capitalized String, <code>null</code> if null String input
	 * @see #uncapitalize(String)
	 * @see #capitalizeFully(String)
	 */
	public static String capitalize(final String str) {
		return capitalize(str, null);
	}

	/**
	 * <p>Capitalizes all the delimiter separated words in a String.
	 * Only the first character of each word is changed. To convert the 
	 * rest of each word to lowercase at the same time, 
	 * use {@link #capitalizeFully(String, char[])}.</p>
	 *
	 * <p>The delimiters represent a set of characters understood to separate words.
	 * The first string character and the first non-delimiter character after a
	 * delimiter will be capitalized. </p>
	 *
	 * <p>A <code>null</code> input String returns <code>null</code>.
	 * Capitalization uses the Unicode title case, normally equivalent to
	 * upper case.</p>
	 *
	 * <pre>
	 * WordUtils.capitalize(null, *)            = null
	 * WordUtils.capitalize("", *)              = ""
	 * WordUtils.capitalize(*, new char[0])     = *
	 * WordUtils.capitalize("i am fine", null)  = "I Am Fine"
	 * WordUtils.capitalize("i aM.fine", {'.'}) = "I aM.Fine"
	 * </pre>
	 * 
	 * @param str  the String to capitalize, may be null
	 * @param delimiters  set of characters to determine capitalization, null means whitespace
	 * @return capitalized String, <code>null</code> if null String input
	 * @see #uncapitalize(String)
	 * @see #capitalizeFully(String)
	 * @since 2.1
	 */
	public static String capitalize(final String str, final char... delimiters) {
		final int delimLen = delimiters == null ? -1 : delimiters.length;
		if (TextUtils.isEmpty(str) || delimLen == 0) {
			return str;
		}
		final char[] buffer = str.toCharArray();
		boolean capitalizeNext = true;
		for (int i = 0; i < buffer.length; i++) {
			final char ch = buffer[i];
			if (isDelimiter(ch, delimiters)) {
				capitalizeNext = true;
			} else if (capitalizeNext) {
				buffer[i] = Character.toTitleCase(ch);
				capitalizeNext = false;
			}
		}
		return new String(buffer);
	}


	//-----------------------------------------------------------------------
	/**
	 * <p>Converts all the whitespace separated words in a String into capitalized words, 
	 * that is each word is made up of a titlecase character and then a series of 
	 * lowercase characters.  </p>
	 *
	 * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * A <code>null</code> input String returns <code>null</code>.
	 * Capitalization uses the Unicode title case, normally equivalent to
	 * upper case.</p>
	 *
	 * <pre>
	 * WordUtils.capitalizeFully(null)        = null
	 * WordUtils.capitalizeFully("")          = ""
	 * WordUtils.capitalizeFully("i am FINE") = "I Am Fine"
	 * </pre>
	 * 
	 * @param str  the String to capitalize, may be null
	 * @return capitalized String, <code>null</code> if null String input
	 */
	public static String capitalizeFully(final String str) {
		return capitalizeFully(str, null);
	}

	/**
	 * <p>Converts all the delimiter separated words in a String into capitalized words, 
	 * that is each word is made up of a titlecase character and then a series of 
	 * lowercase characters. </p>
	 *
	 * <p>The delimiters represent a set of characters understood to separate words.
	 * The first string character and the first non-delimiter character after a
	 * delimiter will be capitalized. </p>
	 *
	 * <p>A <code>null</code> input String returns <code>null</code>.
	 * Capitalization uses the Unicode title case, normally equivalent to
	 * upper case.</p>
	 *
	 * <pre>
	 * WordUtils.capitalizeFully(null, *)            = null
	 * WordUtils.capitalizeFully("", *)              = ""
	 * WordUtils.capitalizeFully(*, null)            = *
	 * WordUtils.capitalizeFully(*, new char[0])     = *
	 * WordUtils.capitalizeFully("i aM.fine", {'.'}) = "I am.Fine"
	 * </pre>
	 * 
	 * @param str  the String to capitalize, may be null
	 * @param delimiters  set of characters to determine capitalization, null means whitespace
	 * @return capitalized String, <code>null</code> if null String input
	 * @since 2.1
	 */
	public static String capitalizeFully(String str, final char... delimiters) {
		final int delimLen = delimiters == null ? -1 : delimiters.length;
		if (TextUtils.isEmpty(str) || delimLen == 0) {
			return str;
		}
		str = str.toLowerCase();
		return capitalize(str, delimiters);
	}

	//-----------------------------------------------------------------------
	/**
	 * <p>Uncapitalizes all the whitespace separated words in a String.
	 * Only the first character of each word is changed.</p>
	 *
	 * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * A <code>null</code> input String returns <code>null</code>.</p>
	 *
	 * <pre>
	 * WordUtils.uncapitalize(null)        = null
	 * WordUtils.uncapitalize("")          = ""
	 * WordUtils.uncapitalize("I Am FINE") = "i am fINE"
	 * </pre>
	 * 
	 * @param str  the String to uncapitalize, may be null
	 * @return uncapitalized String, <code>null</code> if null String input
	 * @see #capitalize(String)
	 */
	public static String uncapitalize(final String str) {
		return uncapitalize(str, null);
	}

	/**
	 * <p>Uncapitalizes all the whitespace separated words in a String.
	 * Only the first character of each word is changed.</p>
	 *
	 * <p>The delimiters represent a set of characters understood to separate words.
	 * The first string character and the first non-delimiter character after a
	 * delimiter will be uncapitalized. </p>
	 *
	 * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.
	 * A <code>null</code> input String returns <code>null</code>.</p>
	 *
	 * <pre>
	 * WordUtils.uncapitalize(null, *)            = null
	 * WordUtils.uncapitalize("", *)              = ""
	 * WordUtils.uncapitalize(*, null)            = *
	 * WordUtils.uncapitalize(*, new char[0])     = *
	 * WordUtils.uncapitalize("I AM.FINE", {'.'}) = "i AM.fINE"
	 * </pre>
	 * 
	 * @param str  the String to uncapitalize, may be null
	 * @param delimiters  set of characters to determine uncapitalization, null means whitespace
	 * @return uncapitalized String, <code>null</code> if null String input
	 * @see #capitalize(String)
	 * @since 2.1
	 */
	public static String uncapitalize(final String str, final char... delimiters) {
		final int delimLen = delimiters == null ? -1 : delimiters.length;
		if (TextUtils.isEmpty(str) || delimLen == 0) {
			return str;
		}
		final char[] buffer = str.toCharArray();
		boolean uncapitalizeNext = true;
		for (int i = 0; i < buffer.length; i++) {
			final char ch = buffer[i];
			if (isDelimiter(ch, delimiters)) {
				uncapitalizeNext = true;
			} else if (uncapitalizeNext) {
				buffer[i] = Character.toLowerCase(ch);
				uncapitalizeNext = false;
			}
		}
		return new String(buffer);
	}

	//-----------------------------------------------------------------------
	/**
	 * Is the character a delimiter.
	 *
	 * @param ch  the character to check
	 * @param delimiters  the delimiters
	 * @return true if it is a delimiter
	 */
	private static boolean isDelimiter(final char ch, final char[] delimiters) {
		if (delimiters == null) {
			return Character.isWhitespace(ch);
		}
		for (final char delimiter : delimiters) {
			if (ch == delimiter) {
				return true;
			}
		}
		return false;
	}

}
