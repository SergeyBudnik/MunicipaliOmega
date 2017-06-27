package acropollis.municipali.omega.common.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Pair<X, Y> {
    private X x;
    private Y y;
}
