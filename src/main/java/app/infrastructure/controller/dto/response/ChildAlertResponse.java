package app.infrastructure.controller.dto.response;

import java.util.List;
import java.util.Objects;

public class ChildAlertResponse {
    private List<ChildAlert> childrenAlert;

    public List<ChildAlert> getChildrenAlert() {
        return childrenAlert;
    }

    public void setChildrenAlert(List<ChildAlert> childrenAlert) {
        this.childrenAlert = childrenAlert;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChildAlertResponse that = (ChildAlertResponse) o;
        return Objects.equals(childrenAlert, that.childrenAlert);
    }

    @Override
    public int hashCode() {
        return Objects.hash(childrenAlert);
    }
}
