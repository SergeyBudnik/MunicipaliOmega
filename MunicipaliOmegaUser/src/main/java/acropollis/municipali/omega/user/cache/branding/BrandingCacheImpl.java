package acropollis.municipali.omega.user.cache.branding;

import acropollis.municipali.omega.common.dto.common.Tuple;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class BrandingCacheImpl implements BrandingCache {
    private Map<Tuple<Integer, Integer>, byte []> background = new HashMap<>();
    private Map<Tuple<Integer, Integer>, byte []> icon = new HashMap<>();

    @Override
    public void setBackground(Map<Tuple<Integer, Integer>, byte[]> background) {
        this.background = background;
    }

    @Override
    public Optional<byte []> getBackground(int w, int h) {
        return Optional.ofNullable(background.get(new Tuple<>(w, h)));
    }

    @Override
    public void setIcon(Map<Tuple<Integer, Integer>, byte[]> icon) {
        this.icon = icon;
    }

    @Override
    public Optional<byte []> getIcon(int size) {
        return Optional.ofNullable(icon.get(new Tuple<>(size, size)));
    }
}
