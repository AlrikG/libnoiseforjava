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

import libnoiseforjava.exception.ExceptionNoModule;

public class ModuleBase {

    /**
     * base class for noise modules.
     */
    public ModuleBase[] sourceModules;
    public int modulesRequired;

    public ModuleBase() {
        this.modulesRequired = 0;
    }

    public ModuleBase(int modulesRequired) {
        // Create an array of pointers to all source modules required by this
        // noise module. Set these pointers to null.
        if (modulesRequired > 0) {
            this.sourceModules = new ModuleBase[modulesRequired];
            for (int i = 0; i < modulesRequired; i++) {
                this.sourceModules[i] = new ModuleBase();
            }
        } else {
            this.sourceModules = null;
        }

        this.modulesRequired = modulesRequired;
    }

    /**
     * Returns a reference to a source module connected to this noise module.
     *
     * @param index The index value assigned to the source module.
     *
     * @return A reference to the source module.
     *
     * @pre The index value ranges from 0 to one less than the number of source
     *      modules required by this noise module.
     * @pre A source module with the specified index value has been added to
     *      this noise module via a call to setSourceModule().
     *
     * @throws ExceptionNoModule See the preconditions for more information.
     *
     *             Each noise module requires the attachment of a certain number
     *             of source modules before an application can call the
     *             getValue() method.
     */
    public ModuleBase getSourceModule(int index) throws ExceptionNoModule {
        if (this.sourceModules != null) {
            if (index >= getSourceModuleCount() || index < 0 || this.sourceModules[index] == null) {
                throw new ExceptionNoModule("Could not retrieve a source module " + "from a noise module.");
            }

            return (this.sourceModules[index]);
        }
        throw new ExceptionNoModule("Could not retrieve a source module " + "from a noise module.");
    }

    /**
     * Returns the number of source modules required by this noise module.
     *
     * @return The number of source modules required by this noise module.
     */
    public int getSourceModuleCount() {
        return this.modulesRequired;
    }

    /**
     * Generates an output value given the coordinates of the specified input
     * value.
     *
     * @param x The @a x coordinate of the input value.
     * @param y The @a y coordinate of the input value.
     * @param z The @a z coordinate of the input value.
     *
     * @return The output value.
     *
     * @pre All source modules required by this noise module have been passed to
     *      the setSourceModule() method.
     *
     *      Before an application can call this method, it must first connect
     *      all required source modules via the setSourceModule() method. If
     *      these source modules are not connected to this noise module, this
     *      method raises a debug assertion.
     *
     *      To determine the number of source modules required by this noise
     *      module, call the getSourceModuleCount() method.
     */
    public double getValue(double x, double y, double z) {
        return x;
    }

    /**
     * Connects a source module to this noise module.
     * 
     * <p>
     * A noise module mathematically combines the output values from the source
     * modules to generate the value returned by getValue().
     *
     * <p>
     * The index value to assign a source module is a unique identifier for that
     * source module. If an index value has already been assigned to a source
     * module, this noise module replaces the old source module with the new
     * source module.
     *
     * <p>
     * Before an application can call the getValue() method, it must first
     * connect all required source modules. To determine the number of source
     * modules required by this noise module, call the getSourceModuleCount()
     * method.
     *
     * <p>
     * This source module must exist throughout the lifetime of this noise
     * module unless another source module replaces that source module.
     *
     * <p>
     * A noise module does not modify a source module; it only modifies its
     * output values.
     *
     * @param index An index value to assign to this source module.
     * @param sourceModule The source module to attach.
     *
     * @pre The index value ranges from 0 to one less than the number of source
     *      modules required by this noise module.
     *
     * @throws IllegalArgumentException An invalid parameter was specified; see the
     *             preconditions for more information.
     */
    public void setSourceModule(int index, ModuleBase sourceModule) throws IllegalArgumentException {
        if (this.sourceModules != null) {
            if (index >= getSourceModuleCount() || index < 0) {
                throw new IllegalArgumentException("Invalid Parameter in ModuleBase");
            }
        }
        this.sourceModules[index] = sourceModule;
    }

}
