/*
 * Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 * Copyright © 2010 Thomas J. Hodge (java port of libnoise)
 * Copyright (c) 2014 Nick Whitney (implemented javadoc)
 * 
 * This file is part of libnoiseforjava.
 * 
 * libnoiseforjava is a Java port of the C++ library libnoise, which may be found at 
 * http://libnoise.sourceforge.net/.  libnoise was developed by Jason Bevins, who may be 
 * contacted at jlbezigvins@gmzigail.com (for great email, take off every 'zig').
 * Porting to Java was done by Thomas Hodge, who may be contacted at
 * libnoisezagforjava@gzagmail.com (remove every 'zag').
 * 
 * libnoiseforjava is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * libnoiseforjava is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * libnoiseforjava.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */

package libnoiseforjava.module;

/** Noise module that clamps the output value from a source module to a
* range of values.
* <p>
* <img src="http://libnoise.sourceforge.net/docs/moduleclamp.png">
* <p>
* The range of values in which to clamp the output value is called the
* <i>clamping range</i>.
* <p>
* If the output value from the source module is less than the lower
* bound of the clamping range, this noise module clamps that value to
* the lower bound.  If the output value from the source module is
* greater than the upper bound of the clamping range, this noise module
* clamps that value to the upper bound.
* <p>
* To specify the upper and lower bounds of the clamping range, call the
* setBounds() method.
* <p>
* This noise module requires one source module.
* 
* @see <a href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Clamp.html">noise::module::Clamp</a>
*/
public class Clamp extends ModuleBase
{
   /**
    *  Default lower bound of the clamping range for the Clamp noise module.
    */
   static final double DEFAULT_CLAMP_LOWER_BOUND = -1.0;

   /**
    *  Default upper bound of the clamping range for the Clamp noise module.
    */
   static final double DEFAULT_CLAMP_UPPER_BOUND = 1.0;

   double lowerBound, upperBound;

   public Clamp (ModuleBase sourceModule) throws IllegalArgumentException
   {
      super(1);
      setSourceModule(0, sourceModule);

      lowerBound = DEFAULT_CLAMP_LOWER_BOUND;
      upperBound = DEFAULT_CLAMP_UPPER_BOUND;
   }

   @Override
public double getValue (double x, double y, double z)
   {
      assert (sourceModules[0] != null);

      double value = sourceModules[0].getValue (x, y, z);
      if (value < lowerBound)
         return lowerBound;
      else if (value > upperBound)
         return upperBound;
      else
         return value;
   }

   public void setBounds (double lowerBound, double upperBound)
   {
      assert (lowerBound < upperBound);

      this.lowerBound = lowerBound;
      this.upperBound = upperBound;
   }
}
