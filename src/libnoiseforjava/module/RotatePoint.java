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
 * Noise module that rotates the input value around the origin before returning
 * the output value from a source module.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/modulerotatepoint.png">
 * 
 * <p>
 * The getValue() method rotates the coordinates of the input value around the
 * origin before returning the output value from the source module. To set the
 * rotation angles, call the setAngles() method. To set the rotation angle
 * around the individual @a x, @a y, or @a z axes, call the setXAngle(),
 * setYAngle() or setZAngle() methods, respectively.
 * 
 * <p>
 * The coordinate system of the input value is assumed to be "left-handed" (@a x
 * increases to the right, @a y increases upward, and @a z increases inward.)
 * 
 * <p>
 * This noise module requires one source module.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1RotatePoint.html">noise::module:RotatePoint</a>
 */
public class RotatePoint extends ModuleBase {

    /**
     * Default x rotation angle for the RotatePoint noise module.
     */
    static final double DEFAULT_ROTATE_X = 0.0;

    /**
     * Default y rotation angle for the RotatePoint noise module.
     */
    static final double DEFAULT_ROTATE_Y = 0.0;

    /**
     * Default z rotation angle for the RotatePoint noise module.
     */
    static final double DEFAULT_ROTATE_Z = 0.0;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double x1Matrix;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double x2Matrix;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double x3Matrix;

    /**
     * x rotation angle applied to the input value, in degrees.
     */
    double xAngle;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double y1Matrix;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double y2Matrix;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double y3Matrix;

    /**
     * y rotation angle applied to the input value, in degrees.
     */
    double yAngle;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double z1Matrix;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double z2Matrix;

    /**
     * An entry within the 3x3 rotation matrix used for rotating the input
     * value.
     */
    double z3Matrix;

    /**
     * z rotation angle applied to the input value, in degrees.
     */
    double zAngle;

    public RotatePoint(ModuleBase sourceModule) throws IllegalArgumentException {
        super(1);
        setSourceModule(0, sourceModule);
        setAngles(DEFAULT_ROTATE_X, DEFAULT_ROTATE_Y, DEFAULT_ROTATE_Z);
    }

    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);

        double nx = (this.x1Matrix * x) + (this.y1Matrix * y) + (this.z1Matrix * z);
        double ny = (this.x2Matrix * x) + (this.y2Matrix * y) + (this.z2Matrix * z);
        double nz = (this.x3Matrix * x) + (this.y3Matrix * y) + (this.z3Matrix * z);
        return this.sourceModules[0].getValue(nx, ny, nz);
    }

    public void setAngles(double xAngle, double yAngle, double zAngle) {
        double xCos, yCos, zCos, xSin, ySin, zSin;
        xCos = Math.cos(Math.toRadians(xAngle));
        yCos = Math.cos(Math.toRadians(yAngle));
        zCos = Math.cos(Math.toRadians(zAngle));
        xSin = Math.sin(Math.toRadians(xAngle));
        ySin = Math.sin(Math.toRadians(yAngle));
        zSin = Math.sin(Math.toRadians(zAngle));

        this.x1Matrix = ySin * xSin * zSin + yCos * zCos;
        this.y1Matrix = xCos * zSin;
        this.z1Matrix = ySin * zCos - yCos * xSin * zSin;
        this.x2Matrix = ySin * xSin * zCos - yCos * zSin;
        this.y2Matrix = xCos * zCos;
        this.z2Matrix = -yCos * xSin * zCos - ySin * zSin;
        this.x3Matrix = -ySin * xCos;
        this.y3Matrix = xSin;
        this.z3Matrix = yCos * xCos;

        this.xAngle = xAngle;
        this.yAngle = yAngle;
        this.zAngle = zAngle;
    }

    /**
     * Returns the rotation angle around the x axis to apply to the input value.
     *
     * @return The rotation angle around the x axis, in degrees.
     */
    public double getXAngle() {
        return this.xAngle;
    }

    /**
     * Returns the rotation angle around the @a y axis to apply to the input
     * value.
     *
     * @return The rotation angle around the @a y axis, in degrees.
     */
    public double getYAngle() {
        return this.yAngle;
    }

    /**
     * Returns the rotation angle around the @a z axis to apply to the input
     * value.
     *
     * @return The rotation angle around the @a z axis, in degrees.
     */
    public double getZAngle() {
        return this.zAngle;
    }

    /**
     * Sets the rotation angle around the x axis to apply to the input value.
     * 
     * <p>
     * The getValue() method rotates the coordinates of the input value around
     * the origin before returning the output value from the source module.
     *
     * @param xAngle The rotation angle around the x axis, in degrees.
     */
    public void setXAngle(double xAngle) {
        setAngles(xAngle, this.yAngle, this.zAngle);
    }

    /**
     * Sets the rotation angle around the @a y axis to apply to the input value.
     * 
     * <p>
     * The getValue() method rotates the coordinates of the input value around
     * the origin before returning the output value from the source module.
     *
     * @param yAngle The rotation angle around the @a y axis, in degrees.
     */
    public void SetYAngle(double yAngle) {
        setAngles(this.xAngle, yAngle, this.zAngle);
    }

    /**
     * Sets the rotation angle around the @a z axis to apply to the input value.
     * 
     * <p>
     * The getValue() method rotates the coordinates of the input value around
     * the origin before returning the output value from the source module.
     *
     * @param zAngle The rotation angle around the @a z axis, in degrees.
     */
    public void SetZAngle(double zAngle) {
        setAngles(this.xAngle, this.yAngle, zAngle);
    }

}
