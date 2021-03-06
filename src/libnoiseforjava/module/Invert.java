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
 * Noise module that inverts the output value from a source module.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/moduleinvert.png">
 * 
 * <p>
 * This noise module requires one source module.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Invert.html">noise::module:Invert</a>
 */
public class Invert extends ModuleBase {

    public Invert(ModuleBase sourceModule) throws IllegalArgumentException {
        super(1);
        setSourceModule(0, sourceModule);
    }

    @Override
    public double getValue(double x, double y, double z) {
        assert (this.sourceModules[0] != null);

        return -(this.sourceModules[0].getValue(x, y, z));
    }
}
