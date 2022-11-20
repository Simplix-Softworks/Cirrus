package dev.simplix.cirrus.model;

import dev.simplix.protocolize.api.SoundCategory;
import dev.simplix.protocolize.data.Sound;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

public record SimpleSound(Sound sound, SoundCategory soundCategory, float volume, float pitch) {

}
