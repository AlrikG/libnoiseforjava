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

package libnoiseforjava.module;

/**
 * Noise module that outputs concentric spheres.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/modulespheres.png">
 * 
 * <p>
 * This noise module outputs concentric spheres centered on the origin like the
 * concentric rings of an onion.
 * 
 * <p>
 * The first sphere has a radius of 1.0. Each subsequent sphere has a radius
 * that is 1.0 unit larger than the previous sphere.
 * 
 * <p>
 * The output value from this noise module is determined by the distance between
 * the input value and the the nearest spherical surface. The input values that
 * are located on a spherical surface are given the output value 1.0 and the
 * input values that are equidistant from two spherical surfaces are given the
 * output value -1.0.
 * 
 * <p>
 * An application can change the frequency of the concentric spheres. Increasing
 * the frequency reduces the distances between spheres. To specify the
 * frequency, call the setFrequency() method.
 * 
 * <p>
 * This noise module, modified with some low-frequency, low-power turbulence, is
 * useful for generating agate-like textures.
 * 
 * <p>
 * This noise module does not require any source modules.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Spheres.html">noise::module:Spheres</a>
 */
public class Spheres extends ModuleBase {

    /**
     * Default frequency value for the Spheres noise module.
     */
    static final double DEFAULT_SPHERES_FREQUENCY = 1.0;

    /**
     * Frequency of the concentric spheres.
     */
    double frequency;

    public Spheres() {
        super(0);
        this.frequency = DEFAULT_SPHERES_FREQUENCY;
    }

    @Override
    public double getValue(double x, double y, double z) {
        x *= this.frequency;
        y *= this.frequency;
        z *= this.frequency;

        double distFromCenter = Math.sqrt(x * x + y * y + z * z);
        double distFromSmallerSphere = distFromCenter - Math.floor(distFromCenter);
        double distFromLargerSphere = 1.0 - distFromSmallerSphere;
        double nearestDist = Math.min(distFromSmallerSphere, distFromLargerSphere);
        return 1.0 - (nearestDist * 4.0); // Puts it in the -1.0 to +1.0 range.
    }

    /**
     * Returns the frequency of the concentric spheres.
     *
     * @return The frequency of the concentric spheres.
     */
    public double getFrequency() {
        return this.frequency;
    }

    /**
     * Sets the frequency of the concentric spheres.
     *
     * <p>
     * Increasing the frequency increases the density of the concentric spheres,
     * reducing the distances between them.
     * 
     * @param frequency The frequency of the concentric spheres.
     */
    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }
}
