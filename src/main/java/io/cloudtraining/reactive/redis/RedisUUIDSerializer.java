package io.cloudtraining.reactive.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.lang.Nullable;

import java.nio.ByteBuffer;
import java.util.UUID;

public class RedisUUIDSerializer implements RedisSerializer<UUID> {
    @Nullable
    @Override
    public byte[] serialize(@Nullable UUID uuid) throws SerializationException {
        if (uuid == null) {
            return null;
        } else {
            ByteBuffer buf = ByteBuffer.allocate(16);
            buf.putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits());
            return buf.array();
        }
    }

    @Nullable
    @Override
    public UUID deserialize(@Nullable byte[] bytes) throws SerializationException {
        if (bytes == null) {
            return null;
        } else if (bytes.length != 16) {
            throw new SerializationException("Invalid number of bytes for key (" + bytes.length + ')');
        } else {
            ByteBuffer buf = ByteBuffer.wrap(bytes);
            long high = buf.getLong();
            long low = buf.getLong();
            return new UUID(high, low);
        }
    }
}
