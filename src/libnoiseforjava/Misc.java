/*******************************************************************************
 *  Copyright (c) 2003, 2004 Jason Bevins (original libnoise code)
 *  Copyright (c) 2010 Thomas J. Hodge (java port of libnoise)
 *  Copyright (c) Nick Whitney ( changed noisegen to perlin basis. added javadoc)
 *  
 *  This file is part of libnoiseforjava.
 *  
 *  libnoiseforjava is a Java port of the C++ library libnoise, which may be
 *  found at http://libnoise.sourceforge.net/. libnoise was developed by Jason
 *  Bevins, who may be contacted at jlbezigvins@gmzigail.com (for great email,
 *  take off every 'zig'). Porting to Java was done by Thomas Hodge, who may be
 *  contacted at libnoisezagforjava@gzagmail.com (remove every 'zag').
 *  
 *  libnoiseforjava is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU General Public License as published by the Free
 *  Software Foundation, either version 3 of the License, or (at your option) any
 *  later version.
 *  
 *  libnoiseforjava is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 *  details.
 *  
 *  You should have received a copy of the GNU General Public License along with
 *  libnoiseforjava. If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/

package libnoiseforjava;

public class Misc {

    /**
     * Clamps a value onto a clamping range.
     * <p>
     * This function does not modify any parameters.
     *
     * @param value
     *            The value to clamp.
     * @param lowerBound
     *            The lower bound of the clamping range.
     * @param upperBound
     *            The upper bound of the clamping range.
     *
     * @return <ul>
     *         <li><b>value</b> if value lies between lowerBound and upperBound. <li>
     *         <b>lowerBound</b> if value is less than lowerBound. <li><b>upperBound</b> if value is
     *         greater than upperBound.
     *         </ul>
     */
    public static int ClampValue(int value, int lowerBound, int upperBound) {
        if (value < lowerBound) {
            return lowerBound;
        } else if (value > upperBound) {
            return upperBound;
        } else {
            return value;
        }
    }

    /**
     * Modifies a floating-point value so that it can be stored in a int32 variable.
     * <p>
     * This function does not modify <b>n</b>.
     * <p>
     * In libnoise, the noise-generating algorithms are all integer-based; they use variables of
     * type int32. Before calling a noise function, pass the x, y, and z coordinates to this
     * function to ensure that these coordinates can be cast to a int32 value.
     * <p>
     * Although you could do a straight cast from double to int32, the resulting value may differ
     * between platforms. By using this function, you ensure that the resulting value is identical
     * between platforms.
     *
     * @param n
     *            A floating-point number.
     *
     * @return The modified floating-point number.
     */
    public static double MakeInt32Range(double n) {
        if (n >= 1073741824.0) {
            return (2.0 * (n % 1073741824.0)) - 1073741824.0;
        } else if (n <= -1073741824.0) {
            return (2.0 * (n % 1073741824.0)) + 1073741824.0;
        } else {
            return n;
        }
    }
}
