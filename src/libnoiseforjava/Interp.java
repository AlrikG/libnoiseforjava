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

public class Interp {

    /**
     * Performs cubic interpolation between two values bound between two other values.
     * <p>
     * The alpha value should range from 0.0 to 1.0. If the alpha value is 0.0, this function
     * returns <i>n1</i>. If the alpha value is 1.0, this function returns <i>n2</i>.
     *
     * @param n0
     *            The value before the first value.
     * @param n1
     *            The first value.
     * @param n2
     *            The second value.
     * @param n3
     *            The value after the second value.
     * @param a
     *            The alpha value.
     *
     * @return The interpolated value.
     */
    public static double cubicInterp(double n0, double n1, double n2, double n3, double a) {
        double p = (n3 - n2) - (n0 - n1);
        double q = (n0 - n1) - p;
        double r = n2 - n0;
        double s = n1;
        return p * a * a * a + q * a * a + r * a + s;
    }

    /**
     * Performs linear interpolation between two values.
     * <p>
     * The alpha value should range from 0.0 to 1.0. If the alpha value is 0.0, this function
     * returns <i>n0</i>. If the alpha value is 1.0, this function returns <i>n1</i>.
     * 
     * @param n0
     *            The first value.
     * @param n1
     *            The second value.
     * @param a
     *            The alpha value.
     *
     * @return The interpolated value.
     */
    public static double lerp(double n0, double n1, double a) {
        return ((1.0 - a) * n0) + (a * n1);
    }

    /**
     * Maps a value onto a cubic S-curve.
     * <p>
     * The derivative of a cubic S-curve is zero at <i>a</i> = 0.0 and <i>a</i> = 1.0.
     * 
     * <i>a</i> should range from 0.0 to 1.0.
     * 
     * @param a
     *            The value to map onto a cubic S-curve.
     *
     * @return The mapped value.
     */
    public static double SCurve3(double a) {
        return (a * a * (3.0 - 2.0 * a));
    }

    /**
     * Maps a value onto a quintic S-curve.
     * <p>
     * The first derivative of a quintic S-curve is zero at <i>a</i> = 0.0 and <i>a</i> = 1.0
     * <p>
     * The second derivative of a quintic S-curve is zero at <i>a</i> = 0.0 and <i>a</i> = 1.0
     * <p>
     * <i>a</i> should range from 0.0 to 1.0.
     *
     * @param a
     *            The value to map onto a quintic S-curve.
     *
     * @return The mapped value.
     */
    public static double SCurve5(double a) {
        return a * a * a * (a * (a * 6 - 15) + 10);
    }

}
