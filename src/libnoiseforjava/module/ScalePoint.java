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
 * Noise module that scales the coordinates of the input value before returning
 * the output value from a source module.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/modulescalebias.png">
 * 
 * <p>
 * The getValue() method multiplies the ( x, y, z ) coordinates of the input
 * value with a scaling factor before returning the output value from the source
 * module. To set the scaling factor, call the setScale() method. To set the
 * scaling factor to apply to the individual x, y, or z coordinates, call the
 * setXScale(), setYScale() or setZScale() methods, respectively.
 * 
 * <p>
 * This noise module requires one source module.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1ScaleBias.html">noise::module:ScaleBias</a>
 */
public class ScalePoint extends ModuleBase {

    /**
     * Default scaling factor applied to the x coordinate for the ScalePoint
     * noise module.
     */
    static final double DEFAULT_SCALE_POINT_X = 1.0;

    /**
     * Default scaling factor applied to the y coordinate for the ScalePoint
     * noise module.
     */
    static final double DEFAULT_SCALE_POINT_Y = 1.0;

    /**
     * Default scaling factor applied to the z coordinate for the ScalePoint
     * noise module.
     */
    static final double DEFAULT_SCALE_POINT_Z = 1.0;

    /**
     * Scaling factor applied to the x coordinate of the input value.
     */
    double xScale;

    /**
     * Scaling factor applied to the y coordinate of the input value.
     */
    double yScale;

    /**
     * Scaling factor applied to the z coordinate of the input value.
     */
    double zScale;

    public ScalePoint(ModuleBase sourceModule) {
        super(1);
        setSourceModule(0, sourceModule);

        this.xScale = DEFAULT_SCALE_POINT_X;
        this.yScale = DEFAULT_SCALE_POINT_Y;
        this.zScale = DEFAULT_SCALE_POINT_Z;
    }

    /**
     * The getValue() method multiplies the ( x, y, z ) coordinates of the input
     * value with a scaling factor before returning the output value from the
     * source module.
     */
    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);

        return this.sourceModules[0].getValue(x * this.xScale, y * this.yScale, z * this.zScale);
    }

    /**
     * Returns the scaling factor applied to the @a x coordinate of the input
     * value.
     *
     * @return The scaling factor applied to the @a x coordinate.
     */
    public double getXScale() {
        return this.xScale;
    }

    /**
     * Returns the scaling factor applied to the y coordinate of the input
     * value.
     *
     * @return The scaling factor applied to the y coordinate.
     */
    public double getYScale() {
        return this.yScale;
    }

    /**
     * Returns the scaling factor applied to the z coordinate of the input
     * value.
     *
     * @return The scaling factor applied to the z coordinate.
     */
    public double getZScale() {
        return this.zScale;
    }

    /**
     * Sets the scaling factor to apply to the input value.
     *
     * @param scale The scaling factor to apply.
     */
    public void setScale(double scale) {
        this.xScale = scale;
        this.yScale = scale;
        this.zScale = scale;
    }

    /**
     * Sets the scaling factor to apply to the ( x, y, z ) coordinates of the
     * input value.
     *
     * @param xScale The scaling factor to apply to the x coordinate.
     * @param yScale The scaling factor to apply to the y coordinate.
     * @param zScale The scaling factor to apply to the z coordinate.
     */
    public void setScale(double xScale, double yScale, double zScale) {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
    }

    /**
     * Sets the scaling factor to apply to the x coordinate of the input value.
     *
     * @param xScale The scaling factor to apply to the x coordinate.
     */
    public void setXScale(double xScale) {
        this.xScale = xScale;
    }

    /**
     * Sets the scaling factor to apply to the y coordinate of the input value.
     *
     * @param yScale The scaling factor to apply to the y coordinate.
     */
    public void setYScale(double yScale) {
        this.yScale = yScale;
    }

    /**
     * Sets the scaling factor to apply to the z coordinate of the input value.
     *
     * @param zScale The scaling factor to apply to the z coordinate.
     */
    public void setZScale(double zScale) {
        this.zScale = zScale;
    }
}
