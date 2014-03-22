/*
 * Copyright 2011 Tom Gibara
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package main.apis.bitvector;

//TODO implement transfer methods that move bits from readers to writers and make public
class BitStreams {

	private BitStreams() {}
	
	// package scoped
	
	static boolean isSameBits(main.apis.bitvector.BitReader r, main.apis.bitvector.BitReader s) {
		int rBit;
		int sBit;
		do {
			try {
				rBit = r.readBit();
			} catch (main.apis.bitvector.EndOfBitStreamException e) {
				rBit = -1;
			}
			try {
				sBit = s.readBit();
			} catch (main.apis.bitvector.EndOfBitStreamException e) {
				sBit = -1;
			}
			if (rBit != sBit) return false;
		} while (rBit != -1);
		return true;
	}
	
	static String bitsToString(main.apis.bitvector.BitReader reader) {
		StringBuilder sb = new StringBuilder();
		while (true) {
			try {
				sb.append(reader.readBoolean() ? '1' : '0');
			} catch (main.apis.bitvector.EndOfBitStreamException e) {
				return sb.toString();
			}
		}
	}
	
	static long countBits(main.apis.bitvector.BitReader reader) {
		return reader.skipBits(Long.MAX_VALUE);
	}
	
}
