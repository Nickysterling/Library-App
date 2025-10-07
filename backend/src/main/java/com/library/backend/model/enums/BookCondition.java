package com.library.backend.model.enums;

public enum BookCondition {
    NEW(1),
    GOOD(2),
    FAIR(3),
    POOR(4),
    DAMAGED(5),
    LOST(6);

    private final int rank;

    BookCondition(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    /**
     * Returns true if this condition is worse or equal to the original.
     * Example: FAIR can be returned as FAIR, POOR, DAMAGED, LOST — but not GOOD or NEW.
     */
    public boolean canBeAssigned(BookCondition original) {
        return this.rank >= original.rank;
    }
}
