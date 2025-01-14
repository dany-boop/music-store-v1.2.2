package com.music.music_store.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BlockChainStatus {
    PENDING("pending"),
    FAILED("failed"),
    SUCCESS("success"),
    INVALID("invalid"),
    REVERTED("reverted");

    private final String name;

    public static BlockChainStatus getByName(String name) {
        for (BlockChainStatus blockChainStatus : BlockChainStatus.values()) {
            if (blockChainStatus.getName().equalsIgnoreCase(name))
                return blockChainStatus;
        }
        return null;
    }
}
