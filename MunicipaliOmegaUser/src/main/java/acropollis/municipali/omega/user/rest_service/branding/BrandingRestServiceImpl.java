package acropollis.municipali.omega.user.rest_service.branding;

import acropollis.municipali.omega.common.exceptions.EntityNotFoundException;
import acropollis.municipali.omega.user.cache.branding.BrandingCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandingRestServiceImpl implements BrandingRestService {
    @Autowired
    private BrandingCache brandingCache;

    @Override
    public byte [] getIcon(int size) {
        return brandingCache.getIcon(size).orElseThrow(() -> new EntityNotFoundException(""));
    }

    @Override
    public byte [] getBackground(int w, int h) {
        return brandingCache.getBackground(w, h).orElseThrow(() -> new EntityNotFoundException(""));
    }
}
