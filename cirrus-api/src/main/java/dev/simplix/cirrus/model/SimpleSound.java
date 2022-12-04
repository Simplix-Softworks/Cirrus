package dev.simplix.cirrus.model;

import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.data.Sound;

/**
 * @param sound         Sound type representing the sound to be played
 * @param soundCategory The category of the sound
 * @param volume        Float representing the volume at which the sound should be played
 * @param pitch         Float representing the pitch at which the sound should be played The
 *                      SimpleSound record can be used to easily store and pass around information
 *                      about a sound without having to create a separate class for it. It provides
 *                      getter and setter methods for each of its fields, allowing users to access
 *                      and modify the stored data. This allows for easy creation and storage of
 *                      sound information in a single object.
 */
public record SimpleSound(Sound sound, SoundCategory soundCategory, float volume, float pitch) {

}
