/*******************************************************************************
 *  Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 *  Copyright © 2010 Thomas J. Hodge (java port of libnoise)
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

import libnoiseforjava.Interp;

/**
 * Noise module that outputs the value selected from one of two source modules
 * chosen by the output value from a control module.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/moduleselect.png">
 * 
 * <p>
 * Unlike most other noise modules, the index value assigned to a source module
 * determines its role in the selection operation:
 * <ul>
 * <li>Source module 0 (upper left in the diagram) outputs a value.
 * <li>Source module 1 (lower left in the diagram) outputs a value.
 * <li>Source module 2 (bottom of the diagram) is known as the <i>control
 * module</i>. The control module determines the value to select. If the output
 * value from the control module is within a range of values known as the
 * <i>selection range</i>, this noise module outputs the value from the source
 * module with an index value of 1. Otherwise, this noise module outputs the
 * value from the source module with an index value of 0.
 * </ul>
 * To specify the bounds of the selection range, call the setBounds() method.
 * 
 * <p>
 * An application can pass the control module to the setControlModule() method
 * instead of the setSourceModule() method. This may make the application code
 * easier to read.
 * 
 * <p>
 * By default, there is an abrupt transition between the output values from the
 * two source modules at the selection-range boundary. To smooth the transition,
 * pass a non-zero value to the setEdgeFalloff() method. Higher values result in
 * a smoother transition.
 * 
 * <p>
 * This noise module requires three source modules.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Select.html">noise::module:Select</a>
 */
public class Select extends ModuleBase {

    /**
     * Default edge-falloff value for the Select noise module.
     */
    static final double DEFAULT_SELECT_EDGE_FALLOFF = 0.0;

    /**
     * Default lower bound of the selection range for the Select noise module.
     */
    static final double DEFAULT_SELECT_LOWER_BOUND = -1.0;

    /**
     * Default upper bound of the selection range for the Select noise module.
     */
    static final double DEFAULT_SELECT_UPPER_BOUND = 1.0;

    /**
     * Edge-falloff value.
     */
    double edgeFalloff;

    /**
     * Lower bound of the selection range.
     */
    double lowerBound;

    /**
     * Upper bound of the selection range.
     */
    double upperBound;

    public Select(ModuleBase sourceModuleOne, ModuleBase sourceModuleTwo, ModuleBase sourceModuleThree) throws IllegalArgumentException {
        super(3);
        setSourceModule(0, sourceModuleOne);
        setSourceModule(1, sourceModuleTwo);
        setSourceModule(2, sourceModuleThree);

        this.edgeFalloff = DEFAULT_SELECT_EDGE_FALLOFF;
        this.lowerBound = DEFAULT_SELECT_LOWER_BOUND;
        this.upperBound = DEFAULT_SELECT_UPPER_BOUND;
    }

    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);
        assert (this.sourceModules[1] != null);
        assert (this.sourceModules[2] != null);

        double controlValue = this.sourceModules[2].getValue(x, y, z);
        double alpha;

        if (this.edgeFalloff > 0.0) {
            if (controlValue < (this.lowerBound - this.edgeFalloff)) {
                /*
                 * The output value from the control module is below the
                 * selector threshold; return the output value from the first
                 * source module.
                 */
                return this.sourceModules[0].getValue(x, y, z);
            } else if (controlValue < (this.lowerBound + this.edgeFalloff)) {
                /*
                 * The output value from the control module is near the lower
                 * end of the selector threshold and within the smooth curve.
                 * Interpolate between the output values from the first and
                 * second source modules.
                 */
                double lowerCurve = (this.lowerBound - this.edgeFalloff);
                double upperCurve = (this.lowerBound + this.edgeFalloff);
                alpha = Interp.SCurve3((controlValue - lowerCurve) / (upperCurve - lowerCurve));
                return Interp.lerp(this.sourceModules[0].getValue(x, y, z), this.sourceModules[2].getValue(x, y, z), alpha);
            } else if (controlValue < (this.upperBound - this.edgeFalloff)) {
                /*
                 * The output value from the control module is within the
                 * selector threshold; return the output value from the second
                 * source module.
                 */
                return this.sourceModules[1].getValue(x, y, z);
            } else if (controlValue < (this.upperBound + this.edgeFalloff)) {
                /*
                 * The output value from the control module is near the upper
                 * end of the selector threshold and within the smooth curve.
                 * Interpolate between the output values from the first and
                 * second source modules.
                 */
                double lowerCurve = (this.upperBound - this.edgeFalloff);
                double upperCurve = (this.upperBound + this.edgeFalloff);
                alpha = Interp.SCurve3((controlValue - lowerCurve) / (upperCurve - lowerCurve));
                return Interp.lerp(this.sourceModules[1].getValue(x, y, z), this.sourceModules[0].getValue(x, y, z), alpha);
            } else {
                /*
                 * Output value from the control module is above the selector
                 * threshold; return the output value from the first source
                 * module.
                 */
                return this.sourceModules[0].getValue(x, y, z);
            }
        } else {
            if (controlValue < this.lowerBound || controlValue > this.upperBound) {
                return this.sourceModules[0].getValue(x, y, z);
            } else {
                return this.sourceModules[1].getValue(x, y, z);
            }
        }
    }

    /**
     * Sets the lower and upper bounds of the selection range.
     *
     * @param lowerBound The lower bound.
     * @param upperBound The upper bound.
     *
     * @pre The lower bound must be less than or equal to the upper bound.
     */
    public void setBounds(double lowerBound, double upperBound) {
        assert (lowerBound < upperBound);

        this.lowerBound = lowerBound;
        this.upperBound = upperBound;

        // Make sure that the edge falloff curves do not overlap.
        setEdgeFalloff(this.edgeFalloff);
    }

    /**
     * Sets the falloff value at the edge transition.
     *
     * <p>
     * The falloff value is the width of the edge transition at either edge of
     * the selection range.
     * 
     * <p>
     * By default, there is an abrupt transition between the values from the two
     * source modules at the boundaries of the selection range.
     * 
     * <p>
     * For example, if the selection range is 0.5 to 0.8, and the edge falloff
     * value is 0.1, then the getValue() method outputs:
     * <ul>
     * <li>the output value from the source module with an index value of 0 if
     * the output value from the control module is less than 0.4 ( = 0.5 - 0.1).
     * <li>a linear blend between the two output values from the two source
     * modules if the output value from the control module is between 0.4 ( =
     * 0.5 - 0.1) and 0.6 ( = 0.5 + 0.1).
     * <li>the output value from the source module with an index value of 1 if
     * the output value from the control module is between 0.6 ( = 0.5 + 0.1)
     * and 0.7 ( = 0.8 - 0.1).
     * <li>a linear blend between the output values from the two source modules
     * if the output value from the control module is between 0.7 ( = 0.8 - 0.1
     * ) and 0.9 ( = 0.8 + 0.1).
     * <li>the output value from the source module with an index value of 0 if
     * the output value from the control module is greater than 0.9 ( = 0.8 +
     * 0.1).
     * </ul>
     * 
     * @param edgeFalloff The falloff value at the edge transition.
     */
    public void setEdgeFalloff(double edgeFalloff) {
        // Make sure that the edge falloff curves do not overlap.
        double boundSize = this.upperBound - this.lowerBound;
        edgeFalloff = (edgeFalloff > boundSize / 2) ? boundSize / 2 : edgeFalloff;
    }

    /**
     * Returns the control module.
     *
     * <p>
     * The control module determines the output value to select. If the output
     * value from the control module is within a range of values known as the
     * <i>selection range</i>, the getValue() method outputs the value from the
     * source module with an index value of 1. Otherwise, this method outputs
     * the value from the source module with an index value of 0.
     * 
     * @return A reference to the control module.
     *
     * @pre A control module has been added to this noise module via a call to
     *      setSourceModule() or setControlModule().
     *
     * @throws IllegalArgumentException See the preconditions for more
     *             information.
     */
    public ModuleBase getControlModule() throws IllegalArgumentException {
        if (this.sourceModules == null || this.sourceModules[2] == null) {
            throw new IllegalArgumentException("Could not retrieve a source module from a noise module.");
        }
        return (this.sourceModules[2]);
    }

    /**
     * Returns the falloff value at the edge transition.
     * 
     * <p>
     * The falloff value is the width of the edge transition at either edge of
     * the selection range.
     *
     * <p>
     * By default, there is an abrupt transition between the output values from
     * the two source modules at the selection-range boundary.
     *
     * @return The falloff value at the edge transition.
     */
    public double getEdgeFalloff() {
        return this.edgeFalloff;
    }

    /**
     * Returns the lower bound of the selection range.
     * 
     * <p>
     * If the output value from the control module is within the selection
     * range, the getValue() method outputs the value from the source module
     * with an index value of 1. Otherwise, this method outputs the value from
     * the source module with an index value of 0.
     * 
     * @return The lower bound of the selection range.
     */
    public double getLowerBound() {
        return this.lowerBound;
    }

    /**
     * Returns the upper bound of the selection range.
     * 
     * <p>
     * If the output value from the control module is within the selection
     * range, the getValue() method outputs the value from the source module
     * with an index value of 1. Otherwise, this method outputs the value from
     * the source module with an index value of 0.
     * 
     * @return The upper bound of the selection range.
     */
    public double getUpperBound() {
        return this.upperBound;
    }

    /**
     * Sets the control module.
     * 
     * <p>
     * The control module determines the output value to select. If the output
     * value from the control module is within a range of values known as the
     * <i>selection range</i>, the getValue() method outputs the value from the
     * source module with an index value of 1. Otherwise, this method outputs
     * the value from the source module with an index value of 0.
     * 
     * <p>
     * This method assigns the control module an index value of 2. Passing the
     * control module to this method produces the same results as passing the
     * control module to the setSourceModule() method while assigning that noise
     * module an index value of 2.
     * 
     * <p>
     * This control module must exist throughout the lifetime of this noise
     * module unless another control module replaces that control module.
     * 
     * @param controlModule The control module.
     */
    public void setControlModule(ModuleBase controlModule) {
        assert (this.sourceModules != null);
        this.sourceModules[2] = controlModule;
    }
}
