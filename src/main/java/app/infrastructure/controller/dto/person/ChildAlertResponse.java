package app.infrastructure.controller.dto.person;

import java.util.List;

public class ChildAlertResponse {
    private List<ChildAlert> childrenAlert;

    public List<ChildAlert> getChildrenAlert() {
        return childrenAlert;
    }

    public void setChildrenAlert(List<ChildAlert> childrenAlert) {
        this.childrenAlert = childrenAlert;
    }

}
