package apiUserManagement.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CoreResponse<T> {
    private boolean status;

    private T payload;

    private String error;
}
