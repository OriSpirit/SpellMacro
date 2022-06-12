package com.spiritlight.spellmacro;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SpellSequence {
    private final List<Integer> keySequence = new ArrayList<>();
    private final String rawSequence;
    private int castDelay = 50;

    public SpellSequence(@Nonnull String sequence) {
        sequence = sequence.toUpperCase(Locale.ROOT).replaceAll("[^(LR)]", "");
        this.rawSequence = sequence;
        while(sequence.length() > 0) {
            switch(sequence.charAt(0)) {
                case 'L':
                    keySequence.add(0);
                    break;
                case 'R':
                    keySequence.add(1);
                    break;
                default:
                    break;
            }
            sequence = sequence.substring(1);
        }
    }

    public void setCastDelay(int delay) throws IllegalArgumentException {
        if(delay < 5) {
            throw new IllegalArgumentException("Delay must be at least 5ms.");
        }
        this.castDelay = delay;
    }

    public int getCastDelay() {
        return castDelay;
    }

    public List<Integer> getSpell() {
        return keySequence;
    }

    public int getKey(int index) {
        return keySequence.get(index);
    }

    public String getRawSequence() {
        return rawSequence;
    }
}
