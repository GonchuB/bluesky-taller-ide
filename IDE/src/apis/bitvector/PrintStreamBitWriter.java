/*
 * Copyright 2007 Tom Gibara
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
package apis.bitvector;

import java.io.PrintStream;


public class PrintStreamBitWriter extends AbstractBitWriter {

	private final PrintStream stream;
	
	public PrintStreamBitWriter() {
		stream = System.out;
	}
	
	public PrintStreamBitWriter(PrintStream stream) {
		if (stream == null) throw new IllegalArgumentException("null stream");
		this.stream = stream;
	}
	
    @Override
    public int writeBit(int bit) {
        String s = (bit & 1) == 1 ? "1" : "0";
        stream.print(s);
        return 1;
    }
    
}
