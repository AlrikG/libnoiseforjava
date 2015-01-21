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

/**
 * Enumerates the noise quality.
 */
public enum NoiseQuality {
    /**
     * Generates coherent noise quickly.
     * <p>
     * When a coherent-noise function with this quality setting is used to generate a bump-map
     * image, there are noticeable "creasing" artifacts in the resulting image. This is because the
     * derivative of that function is discontinuous at integer boundaries.
     */
    QUALITY_FAST,

    /**
     * Generates standard-quality coherent noise.
     * <p>
     * When a coherent-noise function with this quality setting is used to generate a bump-map
     * image, there are some minor "creasing" artifacts in the resulting image. This is because the
     * second derivative of that function is discontinuous at integer boundaries.
     */
    QUALITY_STD,

    /**
     * Generates the best-quality coherent noise.
     * <p>
     * When a coherent-noise function with this quality setting is used to generate a bump-map
     * image, there are no "creasing" artifacts in the resulting image. This is because the first
     * and second derivatives of that function are continuous at integer boundaries.
     */
    QUALITY_BEST
}
