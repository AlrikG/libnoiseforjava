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

import libnoiseforjava.Interp;

/**
 * Noise module that outputs a weighted blend of the output values from two
 * source modules given the output value supplied by a control module.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/moduleblend.png">
 * 
 * <p>
 * Unlike most other noise modules, the index value assigned to a source module
 * determines its role in the blending operation:
 * <ul>
 * <li>Source module 0 outputs one of the values to blend.
 * <li>Source module 1 outputs one of the values to blend.
 * <li>Source module 2 is known as the <i>control module</i>. The control module
 * determines the weight of the blending operation. Negative values weigh the
 * blend towards the output value from the source module with an index value of
 * 0. Positive values weigh the blend towards the output value from the source
 * module with an index value of 1.
 * </ul>
 * 
 * <p>
 * An application can pass the control module to the setControlModule() method
 * instead of the setSourceModule() method. This may make the application code
 * easier to read.
 * 
 * <p>
 * This noise module uses linear interpolation to perform the blending
 * operation.
 * 
 * <p>
 * This noise module requires three source modules.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Blend.html">noise::module::Blend</a>
 */
public class Blend extends ModuleBase {

    public Blend(ModuleBase sourceModuleOne, ModuleBase sourceModuleTwo, ModuleBase sourceModuleThree) throws IllegalArgumentException {
        super(3);
        setSourceModule(0, sourceModuleOne);
        setSourceModule(1, sourceModuleTwo);
        setSourceModule(2, sourceModuleThree);
    }

    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);
        assert (this.sourceModules[1] != null);
        assert (this.sourceModules[2] != null);

        double v0 = this.sourceModules[0].getValue(x, y, z);
        double v1 = this.sourceModules[1].getValue(x, y, z);
        double alpha = (this.sourceModules[2].getValue(x, y, z) + 1.0) / 2.0;

        return Interp.lerp(v0, v1, alpha);
    }
}
