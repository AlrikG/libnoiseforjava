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

/** 
 * Noise module that outputs a constant value.
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/moduleconst.png">
 * <p>
 * To specify the constant value, call the setConstValue() method.
 * <p>
 * This noise module is not useful by itself, but it is often used as a
 * source module for other noise modules.
 * <p>
 * This noise module does not require any source modules.
 * 
 * @see <a href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Const.html">noise::module::Const</a>
 */
public class Const extends ModuleBase
{
   /**
    *  Default constant value for the Const noise module.
    */
   static final double DEFAULT_CONST_VALUE = 0.0;

   double constValue;

   public Const ()
   {
      super(0);
      this.constValue = DEFAULT_CONST_VALUE;
   }

   @Override
public double getValue (double x, double y, double z)
   {
      return constValue;
   }

   /** Sets the constant output value for this noise module.
   *
   * @param constValue The constant output value for this noise module.
   */
   public void setConstValue (double constValue)
   {
      this.constValue = constValue;
   }
}
