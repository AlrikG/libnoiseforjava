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

import java.util.Random;

import libnoiseforjava.NoiseQuality;
import libnoiseforjava.PerlinBasis;

/**
 * Noise module that outputs three-dimensional "billowy" noise.
 * 
 * <p>
 * <img src="http://libnoise.sourceforge.net/docs/modulebillow.png">
 * 
 * <p>
 * This noise module generates "billowy" noise suitable for clouds and rocks.
 * 
 * <p>
 * This noise module is nearly identical to noise::module::Perlin except this
 * noise module modifies each octave with an absolute-value function. See the
 * documentation of noise::module::Perlin for more information.
 * 
 * @see <a
 *      href="http://libnoise.sourceforge.net/docs/classnoise_1_1module_1_1Billow.html">noise::module::Billow</a>
 * @see Perlin
 */
public class Billow extends ModuleBase {

    /**
     * Default frequency for the Billow noise module.
     */
    static final double DEFAULT_BILLOW_FREQUENCY = 1.0;

    /**
     * Default lacunarity for the Billow noise module.
     */
    static final double DEFAULT_BILLOW_LACUNARITY = 2.0;

    /**
     * Default number of octaves for the the noise::module::Billow noise module.
     */
    static final int DEFAULT_BILLOW_OCTAVE_COUNT = 6;

    /**
     * Default persistence value for the the noise::module::Billow noise module.
     */
    static final double DEFAULT_BILLOW_PERSISTENCE = 0.5;

    /**
     * Default noise seed for the the noise::module::Billow noise module.
     */
    static final int DEFAULT_BILLOW_SEED = 0;

    /**
     * Maximum number of octaves for the the noise::module::Billow noise module.
     */
    static final int BILLOW_MAX_OCTAVE = 30;

    double frequency;
    double lacunarity;
    double persistence;
    int octaveCount;
    int seed;
    NoiseQuality noiseQuality;
    double[] frequencies;

    PerlinBasis[] source;

    public Billow() {
        super(0);
        this.frequency = DEFAULT_BILLOW_FREQUENCY;
        this.lacunarity = DEFAULT_BILLOW_LACUNARITY;
        this.octaveCount = DEFAULT_BILLOW_OCTAVE_COUNT;
        this.persistence = DEFAULT_BILLOW_PERSISTENCE;
        this.seed = DEFAULT_BILLOW_SEED;
    }

    public void build() {
        this.source = new PerlinBasis[this.octaveCount];
        this.frequencies = new double[this.octaveCount];
        Random rnd = new Random(this.seed);

        for (int i = 0; i < this.octaveCount; i++) {
            this.source[i] = new PerlinBasis();

            if (this.seed != 0) {
                this.seed = rnd.nextInt();
                this.source[i].setSeed(this.seed + 1);
            } else {
                this.source[i].setSeed(this.seed);
            }

            this.frequencies[i] = Math.pow(this.lacunarity, i);
        }
    }

    @Override
    public double getValue(double x, double y, double z) {
        double value = 0.0;
        double signal = 0.0;
        double curPersistence = 1.0;

        x *= this.frequency;
        y *= this.frequency;
        z *= this.frequency;

        for (int i = 0; i < this.octaveCount; i++) {
            // Get the coherent-noise value from the input value and add it to
            // the
            // final result.
            signal = this.source[i].getValue(x * this.frequencies[i], y * this.frequencies[i], z * this.frequencies[i]);
            signal = 2.0 * Math.abs(signal) - 1.0;
            value += signal * curPersistence;

            // Prepare the next octave.
            curPersistence *= this.persistence;
        }

        value += 0.5;

        return value;
    }

    public double getFrequency() {
        return this.frequency;
    }

    public double getLacunarity() {
        return this.lacunarity;
    }

    public double getPersistence() {
        return this.persistence;
    }

    public int getOctaveCount() {
        return this.octaveCount;
    }

    public int getSeed() {
        return this.seed;
    }

    public void setFrequency(double frequency) {
        this.frequency = frequency;
    }

    public void setLacunarity(double lacunarity) {
        this.lacunarity = lacunarity;
    }

    public void setPersistence(double persistence) {
        this.persistence = persistence;
    }

    public void setOctaveCount(int octaveCount) {
        this.octaveCount = octaveCount;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }
}
