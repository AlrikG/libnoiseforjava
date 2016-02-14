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
 * Noise module that moves the coordinates of the input value before returning
 * the output value from a source module.
 *
 * <p>
 * The getValue() method moves the ( @a x, @a y, @a z ) coordinates of the input
 * value by a translation amount before returning the output value from the
 * source module. To set the translation amount, call the setTranslation()
 * method. To set the translation amount to apply to the individual @a x, @a y,
 * or @a z coordinates, call the setXTranslation(), setYTranslation() or
 * setZTranslation() methods, respectively.
 *
 * <p>
 * This noise module requires one source module.
 */
public class TranslatePoint extends ModuleBase {

    /**
     * Default translation factor applied to the x coordinate for the
     * TranslatePoint noise module.
     */
    static final double DEFAULT_TRANSLATE_POINT_X = 0.0;

    /**
     * Default translation factor applied to the y coordinate for the
     * TranslatePoint noise module.
     */
    static final double DEFAULT_TRANSLATE_POINT_Y = 0.0;

    /**
     * Default translation factor applied to the z coordinate for the
     * TranslatePoint noise module.
     */
    static final double DEFAULT_TRANSLATE_POINT_Z = 0.0;

    /**
     * Translation amount applied to the x coordinate of the input value.
     */
    double xTranslation;

    /**
     * Translation amount applied to the y coordinate of the input value.
     */
    double yTranslation;

    /**
     * Translation amount applied to the z coordinate of the input value.
     */
    double zTranslation;

    public TranslatePoint(ModuleBase sourceModule) throws IllegalArgumentException {
        super(1);
        setSourceModule(0, sourceModule);
        this.xTranslation = DEFAULT_TRANSLATE_POINT_X;
        this.yTranslation = DEFAULT_TRANSLATE_POINT_Y;
        this.zTranslation = DEFAULT_TRANSLATE_POINT_Z;

    }

    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);

        return this.sourceModules[0].getValue(x + this.xTranslation, y + this.yTranslation, z + this.zTranslation);
    }

    /**
     * Returns the translation amount to apply to the x coordinate of the input
     * value.
     *
     * @return The translation amount to apply to the x coordinate.
     */
    public double getXTranslation() {
        return this.xTranslation;
    }

    /**
     * Returns the translation amount to apply to the y coordinate of the input
     * value.
     *
     * @return The translation amount to apply to the y coordinate.
     */
    public double getYTranslation() {
        return this.yTranslation;
    }

    /**
     * Returns the translation amount to apply to the z coordinate of the input
     * value.
     *
     * @return The translation amount to apply to the z coordinate.
     */
    public double getZTranslation() {
        return this.zTranslation;
    }

    /**
     * Sets the translation amount to apply to the input value.
     * 
     * <p>
     * The getValue() method moves the ( x, y, z ) coordinates of the input
     * value by a translation amount before returning the output value from the
     * source module.
     * 
     * @param translation The translation amount to apply.
     */
    public void setTranslation(double translation) {
        this.xTranslation = translation;
        this.yTranslation = translation;
        this.zTranslation = translation;
    }

    /**
     * Sets the translation amounts to apply to the ( x, y, z ) coordinates of
     * the input value.
     * 
     * <p>
     * The getValue() method moves the ( x, y, z ) coordinates of the input
     * value by a translation amount before returning the output value from the
     * source module.
     * 
     * @param xTranslation The translation amount to apply to the x coordinate.
     * @param yTranslation The translation amount to apply to the y coordinate.
     * @param zTranslation The translation amount to apply to the z coordinate.
     */
    public void setTranslation(double xTranslation, double yTranslation, double zTranslation) {
        this.xTranslation = xTranslation;
        this.yTranslation = yTranslation;
        this.zTranslation = zTranslation;
    }

    /**
     * Sets the translation amount to apply to the x coordinate of the input
     * value.
     * 
     * <p>
     * The getValue() method moves the ( x, y, z ) coordinates of the input
     * value by a translation amount before returning the output value from the
     * source module.
     * 
     * @param xTranslation The translation amount to apply to the @a x
     *            coordinate.
     */
    public void setXTranslation(double xTranslation) {
        this.xTranslation = xTranslation;
    }

    /**
     * Sets the translation amount to apply to the y coordinate of the input
     * value.
     * 
     * <p>
     * The getValue() method moves the ( x, y, z ) coordinates of the input
     * value by a translation amount before returning the output value from the
     * source module.
     * 
     * @param yTranslation The translation amount to apply to the y coordinate.
     */
    public void setYTranslation(double yTranslation) {
        this.yTranslation = yTranslation;
    }

    /**
     * Sets the translation amount to apply to the z coordinate of the input
     * value.
     * 
     * <p>
     * The getValue() method moves the ( x, y, z ) coordinates of the input
     * value by a translation amount before returning the output value from the
     * source module
     * 
     * @param zTranslation The translation amount to apply to the @a z
     *            coordinate.
     */
    public void setZTranslation(double zTranslation) {
        this.zTranslation = zTranslation;
    }
}
