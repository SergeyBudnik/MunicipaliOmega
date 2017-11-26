package acropollis.municipali.omega.health_check.async;

import acropollis.municipali.omega.health_check.cache.HealthCheckCache;
import acropollis.municipali.omega.health_check.data.CommonComponentHealth;
import acropollis.municipali.omega.health_check.data.CommonHealth;

import java.util.Date;
import java.util.function.Consumer;

public abstract class CommonHealthCheck<H extends CommonComponentHealth, JH extends CommonHealth> {
    private static final int NO_RELOAD = -1;
    private static final String SUCCESS = "Success";

    private long newLastReloadDate = NO_RELOAD;
    private long lastReloadDate = NO_RELOAD;

    protected abstract HealthCheckCache getHealthCheckCache();
    protected abstract H getHealthEntity();
    protected abstract JH getReloadJobHealthEntity();
    protected abstract void updateReloadJobHealth(H userHealth, JH reloadJobHealth);

    protected final void onReloadStarted() {
        newLastReloadDate = new Date().getTime();
    }

    protected final H onReloadSuccess(long startDate, long endDate) {
        return onReloadFinished(true, startDate, endDate, reloadJobHealth ->
                reloadJobHealth.setMessage(SUCCESS)
        );
    }

    protected final void onReloadFailure(long startDate, long endDate, Exception failureReason) {
        onReloadFinished(false, startDate, endDate, reloadJobHealth ->
            reloadJobHealth.setMessage(failureReason.getMessage())
        );
    }

    private H onReloadFinished(boolean health, long startDate, long endDate, Consumer<JH> specificAction) {
        H componenHealth = (H) getHealthCheckCache().getHealth().orElse(getHealthEntity());

        JH reloadJobHealth = getReloadJobHealthEntity(); {
            reloadJobHealth.setHealth(health);
            reloadJobHealth.setDuration(endDate - startDate);
            reloadJobHealth.setLastUpdateDate(new Date().getTime());
            reloadJobHealth.setDurationSincePenultimateUpdate(
                    lastReloadDate == NO_RELOAD ? NO_RELOAD : endDate - lastReloadDate
            );
        }

        specificAction.accept(reloadJobHealth);

        updateReloadJobHealth(componenHealth, reloadJobHealth);

        getHealthCheckCache().saveHealth(componenHealth);

        lastReloadDate = newLastReloadDate;

        return componenHealth;
    }
}
