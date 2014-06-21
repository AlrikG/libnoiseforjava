/*
 * Copyright (C) 2003, 2004 Jason Bevins (original libnoise code)
 * Copyright © 2010 Thomas J. Hodge (java port of libnoise)
 * Copyright (c) 2014 Nick Whitney (changed noisegen to perlin basis)
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

/** Noise module that randomly displaces the input value before
* returning the output value from a source module.
* <p>
* <img src="http://libnoise.sourceforge.net/docs/moduleturbulence.png">
* <p>
* Turbulence is the pseudo-random displacement of the input value.
* The getValue() method randomly displaces the ( x, y, z )
* coordinates of the input value before retrieving the output value from
* the source module.  To control the turbulence, an application can
* modify its frequency, its power, and its roughness.
* <p>
* The frequency of the turbulence determines how rapidly the
* displacement amount changes.  To specify the frequency, call the
* setFrequency() method.
* <p>
* The power of the turbulence determines the scaling factor that is
* applied to the displacement amount.  To specify the power, call the
* setPower() method.
* <p>
* The roughness of the turbulence determines the roughness of the
* changes to the displacement amount.  Low values smoothly change the
* displacement amount.  High values roughly change the displacement
* amount, which produces more "kinky" changes.  To specify the
* roughness, call the setRoughness() method.
* <p>
* Use of this noise module may require some trial and error.  Assuming
* that you are using a generator module as the source module, you
* should first:
* <ul>
* <li>Set the frequency to the same frequency as the source module.
* <li>Set the power to the reciprocal of the frequency.
* </ul>
* From these initial frequency and power values, modify these values
* until this noise module produce the desired changes in your terrain or
* texture.  For example:
* <ul>
* <li>Low frequency (1/8 initial frequency) and low power (1/8 initial
*   power) produces very minor, almost unnoticeable changes.
* <li>Low frequency (1/8 initial frequency) and high power (8 times
*   initial power) produces "ropey" lava-like terrain or marble-like
*   textures.
* <li>High frequency (8 times initial frequency) and low power (1/8
*   initial power) produces a noisy version of the initial terrain or
*   texture.
* <li>High frequency (8 times initial frequency) and high power (8 times
*   initial power) produces nearly pure noise, which isn't entirely
*   useful.
* </ul>
* Displacing the input values result in more realistic terrain and
* textures.  If you are generating elevations for terrain height maps,
* you can use this noise module to produce more realistic mountain
* ranges or terrain features that look like flowing lava rock.  If you
* are generating values for textures, you can use this noise module to
* produce realistic marble-like or "oily" textures.
* <p>
* Internally, there are three noise::module::Perlin noise modules
* that displace the input value; one for the x, one for the y,
* and one for the z coordinate.
* <p>
* This noise module requires one source module.
* 
* @see <a href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Turbulence.html">noise::module:Turbulence</a>
*/
public class Turbulence extends ModuleBase
{
	/** 
	 * Default frequency for the Turbulence noise module.
	 */
	static final double DEFAULT_TURBULENCE_FREQUENCY = Perlin.DEFAULT_PERLIN_FREQUENCY;

	/** 
	 * Default power for the Turbulence noise module.
	 */
	static final double DEFAULT_TURBULENCE_POWER = 1.0;

	/** 
	 * Default roughness for the Turbulence noise module.
	 */
	static final int DEFAULT_TURBULENCE_ROUGHNESS = 3;

	/** 
	 * Default noise seed for the Turbulence noise module.
	 */
	static final int DEFAULT_TURBULENCE_SEED = Perlin.DEFAULT_PERLIN_SEED;

	/** 
	 * The power (scale) of the displacement.
	 */
	double power;

	/** 
	 * Noise module that displaces the @a x coordinate.
	 */
	Perlin xDistortModule;

	/** 
	 * Noise module that displaces the y coordinate.
	 */
	Perlin yDistortModule;

	/** 
	 * Noise module that displaces the z coordinate.
	 */
	Perlin zDistortModule;

	public Turbulence (ModuleBase sourceModule) throws IllegalArgumentException
	{
		super(1);
		setSourceModule(0, sourceModule);

		power = DEFAULT_TURBULENCE_POWER;

		xDistortModule = new Perlin();
		yDistortModule = new Perlin();
		zDistortModule = new Perlin();

		setSeed(DEFAULT_TURBULENCE_SEED);
		setFrequency(DEFAULT_TURBULENCE_FREQUENCY);
		setRoughness (DEFAULT_TURBULENCE_ROUGHNESS);
	}
	
	public void build()
	{
		xDistortModule.build();
		yDistortModule.build();
		zDistortModule.build();
	}

	/** Returns the frequency of the turbulence.
	*
	* @returns The frequency of the turbulence.
	*
	* The frequency of the turbulence determines how rapidly the
	* displacement amount changes.
	*/
	public double getFrequency ()
	{
		// Since each noise::module::Perlin noise module has the same frequency, it
		// does not matter which module we use to retrieve the frequency.
		return xDistortModule.getFrequency ();
	}

	/** 
	 * Returns the seed value of the internal Perlin-noise modules that
	 * are used to displace the input values.
	 *
	 * @returns The seed value.
	 *
	 * Internally, there are three Perlin noise modules
	 * that displace the input value; one for the @a x, one for the @a y,
	 * and one for the @a z coordinate.
	 */
	public int getSeed ()
	{
		return xDistortModule.getSeed ();
	}

	@Override
	public double getValue (double x, double y, double z)
	{
		assert (sourceModules[0] != null);

		// Get the values from the three Perlin noise modules and
		// add each value to each coordinate of the input value.  There are also
		// some offsets added to the coordinates of the input values.  This prevents
		// the distortion modules from returning zero if the (x, y, z) coordinates,
		// when multiplied by the frequency, are near an integer boundary.  This is
		// due to a property of gradient coherent noise, which returns zero at
		// integer boundaries.
		double x0, y0, z0;
		double x1, y1, z1;
		double x2, y2, z2;

		x0 = x + (12414.0 / 65536.0);
		y0 = y + (65124.0 / 65536.0);
		z0 = z + (31337.0 / 65536.0);
		x1 = x + (26519.0 / 65536.0);
		y1 = y + (18128.0 / 65536.0);
		z1 = z + (60493.0 / 65536.0);
		x2 = x + (53820.0 / 65536.0);
		y2 = y + (11213.0 / 65536.0);
		z2 = z + (44845.0 / 65536.0);

		double xDistort = x + (xDistortModule.getValue (x0, y0, z0)
				* power);
		double yDistort = y + (yDistortModule.getValue (x1, y1, z1)
				* power);
		double zDistort = z + (zDistortModule.getValue (x2, y2, z2)
				* power);

		// Retrieve the output value at the offsetted input value instead of the
		// original input value.
		return sourceModules[0].getValue (xDistort, yDistort, zDistort);
	}

	/** 
	 * Sets the seed value of the internal noise modules that are used to
	 * displace the input values.
	 * <p>
	 * Internally, there are three Perlin noise modules
	 * that displace the input value; one for the x, one for the y,
	 * and one for the z coordinate.  This noise module assigns the
	 * following seed values to the Perlin noise modules: 
	 * <ul>
	 * <li>It assigns the seed value (@a seed + 0) to the x noise module.
	 * <li>It assigns the seed value (@a seed + 1) to the y noise module.
	 * <li>It assigns the seed value (@a seed + 2) to the z noise module.
	 * </ul>
	 * This is done to prevent any sort of weird artifacting.
	 * 
	 * @param seed The seed value.
	 */
	public void setSeed (int seed)
	{
		if(seed == 0)
		{
			xDistortModule.setSeed (0);
			yDistortModule.setSeed (0);
			zDistortModule.setSeed (0);
		}
		else
		{
			xDistortModule.setSeed (seed);
			yDistortModule.setSeed (seed + 1);
			zDistortModule.setSeed (seed + 2);
		}
	}

	/** 
	 * Returns the power of the turbulence.
	 *
	 * The power of the turbulence determines the scaling factor that is
	 * applied to the displacement amount.
	 * 
	 * @returns The power of the turbulence.
	 */
	public double getPower ()
	{
		return power;
	}

	/** 
	 * Returns the roughness of the turbulence.
	 * <p>
	 * The roughness of the turbulence determines the roughness of the
	 * changes to the displacement amount.  Low values smoothly change
	 * the displacement amount.  High values roughly change the
	 * displacement amount, which produces more "kinky" changes.
	 * 
	 * @returns The roughness of the turbulence.
	 */
	public int getRoughness ()
	{
		return xDistortModule.getOctaveCount ();
	}

	/** 
	 * Sets the frequency of the turbulence.
	 * <p>
	 * The frequency of the turbulence determines how rapidly the
	 * displacement amount changes.
	 * 
	 * @param frequency The frequency of the turbulence.
	 */
	public void setFrequency (double frequency)
	{
		xDistortModule.setFrequency (frequency);
		yDistortModule.setFrequency (frequency);
		zDistortModule.setFrequency (frequency);
	}

	/** 
	 * Sets the power of the turbulence.
	 * <p>
	 * The power of the turbulence determines the scaling factor that is
	 * applied to the displacement amount.
	 * 
	 * @param power The power of the turbulence.
	 */
	public void setPower (double power)
	{
		this.power = power;
	}

	/** 
	 * Sets the roughness of the turbulence.
	 * <p>
	 * The roughness of the turbulence determines the roughness of the
	 * changes to the displacement amount.  Low values smoothly change
	 * the displacement amount.  High values roughly change the
	 * displacement amount, which produces more "kinky" changes.
	 * <p>
	 * Internally, there are three Perlin noise modules
	 * that displace the input value; one for the x, one for the y,
	 * and one for the z coordinate.  The roughness value is equal to
	 * the number of octaves used by the noise::module::Perlin noise
	 * modules.
	 * 
	 * @param roughness The roughness of the turbulence.
	 */
	public void setRoughness (int roughness) throws IllegalArgumentException
	{
		xDistortModule.setOctaveCount (roughness);
		yDistortModule.setOctaveCount (roughness);
		zDistortModule.setOctaveCount (roughness);
	}

}
