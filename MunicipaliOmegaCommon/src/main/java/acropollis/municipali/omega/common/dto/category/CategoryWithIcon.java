package acropollis.municipali.omega.common.dto.category;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Delegate;

import java.beans.Transient;
import java.util.Map;

@Data
public class CategoryWithIcon {
    @Delegate
    @Getter(AccessLevel.PRIVATE)
    @Setter(AccessLevel.PRIVATE)
    private Category category;

    private Map<Integer, byte []> icon;

    @Transient
    public Category withoutIcon() {
        return category;
    }
}
