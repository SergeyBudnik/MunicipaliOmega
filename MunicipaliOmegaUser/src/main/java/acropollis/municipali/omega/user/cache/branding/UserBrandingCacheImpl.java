package acropollis.municipali.omega.user.cache.branding;

import acropollis.municipali.omega.common.dto.common.Pair;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Service
public class UserBrandingCacheImpl implements UserBrandingCache {
    private AtomicReference<Map<Pair<Integer, Integer>, byte []>> background = new AtomicReference<>(new HashMap<>());
    private AtomicReference<Map<Pair<Integer, Integer>, byte []>> icon = new AtomicReference<>(new HashMap<>());

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public void setBackground(Map<Pair<Integer, Integer>, byte[]> background) {
        lock.writeLock().lock();

        this.background.set(background);

        lock.writeLock().unlock();
    }

    @Override
    public Optional<byte []> getBackground(int w, int h) {
        try {
            lock.readLock().lock();

            return Optional.ofNullable(background.get().get(new Pair<>(w, h)));
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void setIcon(Map<Pair<Integer, Integer>, byte[]> icon) {
        lock.writeLock().lock();

        this.icon.set(icon);

        lock.writeLock().unlock();
    }

    @Override
    public Optional<byte []> getIcon(int size) {
        try {
            lock.readLock().lock();

            return Optional.ofNullable(icon.get().get(new Pair<>(size, size)));
        } finally {
            lock.readLock().unlock();
        }
    }
}
