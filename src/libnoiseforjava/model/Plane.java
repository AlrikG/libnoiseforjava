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

package libnoiseforjava.model;

import libnoiseforjava.module.ModuleBase;

/**
 * Model that defines the surface of a plane.
 * 
 * <p>
 * This model returns an output value from a noise module given the coordinates
 * of an input value located on the surface of an ( @a x, @a z ) plane.
 * 
 * <p>
 * To generate an output value, pass the ( @a x, @a z ) coordinates of an input
 * value to the GetValue() method.
 * 
 * <p>
 * This model is useful for creating: - two-dimensional textures - terrain
 * height maps for local areas
 * 
 * <p>
 * This plane extends infinitely in both directions.
 */
public class Plane {

    /**
     * A pointer to the noise module used to generate the output values.
     */
    ModuleBase module;

    public Plane() {
        this.module = new ModuleBase(1);
    }

    public Plane(ModuleBase module) {
        this.module = module;
    }

    /**
     * Returns the output value from the noise module given the ( @a x, @a z )
     * coordinates of the specified input value located on the surface of the
     * plane.
     * 
     * <p>
     * This output value is generated by the noise module passed to the
     * setModule() method.
     * 
     * @param x The @a x coordinate of the input value.
     * @param z The @a z coordinate of the input value.
     * 
     * @return The output value from the noise module.
     * 
     * @pre A noise module was passed to the setModule() method.
     */
    public double getValue(double x, double z) {
        assert (this.module != null);

        return this.module.getValue(x, 0, z);
    }

    /**
     * Returns the noise module that is used to generate the output values.
     * 
     * @return A reference to the noise module.
     * 
     * @pre A noise module was passed to the setModule() method.
     */
    public ModuleBase getModule() {
        assert (this.module != null);
        return this.module;
    }

    /**
     * Sets the noise module that is used to generate the output values.
     * 
     * <p>
     * This noise module must exist for the lifetime of this object, until you
     * pass a new noise module to this method.
     * 
     * @param module The noise module that is used to generate the output
     *            values.
     */
    public void setModule(ModuleBase module) {
        this.module = module;
    }
}
