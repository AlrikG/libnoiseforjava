/*******************************************************************************
 *  Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 *  Copyright � 2010 Thomas J. Hodge (java port of libnoise)
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
 * Noise module that caches the last output value generated by a source module.
 * 
 * <p>
 * If an application passes an input value to the getValue() method that differs
 * from the previously passed-in input value, this noise module instructs the
 * source module to calculate the output value. This value, as well as the ( x,
 * y, z ) coordinates of the input value, are stored (cached) in this noise
 * module.
 * 
 * <p>
 * If the application passes an input value to the getValue() method that is
 * equal to the previously passed-in input value, this noise module returns the
 * cached output value without having the source module recalculate the output
 * value.
 * 
 * <p>
 * If an application passes a new source module to the setSourceModule() method,
 * the cache is invalidated.
 * 
 * <p>
 * Caching a noise module is useful if it is used as a source module for
 * multiple noise modules. If a source module is not cached, the source module
 * will redundantly calculate the same output value once for each noise module
 * in which it is included.
 * 
 * <p>
 * This noise module requires one source module.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Cached.html">noise::module::Cached</a>
 */
public class Cached extends ModuleBase {

    /**
     * The cached output value at the cached input value.
     */
    double cachedValue;

    /**
     * Determines if a cached output value is stored in this noise module.
     */
    boolean isCached;

    /**
     * @a x coordinate of the cached input value.
     */
    double xCache;

    /**
     * @a y coordinate of the cached input value.
     */
    double yCache;

    /**
     * @a z coordinate of the cached input value.
     */
    double zCache;

    public Cached(ModuleBase sourceModule) throws IllegalArgumentException {
        super(1);
        setSourceModule(0, sourceModule);
        this.isCached = false;
    }

    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);

        if (!(this.isCached && x == this.xCache && y == this.yCache && z == this.zCache)) {
            this.cachedValue = this.sourceModules[0].getValue(x, y, z);
            this.xCache = x;
            this.yCache = y;
            this.zCache = z;
        }

        this.isCached = true;

        return this.cachedValue;
    }
}
